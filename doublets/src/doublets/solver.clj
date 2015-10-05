(ns doublets.solver
  (:require [clojure.java.io :as io]
            [clj-fuzzy.levensthein :refer [distance]]
            [loom.graph :refer [graph add-edges]]
            [loom.alg :refer [bf-path]]))

(def word-link-file-base "%d-letter-word-links.edn")

(defn word-link-file [word-len]
  (io/resource (format word-link-file-base word-len)))

(defn word-links [word-len]
  (if-let [word-link-file (word-link-file word-len)]
    (read-string (slurp word-link-file))
    []))

(defn word-link-graph [word-len]
  (let [word-links (word-links word-len)]
    (apply graph (map vec word-links))))

(defonce graphs
  {3 (word-link-graph 3)
   4 (word-link-graph 4)
   5 (word-link-graph 5)})

(defonce empty-graph (graph))

(defn doublets [word1 word2]
  (if (= (count word1) (count word2))
    (let [g (get graphs (count word1) empty-graph)]
      (vec (bf-path g word1 word2)))
    []))
