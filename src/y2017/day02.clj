(ns y2017.day02
  (:require
   [clojure.java.io :as io]
   [clojure.string :as s]))


(defn parse-spreadsheet
  [s]
  (->> (s/split-lines s)
       (map #(s/split % #"\s"))
       (map (fn [row]
              (map read-string row)))
       ))

(defn min-max
  [row]
  (reduce (fn [[minval maxval] x]
            [(if (< x minval) x minval)
             (if (> x maxval) x maxval)])
          [java.lang.Integer/MAX_VALUE java.lang.Integer/MIN_VALUE]
          row))

(defn min-max-diff-op
  [row]
  (let [[minval maxval] (min-max row)]
    (- maxval minval)))

(defn evenly-divisible-op
  [row]
  (->> row
       (map-indexed (fn [idx-x x]
                      (->> row
                           (map-indexed (fn [idx-y y]
                                          (if (and (not= idx-x idx-y)
                                                   (zero? (mod x y)))
                                            (/ x y)
                                            nil)))
                           (remove nil?)
                           )))
       (remove empty?)
       first
       first
       ))

(defn checksum
  [spreadsheet row-op]
  (->> spreadsheet
       (map row-op)
       (reduce + 0)))

(defn part-1
  [spreadsheet]
  (checksum spreadsheet min-max-diff-op))
  
(defn part-2
  [spreadsheet]
  (checksum spreadsheet evenly-divisible-op))
  
(defn -main []
  (let [test-data (parse-spreadsheet "5 1 9 5\n7 5 3\n2 4 6 8\n")]
    (println (str "test:  p1: " (part-1 test-data))))

  (let [test-data (parse-spreadsheet "5 9 2 8\n9 4 7 3\n3 8 6 5\n")]
    (println (str "test:  p2: " (part-2 test-data))))

  (let [spreadsheet (-> "y2017/day02/input" io/resource slurp s/trim
                        parse-spreadsheet)]
    (println "part 1 solution -> " (part-1 spreadsheet))
    (println "part 2 solution -> " (part-2 spreadsheet))
    ))
