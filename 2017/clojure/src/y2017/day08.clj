(ns y2017.day08
  (:require
   [clojure.java.io :as io]
   [clojure.string :as s]
   [clojure.set :as cs]
   [clojure.pprint :as pp]
   [y2017.util :as u]))

(def test-input
  "b inc 5 if a > 1
a inc 1 if b < 5
c dec -10 if a >= 1
c inc -20 if c == 10")

(defn parse-line [line]
  (let [pieces (s/split line #"\s+")]
    {:register (nth pieces 0)
     :op (nth pieces 1)
     :val (nth pieces 2)
     :pred {:reg (nth pieces 4) :comp (nth pieces 5) :val (nth pieces 6)}}))

(defn comp-fn
  [s]
  (case s
    "<"  #'<
    ">"  #'>
    "==" #'=
    "!=" #'not=
    ">=" #'>=
    "<=" #'<=))

(defn historical-max-register-value
  [state]
  (let [old-max (:max-register-val-seen state)
        new-max (-> state
                    (dissoc :max-register-val-seen)
                    vals
                    (->> (apply max)))]
    (max old-max new-max)))

(defn max-register-value
  [state]
  (-> state
      (dissoc :max-register-val-seen)
      vals
      (->> (apply max))))

(defn run-instruction
  "{register,val} -> [{:register, :op, ...}] -> {register,val}"
  [state instruction]
  (let [comp      (comp-fn (get-in instruction [:pred :comp]))
        comp-reg  (or (get state (get-in instruction [:pred :reg])) 0)
        comp-val  (read-string (get-in instruction [:pred :val]))
        update-op (case (:op instruction)
                    "inc" #'+
                    "dec" #'-)
        update-fn (fn [val]
                    (update-op (or val 0) (read-string (:val instruction))))]
  (if (comp comp-reg comp-val)
    (as-> state m
        (update m (:register instruction) update-fn)
        (assoc m :max-register-val-seen (historical-max-register-value m)))
    state)))

(defn interpreter
  "[{:register, :op, ...}] -> {register,val}"
  [instructions]
  (loop [state {:max-register-val-seen java.lang.Integer/MIN_VALUE}
         instr instructions]
    (if (empty? (rest instr))
      (run-instruction state (first instr))
      (recur (run-instruction state (first instr)) (rest instr)))))

(defn part-1 [instructions]
  (let [ret (interpreter instructions)]
    (max-register-value ret)))

(defn part-2 [instructions]
  (let [ret (interpreter instructions)]
    (historical-max-register-value ret)))

(defn -main []
  (let [input (->> test-input s/trim s/split-lines (map parse-line))]
    (println (str "part 1 using test-input -> " (part-1 input)))
    (println (str "part 2 using test-input -> " (part-2 input))))

  (let [input (->> (u/load-res "day08")
                   s/split-lines
                   (map parse-line))]
    (println "part 1 solution -> " (part-1 input))
    (println "part 2 solution -> " (part-2 input))))
