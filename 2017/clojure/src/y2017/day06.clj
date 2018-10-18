(ns y2017.day06
  (:require
   [clojure.java.io :as io]
   [clojure.string :as s]
   [clojure.set :as cs]
   [y2017.util :as u]))

(defn spread
  [arr idx]
  (let [marr     (transient arr)
        total    (nth arr idx)
        sz       (count arr)
        next-idx (fn [idx] (mod (inc idx) sz))]
    (assoc! marr idx 0)
    (loop [cur (next-idx idx)
           rem total]
      (when (> rem 0)
        (assoc! marr cur (inc (nth marr cur)))
        (recur (next-idx cur) (dec rem))))
    (persistent! marr)))

(defn highest-value-index
  [arr]
  (->> arr
       (reduce (fn [acc x]
                 (let [ctr (:ctr acc)]
                   (if (> x (:max acc))
                     {:max x :idx ctr :ctr (inc ctr)}
                     (assoc acc :ctr (inc ctr)))))
               {:ctr 0 :max java.lang.Integer/MIN_VALUE})
       :idx))

(defn run-cycles
  [arr seen-accum-fn]
  (loop [seen #{}
         i    0
         arr  arr]
    (if (cs/subset? (set [arr]) seen)
      [i arr]
      (recur (seen-accum-fn seen arr)
             (inc i)
             (spread arr (highest-value-index arr))))))

(defn accum-seen [seen arr] (cs/union seen (set [arr])))

(defn part-1 [arr]
  (-> (run-cycles arr accum-seen)
      first))

(defn part-2
  [arr]
  (let [[_ seen] (run-cycles arr accum-seen)]
    (-> (run-cycles seen (constantly (set [seen])))
        first)))

(defn -main []
  (let [input [0 2 7 0]]
    (println (str "part 1 using " input " -> " (part-1 input)))
    (println (str "part 2 using " input " -> " (part-2 input))))

  (let [input (-> (u/load-res "day06")
                  (s/split #"\s+")
                  (->> (mapv read-string)))]
    (println "part 1 solution -> " (part-1 input))
    (println "part 2 solution -> " (part-2 input))))
