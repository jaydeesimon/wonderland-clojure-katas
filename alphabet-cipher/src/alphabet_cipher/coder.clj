(ns alphabet-cipher.coder
  (:require [clojure.string :refer [join]]))

(def ascii-offset (int \a))

(def alphabet "abcdefghijklmnopqrstuvwxyz")

(defn- rotations [coll]
  (take (count coll) (partition (count coll) 1 (cycle coll))))

(def substitution-chart
  (vec (map vec (rotations alphabet))))

(defn- letter->idx [letter]
  (- (int letter) ascii-offset))

(defn- encode-letter [letter keyword-letter]
  (let [row (letter->idx letter)
        col (letter->idx keyword-letter)]
    (get-in substitution-chart [row col])))

(defn- decode-letter [letter keyword-letter]
  (let [row-idx (letter->idx keyword-letter)
        row (get substitution-chart row-idx)
        letter-idx (.indexOf row letter)
        ascii-letter-idx (+ letter-idx ascii-offset)]
    (char ascii-letter-idx)))

(defn- first-repeat [coll]
  (loop [partitioned (partition 1 coll)]
    (let [fp (first partitioned)
          sp (second partitioned)]
      (if (or
           (= fp sp)
           (= (count partitioned) 1))
        fp
        (recur
         (partition (inc (count fp)) coll))))))

(defn encode [keyword message]
  (let [keyword-repeated (take (count message) (cycle keyword))]
    (join (map encode-letter message keyword-repeated))))

(defn decode [keyword message]
  (let [keyword-repeated (take (count message) (cycle keyword))]
    (join (map decode-letter message keyword-repeated))))

(defn decypher [cypher message]
  (-> (map decode-letter cypher message)
      (first-repeat)
      (join)))
