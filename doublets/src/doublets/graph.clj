(ns doublets.graph
  (:require [clojure.java.io :as io]
            [clj-fuzzy.levensthein :refer [distance]]))

(def dictionary-file (io/resource "dictionary.txt"))

(def re-word #"^[A-Z]+$")

(defn- line-seq->words [line-seq]
  (reduce
   (fn [words line]
     (if-let [word-match (re-matches re-word line)]
       (conj words word-match)
       words))
   #{} line-seq))

(defn- dictionary->set
  "Convert the dictionary file to a set with only the dictionary words."
  []
  (with-open
   [xin (io/reader dictionary-file)]
    (line-seq->words (line-seq xin))))

(defn- words-of-len
  "Given a collection of dictionary words, get only the words of a certain
  length."
  [words len]
  (let [grouped (group-by count words)]
    (get grouped len)))

(defn- word-links
  "Given a colleciton of word pairs, keep the ones that have a levenshenstein
  distance of exactly 1. In the doublet world, this means they link. We can
  use these pairs to build a graph."
  [pairs]
  (filter
   (fn [pair] (= (apply distance pair) 1))
   pairs))

(defn- all-pairs
  "Given a list of words, return a distinct pair. The result of this could
  be huge obviously. I'm sure there's a better way to do this."
  [words]
  (distinct
   (for [i words
         j words
         :when (not= i j)]
     (set [i j]))))

(defn write-to-file
  "This defn ties everything together and writes the results to a file."
  [filename word-len]
  (let [all-words (dictionary->set)
        words-len (words-of-len all-words word-len)
        pairs (all-pairs words-len)
        word-links (word-links pairs)]
    (spit filename (with-out-str (pr word-links)))))
