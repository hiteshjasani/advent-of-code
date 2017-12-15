(ns y2017.day07
  (:require
   [clojure.java.io :as io]
   [clojure.string :as s]
   [clojure.set :as cs]
   [clojure.pprint :as pp]
   [y2017.util :as u]))

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
        child-names     (or (some-> (re-find #"-> (.*)" line) second
                                    (s/split #",?\s"))
                            [])]
    {:name prog :weight (read-string weight) :child-names child-names}))

(defn make-tree [prog-map seed-name]
  (let [cur-node (get prog-map seed-name)]
    (if (empty? (:child-names cur-node))
      cur-node
      (assoc cur-node :children (mapv #(make-tree prog-map %)
                                      (:child-names cur-node))))))

(defn check-balance
  "Check the balance of children of the tree"
  [tree level]
  (if (empty? (:children tree))
    (:weight tree)
    (let [child-weights (->> (:children tree)
                             (mapv #(into {} [[(:name %)
                                               (check-balance % (inc level))]]))
                             (apply merge))]
      (when-not (u/matching-values? (vals child-weights))
        (println (str (u/indent level) (:name tree) " is imbalanced with "
                      child-weights)))
      (apply + (:weight tree) (vals child-weights)))))

(defn child-names
  "[{:name :child-names ...}] -> [child-names]"
  [progs]
  (->> progs
       (map :child-names)
       (remove empty?)
       (apply concat)
       vec))

(defn prog-map
  "[{:name :child-names ...}] -> {:name {:name :weight :child-names}}"
  [progs]
  (->> progs
       (map (fn [x]
              {(:name x) x}))
       (apply merge)))

(defn find-root
  "[{:name ...}] -> :name"
  [progs]
  (let [names       (map :name progs)
        child-names (child-names progs)]
    (-> (cs/difference (set names) (set child-names))
        first)))

(defn part-1 [progs] (find-root progs))

(defn part-2 [progs]
  (let [root-name (find-root progs)
        tree      (make-tree (prog-map progs) root-name)]
    (check-balance tree 1)))

(defn -main []
  (let [progs (->> test-input s/trim s/split-lines (map parse-line))]
    (println (str "part 1 using test-input -> " (part-1 progs)))
    (println "part 2 using test-input -> ")
    (part-2 progs))

  (let [progs (->> "y2017/day07/input" io/resource slurp s/trim s/split-lines
                   (map parse-line))]
    (println "part 1 solution -> " (part-1 progs))
    (println "part 2 solution -> ")
    (part-2 progs)
    ))
