(ns y2017.util
  (:require [clojure.java.io :as io]
            [clojure.string :as s]))

(defn indent [level] (apply str (repeat level "  ")))

(defn matching-values?
  "Eq => a :: [a] -> bool

   Test that every member of collection is equal"
  [coll]
  (every? #(= (first coll) %) coll))

(defn load-res
  "Load file from classpath"
  [filepath]
  (->> filepath
       io/resource
       slurp
       s/trim))
