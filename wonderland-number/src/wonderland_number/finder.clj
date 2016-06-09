(ns wonderland-number.finder)

(defn same-digits? [i j]
  (let [f (comp frequencies str)]
    (= (f i) (f j))))

(defn wonderland-number-candidates []
  (for [n (range 100000 1000000)]
    (let [mults (map (partial * n) (range 2 (inc 6)))
          mults-same-digits (map (partial same-digits? n) mults)]
      {:n n :same-digits? (every? true? mults-same-digits)})))

(defn wonderland-number []
  (->> (wonderland-number-candidates)
       (filter :same-digits?)
       (first)
       :n))
