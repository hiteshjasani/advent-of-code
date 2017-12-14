(ns y2017.day03
  (:require
   [clojure.java.io :as io]
   [clojure.string :as s]))

(def n-chain (map #(into [] [% (* % %)]) (range 1 10000000 2)))

(defn ring
  "Returns the ring and max value.

   (ring 2)
   => [3 9]
  "
  [val]
  (->> n-chain
       (drop-while #(< (second %) val))
       first))

(defn bottom-right
  [val]
  (-> (ring val)
      second))

(defn bottom-center
  [val]
  (let [[n n2] (ring val)
        n-1    (dec n)]
    (- n2 (/ n-1 2))))

(defn left-center
  [val]
  (let [[n n2] (ring val)
        n-1    (dec n)]
    (- n2
       (+ (/ n-1 2) (* 1 n-1)))))

(defn top-center
  [val]
  (let [[n n2] (ring val)
        n-1    (dec n)]
    (- n2
       (+ (/ n-1 2) (* 2 n-1)))))

(defn right-center
  [val]
  (let [[n n2] (ring val)
        n-1    (dec n)]
    (- n2
       (+ (/ n-1 2) (* 3 n-1)))))

(defn closest-center-dist
  [val]
  (let [centers ((juxt top-center right-center bottom-center left-center) val)]
    (->> centers
         (map #(Math/abs (- val %)))
         (apply min))))

(defn ring-dist
  [val]
  (let [[n _] (ring val)]
    (/ (dec n) 2)))

(defn dist
  [val]
  (->> val
       ((juxt closest-center-dist ring-dist))
       (reduce + 0)))

(defn part-1
  [val]
  (dist val))
  
 
(defn test-val
  [val]
  (println (format "\n+--%7d   --+" (top-center val)))
  (println (format "|              |"))
  (println (format "%4d  %4d  %4d" (left-center val) val (right-center val)))
  (println (format "|              |"))
  (println (format "+--%7d  %4d" (bottom-center val) (bottom-right val))))

(defn -main []
  (let [test-data [1 9 8 12 24 25 69]]
    (doseq [val test-data]
      (test-val val)))

  (println "part 1 solution -> " (part-1 325489))
  )

;; 101 100  99  98  97  96  95  94  93  92  91
;; 102  65  64  63  62  61  60  59  58  57  90
;; 103  66  37  36  35  34  33  32  31  56  89
;; 104  67  38  17  16  15  14  13  30  55  88
;; 105  68  39  18   5   4   3  12  29  54  87
;; 106  69  40  19   6   1   2  11  28  53  86
;; 107  70  41  20   7   8   9  10  27  52  85
;; 108  71  42  21  22  23  24  25  26  51  84
;; 109  72  43  44  45  46  47  48  49  50  83
;; 110  73  74  75  76  77  78  79  80  81  82
;; 111 112 113 114 115 116 117 118 119 120 121 122

;; observations
;; o bot-right corner is odd squared, eg. 3^2=9, 5^2=25, 7^2=49, 9^2=81,
;;                                        11^2=121

;; o 81 = ring 9
;; o 73 = ring 9^2 (81) - (n-1) = bot-left corner
;; o 65 = ring 9^2 (81) - 2*(n-1) = top-left corner
;; o 57 = ring 9^2 (81) - 3*(n-1) = top-right corner

;; o 77 = ring 9^2 (81) - [4] (n-1)/2 = bottom center
;; o 69 = ring 9^2 (81) - [12] (n-1)/2 + (n-1) = left center
;; o 61 = ring 9^2 (81) - [20] (n-1)/2 + 2*(n-1) = top center
;; o 53 = ring 9^2 (81) - [28] (n-1)/2 + 3*(n-1) = right center

