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
                            #(re-matches #"([L|R])(\d+)" %))))]
    (reduce
     (fn [[landings curr-degree :as acc] [move degree]]
       (let [new-degree (if (= move "R")
                          (+ degree curr-degree)
                          (- degree curr-degree))]
         ())
       [[] 50]
       instructions)))

(comment
  (part-one (read-input-file "one"))
  (part-one "L68
L30
R48
L5
R60
L55
L1
L99
R14
L82"))

;; 20 19 40 70 90 110 90

(mod 200 100)

(re-matches #"([L|R])(\d+)" "L3")