(ns card-game-war.game)

(def suits [:spade :club :diamond :heart])

(def ranks [2 3 4 5 6 7 8 9 10 :jack :queen :king :ace])

(def cards
  (for [suit suits
        rank ranks]
    [suit rank]))

(defn- rank [card]
  (get card 1))

(defn- rank->idx [rank]
  (.indexOf ranks rank))

(defn- rank-diff [rank1 rank2]
  (- (rank->idx rank1) (rank->idx rank2)))

(defn- suit->idx [suit]
  (.indexOf suits suit))

(defn- suit [card]
  (get card 0))

(defn- winner-by-suit [player1-card player2-card]
  (let [suit-idx1 (suit->idx (suit player1-card))
        suit-idx2 (suit->idx (suit player2-card))]
    (if (pos? (- suit-idx1 suit-idx2))
      player1-card
      player2-card)))

(defn- winner [player1-card player2-card]
  (let [player1-rank (rank player1-card)
        player2-rank (rank player2-card)
        rank-diff (rank-diff player1-rank player2-rank)]
    (cond
      (pos? rank-diff) player1-card
      (neg? rank-diff) player2-card
      :else (winner-by-suit player1-card player2-card))))

(defn- add-cards-to-bottom
  "Adds cards to the bottom of the deck but shuffles them before adding.
  If the cards are always added back to the winning deck in the same order,
  then the game never ends."
  [deck & cards]
  (apply conj deck (shuffle cards)))

(defn- play-game [player1-cards player2-cards]
  (loop [player1-cards (apply conj (clojure.lang.PersistentQueue/EMPTY) player1-cards)
         player2-cards (apply conj (clojure.lang.PersistentQueue/EMPTY) player2-cards)]
    (cond
      (empty? player1-cards) :player2-wins
      (empty? player2-cards) :player1-wins
      :else
        (let [player1-card (peek player1-cards)
              player2-card (peek player2-cards)
              winning-card (winner player1-card player2-card)
              _ (println (str "player1: " player1-card " -- player2: " player2-card " -- winner: " winning-card " -- player1cardsize=" (count player1-cards) " -- player2cardsize=" (count player2-cards)))]
          (if (= winning-card player1-card)
            (recur (add-cards-to-bottom (pop player1-cards) player1-card player2-card) (pop player2-cards))
            (recur (pop player1-cards) (add-cards-to-bottom (pop player2-cards) player1-card player2-card)))))))

(defn war []
  (let [shuffled (shuffle cards)
        piles (partition (/ (count cards) 2) shuffled)
        player1-cards (first piles)
        player2-cards (second piles)]
    (play-game player1-cards player2-cards)))