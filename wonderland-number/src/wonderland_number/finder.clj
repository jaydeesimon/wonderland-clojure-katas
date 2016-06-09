(ns wonderland-number.finder)

(defn same-digits? [i j]
  (let [f (comp frequencies str)]
    (= (f i) (f j))))

(defn wonderland-number-candidates []
  (let [two-six (range 2 (inc 6))]
    (for [n (range 100000 1000000)]
      (let [mults-same-digits (map #(same-digits? n (* n %)) two-six)]
        {:n n :same-digits? (every? true? mults-same-digits)}))))

(defn wonderland-number []
  (->> (wonderland-number-candidates)
       (filter :same-digits?)
       (first)
       :n))
