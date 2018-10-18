(ns y2017.day01
  (:require
   [clojure.java.io :as io]
   [clojure.string :as s]
   [y2017.util :as u]))

(defn peer-index
  "idx-size : int
   peer-offset : int (eg. 1 or (/ idx-size 2))
   my-idx-val : int
  "
  [idx-size peer-offset my-idx-val]
  (if (zero? idx-size)
    0
    (mod (+ my-idx-val peer-offset) idx-size)))

(defn count-peer-dups
  [digits peer-offset]
  (let [idx-size (count digits)]
    (->> digits
         (map-indexed (fn [idx x]
                        (if (= x
                               (nth digits
                                    (peer-index idx-size peer-offset idx)))
                          x
                          0)))
         (reduce + 0))))

(defn str->ints [s] (map #(- (int %) (int \0)) s))

(defn part-1
  [digits]
  (count-peer-dups digits 1))

(defn part-2
  [digits]
  (count-peer-dups digits (/ (count digits) 2)))

(defn -main []
  (let [test-data ["1122" "1111" "1234" "91212129"
                   "1212" "1221" "123425" "123123" "12131415"]]
    (doseq [s test-data]
      (let [digits (str->ints s)]
        (println (str s " -> p1: " (part-1 digits)
                      "  p2: " (part-2 digits))))))

  (let [digits (-> (u/load-res "day01")
                   str->ints)]
    (println "part 1 solution -> " (part-1 digits))
    (println "part 2 solution -> " (part-2 digits))
    ))
