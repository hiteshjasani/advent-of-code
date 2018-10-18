(ns y2017.day04
  (:require
   [clojure.java.io :as io]
   [clojure.string :as s]
   [y2017.util :as u]))

(defn valid?
  [passphrase]
  (let [chunks (s/split passphrase #"\s")]
    (= (count chunks) (count (set chunks)))))

(defn anagrams?
  "Test if the passphrase contains anagrams."
  [passphrase]
  (let [chunks (s/split passphrase #"\s")]
    (not= (count chunks)
          (->> chunks
               (map frequencies)
               set
               count))))

(defn part-1
  [passphrases]
  (->> passphrases
       (map valid?)
       (filter true?)
       count))

(defn part-2
  [passphrases]
  (->> passphrases
       (map anagrams?)
       (remove true?)
       count))

(defn -main []
  (let [test-data ["aa bb cc dd ee"
                   "aa bb cc dd aa"
                   "aa bb cc dd aaa"
                   "abcde fghij"
                   "abcde xyz ecdab"
                   "a ab abc abd abf abj"
                   "iiii oiii ooii oooi oooo"
                   "oiii ioii iioi iiio"
                   ]]
        (doseq [data test-data]
          (println (format "no dupes? %s  anagrams? %s  : %s"
                           (str (valid? data))
                           (str (anagrams? data))
                           data))
          ))

  (let [passphrases (-> (u/load-res "day04")
                        s/split-lines)]
    (println "part 1 solution -> " (part-1 passphrases))
    (println "part 2 solution -> " (part-2 passphrases))
    )
  )
