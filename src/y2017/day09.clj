(ns y2017.day09
  (:require
   [clojure.core.match :refer [match]]
   [y2017.util :as u]))

(defn begin-group
  [state]
  (let [new-group-level (inc (:group-level state))]
    (-> state
        (update :mode conj :in-group)
        (assoc :group-level new-group-level)
        (update :group-score + new-group-level))))

(defn end-group
  [state]
  (-> state
      (update :mode pop)
      (update :group-level dec)))

(defn begin-garbage
  [state]
  (update state :mode conj :in-garbage))

(defn end-garbage
  [state]
  (update state :mode pop))

(defn count-garbage
  [state]
  (update state :garbage-chars inc))

(defn begin-ignore
  [state]
  (update state :mode conj :ignore))

(defn end-ignore
  [state]
  (update state :mode pop))

(defn interpreter
  [state c]
  (match [(peek (:mode state)) c]
         [:ignore      _] (end-ignore state)
         [:in-garbage \!] (begin-ignore state)
         [:in-garbage \>] (end-garbage state)
         [:in-garbage  _] (count-garbage state)
         [:in-group   \}] (end-group state)
         [_           \,] state
         [_           \{] (begin-group state)
         [_           \<] (begin-garbage state)))

(defn process
  [char-stream]
  (reduce interpreter
          {:mode          [:none]
           :group-level   0
           :group-score   0
           :garbage-chars 0}
          char-stream))

(defn -main []
  (let [input (u/load-res "y2017/day09/input")]
    (println "part 1/2 solution -> " (process input))))
