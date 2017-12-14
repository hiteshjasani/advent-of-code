(ns y2017.day04
  (:require
   [clojure.java.io :as io]
   [clojure.string :as s]))

(defn valid?
  [passphrase]
  (let [chunks (s/split passphrase #"\s")]
    (= (count chunks) (count (set chunks)))))

(defn part-1
  [passphrases]
  (->> passphrases
       (map valid?)
       (filter true?)
       count))

(defn -main []
  (let [test-data ["aa bb cc dd ee"
                   "aa bb cc dd aa"
                   "aa bb cc dd aaa"]]
        (doseq [data test-data]
          (println (format "valid? %s  : %s" (str (valid? data)) data))
          ))

  (let [passphrases (-> "y2017/day04/input" io/resource slurp s/trim
                        s/split-lines)]
    (println "part 1 solution -> " (part-1 passphrases))
    )
  )
