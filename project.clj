(defproject curbside "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "https://github.com/sineer/curbside"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.10.0"]
                 [org.clojure/core.async "0.4.490"]
                 [clj-http "3.9.1"]
                 [slingshot "0.12.2"]
                 [cheshire "5.8.1"]]
  :main ^:skip-aot curbside.core
  :target-path "target/%s"
  :profiles {:uberjar {:aot :all}})
