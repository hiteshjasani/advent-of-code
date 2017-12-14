(ns y2017.day05
  (:require
   [clojure.java.io :as io]
   [clojure.string :as s]))


(defn simple-offset-modifier
  [offset]
  (inc offset))

(defn stranger-offset-modifier
  [offset]
  (if (>= offset 3)
    (dec offset)
    (inc offset)))

(defn interpret-jumps
  "Instructions are a vector of jump offsets.
   for example: [0 3 0 1 -3]"
  [instructions offset-modifier-fn]
  (let [offsets (transient instructions)
        sz      (count instructions)]
    (loop [idx   0
           steps 1]
      (let [idx-offset (nth offsets idx)]
        (assoc! offsets idx (offset-modifier-fn idx-offset))
        (if (>= (+ idx idx-offset) sz)
          steps
          (recur (+ idx idx-offset) (inc steps)))))))

(defn part-1
  [jump-offsets]
  (interpret-jumps jump-offsets simple-offset-modifier))

(defn part-2
  [jump-offsets]
  (interpret-jumps jump-offsets stranger-offset-modifier))

(defn -main []
  (let [test-data [0 3 0 1 -3]]
    (println (format "p1-steps: %d  p2-steps: %d  processing %s"
                     (part-1 test-data)
                     (part-2 test-data)
                     (str test-data))))

  (let [jump-offsets (->> "y2017/day05/input"
                          io/resource
                          slurp
                          s/trim
                          s/split-lines
                          (mapv read-string))]
    (println "part 1 solution -> " (part-1 jump-offsets))
    (println "part 2 solution -> " (part-2 jump-offsets))))

