# curbside

Clojure solution to the Curbside challenge using core.async...

## Installation

git clone https://github.com/sineer/curbside

## Usage

Here is the output running on an 3.4 GHz Intel Core i5 (4x CPUs):
```
 j@jD0G  ~/s/curbside   master  lein run
Hello Curbside!
Session ID:  4038f1f09f9541d3b3fde538210405d6
pipeline-async-crawler parallelism:  20
%:   0  Speed:    1  # of Pending HTTP GET Req:    60
%:   1  Speed:   59  # of Pending HTTP GET Req:   229
%:   3  Speed:  131  # of Pending HTTP GET Req:   441
%:   4  Speed:  112  # of Pending HTTP GET Req:   607
%:   5  Speed:  114  # of Pending HTTP GET Req:   802
%:   6  Speed:  130  # of Pending HTTP GET Req:  1009
%:   8  Speed:  113  # of Pending HTTP GET Req:  1172
%:   9  Speed:  131  # of Pending HTTP GET Req:  1365
%:  10  Speed:  112  # of Pending HTTP GET Req:  1599
%:  12  Speed:  129  # of Pending HTTP GET Req:  1796
%:  13  Speed:  113  # of Pending HTTP GET Req:  2007
%:  14  Speed:  130  # of Pending HTTP GET Req:  2225
%:  16  Speed:  114  # of Pending HTTP GET Req:  2430
%:  17  Speed:  128  # of Pending HTTP GET Req:  2625
%:  18  Speed:  118  # of Pending HTTP GET Req:  2819
%:  19  Speed:  118  # of Pending HTTP GET Req:  3009
%:  21  Speed:  124  # of Pending HTTP GET Req:  3202
%:  22  Speed:  121  # of Pending HTTP GET Req:  3399
%:  23  Speed:  127  # of Pending HTTP GET Req:  3602
%:  25  Speed:  115  # of Pending HTTP GET Req:  3795
%:  26  Speed:  130  # of Pending HTTP GET Req:  4016
%:  27  Speed:  115  # of Pending HTTP GET Req:  4202
%:  29  Speed:  129  # of Pending HTTP GET Req:  4412
%:  30  Speed:  116  # of Pending HTTP GET Req:  4617
%:  31  Speed:  129  # of Pending HTTP GET Req:  4821
%:  33  Speed:  115  # of Pending HTTP GET Req:  4991
%:  34  Speed:  130  # of Pending HTTP GET Req:  5180
%:  35  Speed:  114  # of Pending HTTP GET Req:  5376
%:  37  Speed:  119  # of Pending HTTP GET Req:  5583
%:  38  Speed:  123  # of Pending HTTP GET Req:  5768
%:  39  Speed:  117  # of Pending HTTP GET Req:  5964
%:  40  Speed:  125  # of Pending HTTP GET Req:  5901
%:  42  Speed:  115  # of Pending HTTP GET Req:  5771
%:  43  Speed:  120  # of Pending HTTP GET Req:  5640
%:  44  Speed:  121  # of Pending HTTP GET Req:  5512
%:  46  Speed:  115  # of Pending HTTP GET Req:  5383
%:  47  Speed:  121  # of Pending HTTP GET Req:  5253
%:  48  Speed:  120  # of Pending HTTP GET Req:  5129
%:  49  Speed:  120  # of Pending HTTP GET Req:  4997
%:  51  Speed:  123  # of Pending HTTP GET Req:  4865
%:  52  Speed:  110  # of Pending HTTP GET Req:  4742
%:  53  Speed:  116  # of Pending HTTP GET Req:  4620
%:  55  Speed:  112  # of Pending HTTP GET Req:  4488
%:  56  Speed:  115  # of Pending HTTP GET Req:  4356
%:  57  Speed:  127  # of Pending HTTP GET Req:  4224
%:  58  Speed:  112  # of Pending HTTP GET Req:  4102
%:  60  Speed:  121  # of Pending HTTP GET Req:  3979
%:  61  Speed:  121  # of Pending HTTP GET Req:  3847
%:  62  Speed:  111  # of Pending HTTP GET Req:  3722
%:  63  Speed:  115  # of Pending HTTP GET Req:  3599
%:  65  Speed:  115  # of Pending HTTP GET Req:  3471
%:  66  Speed:  113  # of Pending HTTP GET Req:  3350
%:  67  Speed:  128  # of Pending HTTP GET Req:  3218
%:  69  Speed:  112  # of Pending HTTP GET Req:  3086
%:  70  Speed:  114  # of Pending HTTP GET Req:  2972
%:  71  Speed:  128  # of Pending HTTP GET Req:  2842
%:  72  Speed:  113  # of Pending HTTP GET Req:  2709
%:  74  Speed:  115  # of Pending HTTP GET Req:  2584
%:  75  Speed:  128  # of Pending HTTP GET Req:  2461
%:  76  Speed:  115  # of Pending HTTP GET Req:  2329
%:  77  Speed:  113  # of Pending HTTP GET Req:  2197
%:  79  Speed:  117  # of Pending HTTP GET Req:  2079
%:  80  Speed:  113  # of Pending HTTP GET Req:  1949
%:  81  Speed:  125  # of Pending HTTP GET Req:  1826
%:  82  Speed:  115  # of Pending HTTP GET Req:  1698
%:  84  Speed:  115  # of Pending HTTP GET Req:  1566
%:  85  Speed:  114  # of Pending HTTP GET Req:  1447
%:  86  Speed:  111  # of Pending HTTP GET Req:  1323
%:  88  Speed:  110  # of Pending HTTP GET Req:  1191
%:  89  Speed:  112  # of Pending HTTP GET Req:  1079
%:  90  Speed:  130  # of Pending HTTP GET Req:   948
%:  91  Speed:  112  # of Pending HTTP GET Req:   825
%:  93  Speed:  111  # of Pending HTTP GET Req:   700
%:  94  Speed:  122  # of Pending HTTP GET Req:   571
%:  95  Speed:  121  # of Pending HTTP GET Req:   439
%:  96  Speed:  114  # of Pending HTTP GET Req:   310
%:  98  Speed:  120  # of Pending HTTP GET Req:   180
%:  99  Speed:  120  # of Pending HTTP GET Req:    49
Output-chan processing loop Finished!
Total # of HTTP GET Req:  9978
SECRET KEY:  60da7c596820e0ccafa16b9971e5753eaa437eb36ff262184af24e2c61f8f657
Time spent processing the challenge (in milliseconds):  84584
Validating the key...
Key is Valid! Success!!!
```
