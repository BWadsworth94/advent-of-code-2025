(ns solutions.day-one
  (:require
   [aoc-2025.util :refer [read-input-file]]
   [clojure.string :as str]))

(defn part-one
  [input]
  (let [instructions (->> input
                          str/split-lines
                          (map
                           (comp
                            (fn [[_ move degree]]
                              [move (parse-long degree)])
                            #(re-matches #"([L|R])(\d+)" %))))
        {:keys [landings]} (reduce
                            (fn [{:keys [current-position] :as acc} [move degree]]
                              (let [new-position (if (= move "R")
                                                   (+ current-position degree)
                                                   (- current-position degree))]
                                (-> acc
                                    (update :landings conj new-position)
                                    (assoc :current-position new-position))))
                            {:landings []
                             :current-position 50}
                            instructions)]
    (->> landings (filter #(zero? (mod % 100))) count)))

(comment
  (part-one (read-input-file "one")))