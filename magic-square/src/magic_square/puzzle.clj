(ns magic-square.puzzle
  (:require [clojure.math.combinatorics :as c]))

(def values [1.0 1.5 2.0 2.5 3.0 3.5 4.0 4.5 5.0])

(defn rows [n values]
  (partition n values))

(defn cols [n values]
  (let [partitioned (partition n values)]
    (for [row (range n)]
      (map #(nth % row) partitioned))))

(defn diags [n values]
  (let [partitioned (partition n values)]
    [(map #(nth %1 %2) partitioned (range n))
     (map #(nth %1 %2) partitioned (range (dec n) (dec 0) -1))]))

(defn magic-square? [n values]
  (let [group-nums (concat (rows n values)
                           (cols n values)
                           (diags n values))
        sum (fn [l] (reduce + l))]
    (apply = (map sum group-nums))))

(defn magic-square [values]
  (let [n 3]
    (->> (c/permutations values)
         (filter (partial magic-square? n))
         (first)
         (partition n)
         (map vec)
         (vec))))
