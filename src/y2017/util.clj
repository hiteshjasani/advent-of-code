(ns y2017.util)

(defn indent [level] (apply str (repeat level "  ")))

(defn matching-values?
  "Eq => a :: [a] -> bool

   Test that every member of collection is equal"
  [coll]
  (every? #(= (first coll) %) coll))
