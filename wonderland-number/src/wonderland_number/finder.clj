(ns wonderland-number.finder)

(defn same-digits? [i j]
  (let [f (comp frequencies str)]
    (= (f i) (f j))))

(defn wonderland-number-candidates []
  (for [n (range 100000 1000000)]
    (let [two-six (range 2 (inc 6))
          sd? (map #(same-digits? n (* n %)) two-six)]
      {:n n :same-digits? (every? true? sd?)})))

(defn wonderland-number []
  (:n (first (filter :same-digits? (wonderland-number-candidates)))))
