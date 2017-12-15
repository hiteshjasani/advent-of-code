(ns y2017.day07
  (:require
   [clojure.java.io :as io]
   [clojure.string :as s]
   [clojure.set :as cs]))

(def test-input
  "pbga (66)
xhth (57)
ebii (61)
havc (66)
ktlj (57)
fwft (72) -> ktlj, cntj, xhth
qoyq (66)
padx (45) -> pbga, havc, qoyq
tknk (41) -> ugml, padx, fwft
jptl (61)
ugml (68) -> gyxo, ebii, jptl
gyxo (61)
cntj (57)")

(defn parse-line [line]
  (let [[_ prog weight] (re-find #"(\w+) \((\d+)\)" line)
        deps (or (some-> (re-find #"-> (.*)" line) second (s/split #",?\s"))
                 [])]
    {:name prog :weight (read-string weight) :children deps}))

#_(defn make-child-parent-map
  "Create a child (key) to parent (val) map"
  [progs]
  (->> progs
       (remove #(empty? (:children %)))
       (map (fn [x]
              (zipmap (:children x) (repeat (:name x)))))
       (apply merge)))

(defn child-names [progs]
  (->> progs
       (map :children)
       (remove empty?)
       (apply concat)
       vec))

(defn part-1 [input]
  (let [progs       (map parse-line input)
        names       (map :name progs)
        child-names (child-names progs)]
    (cs/difference (set names) (set child-names))))

(defn -main []
  (let [input (-> test-input s/trim s/split-lines)]
    (println (str "part 1 using test-input -> " (part-1 input)))
    #_(println (str "part 2 using test-input -> " (part-2 input))))

  (let [input (-> "y2017/day07/input" io/resource slurp s/trim s/split-lines)]
    (println "part 1 solution -> " (part-1 input))
    #_(println "part 2 solution -> " (part-2 input))
    ))
