(ns curbside.core
  (:gen-class)
  (:require [clojure.string :refer [replace-first]]
            [clojure.core.async :refer [go-loop chan put! close! >!! <!
                                        alts!! pipeline-async timeout]]
            [clj-http.client :as http])
  (:use [clojure.pprint]
        [slingshot.slingshot :only [throw+ try+]]))


(def challenge-timeout 300000)
(def root-url "http://challenge.shopcurbside.com/")

(def num-cpus (.availableProcessors (Runtime/getRuntime)))
(def parallelism (* num-cpus 5))

(def secret-str (atom ""))
(def pending-map (atom {}))

(defn alts?? [chans]
  "Async alts!! that throws an exception if item returned is throwable."
	(let [[v c] (alts!! chans)]
		(if (instance? Throwable v)
			(throw v)
			[v c])))

(defn put-and-close! [c v]
  (put! c v)
  (close! c))

(defn throw-and-close! [c ex]
  (put! c (throw+ ex))
  (close! c))

(defn get-session-id [url]
  (let [res (try+
             (http/get url {:as :json})
             (catch Object _ (throw+)))]
    (get-in res [:body :session])))

(defn normalize-body [body]
  (reduce-kv
   (fn [m k v]
     (assoc m (keyword (.toLowerCase (name k))) v)) {} body))

(defn async-http-get [url session-id out*]
  (let [url (str root-url url)]
    (http/get url
              {:async? true
               :as :json
               :headers {:Session session-id}}
              ;; Callback
              (fn [res]
                (let [body   (normalize-body (:body res))
                      id     (:id body)
                      next   (:next body)
                      secret (:secret body)]
                  (if (nil? id)
                    (throw-and-close! out*
                                      {:url url :body body :res res
                                       :mgs "missing :id ?"})
                    (if (nil? secret)
                      (if (nil? next)
                        (throw-and-close! out*
                                          {:url url :body body :res res
                                           :id id :msg "missing :next ?"})
                        (let [urls (if (vector? next) next [next])]
                          (put-and-close! out* [id urls])))
                      (put-and-close! out* [id secret])))))
              ;; Exception callback
              (fn [ex] (throw-and-close! out* ex)))))

(defn pipeline-async-crawler [output-chan input-chan session-id]
  (println "pipeline-async-crawler parallelism: " parallelism)
  (let [req-handler (fn [url out*] (async-http-get url session-id out*))]
    (pipeline-async parallelism
                    output-chan
                    req-handler
                    input-chan)))


(defn -main
  "Run the curbside challenge."
  [& args]
  (println "Hello Curbside!")
  (let [timedout-bool  (atom false)
        finished-bool (atom false)
        input-chan    (chan 10000)
        output-chan   (chan)
        timeout-chan  (timeout challenge-timeout)
        session-id    (get-session-id (str root-url "get-session"))
        begin-time    (System/currentTimeMillis)]

    (println "Session ID: " session-id)

    (pipeline-async-crawler output-chan input-chan session-id)

    (>!! input-chan "start")

    ;; Periodic display of Progress (%), Speed and # of pending req(s)
    (go-loop []
      (<! (timeout 1000))
      (let [url (str root-url "get-stats/" session-id)
            res (try
                 (http/get url {:as :json
                                :headers {:Session session-id}})
                 (catch Object _ nil))]
        (if (= 200 (:status res))
          (let [body  (:body res)
                pct   (:Pct body)
                speed (:Speed body)]
            (println "%:" (format "%3d" pct)
                     " Speed: " (format "%3d" speed)
                     " # of Pending HTTP GET Req: "
                     (format "%4d" (count @pending-map))))))
      (if (and (not @timedout-bool) (not @finished-bool)) (recur)))

    ;; Sequentially process the output-chan,
    ;; enqueue next URL(s) if any and add them to the pending-map.
    ;; finally, recur until no more next URL.
    ;;
    ;; NOTE: Blocking get from the output-queue ==> depth-first traversal!
    ;;       This is because core.async pipeline outputs will be returned
    ;;       in order relative to the inputs...
    ;;
    (loop [cnt 0]
      (if (nil?
           (try
             (let [[out ch] (alts?? [output-chan timeout-chan])]
               (condp = ch
                 timeout-chan (do (close! input-chan)
                                  (reset! timedout-bool true)
                                  (println "TIMEOUT!"))
                 output-chan
                 (if (nil? out)
                   (println "Output channel is now closed.")
                   (let [id  (first out)
                         val (second out)]
                     (if (vector? val)
                       ;; GOT NEXT!
                       (let [urls-map (zipmap val (repeat id))]
                         (doseq [url val] (>!! input-chan url))
                         (swap! pending-map merge urls-map))
                       ;; GOT SECRET!
                       (if (complement (nil? val))
                         (swap! secret-str str val)))
                     (swap! pending-map dissoc id)
                     (if (empty? @pending-map)
                       (do (close! input-chan)
                           (reset! finished-bool true)
                           nil)
                       out)))))
             (catch Exception e (throw e))))
        ;; nil ==> Loop over!
        (do (println "Output-chan processing loop Finished!")
            (println "Total # of HTTP GET Req: " cnt))
        ;; Keep looping until output-chan closes or timeout...
        (recur (inc cnt))))

    ;; It's over, did we get a timeout or a valid secret key?
    (if @timedout-bool
      (println "Error: Timed-out before completing the challenge!")
      (let [secret-key (replace-first @secret-str #"Secret key: " "")]
        (assert @finished-bool)
        (println "SECRET KEY: " secret-key)
        (println "Time spent processing the challenge (in milliseconds): "
                 (- (System/currentTimeMillis) begin-time))
        (println "Validating the key...")
        (let [url (str root-url "validate/" secret-key)
              res (try+
                   (http/get url {:headers {:Session session-id}})
                   (catch Object _ (throw+)))]
          (if (= 200 (:status res))
            (println "Key is Valid! Success!!!")
            (println "Key is NOT valid. Sorry!"))
          )))
    ))
