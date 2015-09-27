(ns alphabet-cipher.garbage)

;(def alphabet
;  (->>
;   (range (int \a) (inc (int \z)))
;   (map char)
;   (apply sorted-set)))

;(def substitution-chart
;  (vec
;   (for [letter alphabet]
;     (apply conj [] (rotated-letters letter)))))


;(defn- rotated-letters [start-letter]
;  {:pre [(and
;          (char? start-letter)
;          (contains? alphabet start-letter))]}
;  (let [left-seq (range (int start-letter) (inc (int \z)))
;        right-seq (range (int \a) (int start-letter))
;        combined (concat left-seq right-seq)]
;    (vec (map char combined))))

;(defonce letter-pair-map
;  (let [pairs
;        (for [ltr1 alphabet
;              ltr2 alphabet]
;          {[ltr1 ltr2] (get-by-letter-idx (letters ltr1) ltr2)})]
;    (apply merge {} pairs)))

;(defn letter-seq2 [start-letter]
;  (let [iterate-fn (fn [x]
;                     (-> x
;                         (inc)
;                         (mod 26)))
;        start-x (- (int start-letter) (int \a))]
;    (->> (take 26 (iterate iterate-fn start-x))
;         (map #(+ 97 %))
;         (map char))))

