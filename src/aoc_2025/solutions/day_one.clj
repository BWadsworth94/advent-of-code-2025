(ns solutions.day-one
  (:require
   [aoc-2025.util :refer [read-input-file]]
   [clojure.string :as str]))

(defn- parse-instructions
  "Produce a seq of [dir degree]; 'L32' => [['L', 32]]"
  [input]
  (->> input str/split-lines
       (map
        (comp (fn [[_ dir degree]]
                [dir (parse-long degree)])
              #(re-matches #"([L|R])(\d+)" %)))))

(defn- rotate-dial
  "'Rotates' the dial by the current instructions direction and degree.
   (52 [L 3]) => 49; does not wrap at 0 or 100."
  [dial instruction]
  (let [[dir degree] instruction]
    (if (= "L" dir)
      (- dial degree)
      (+ dial degree))))

(defn- zero-point?
  "Not handling wrapping of the dial means we can just check for a point to zero
   by modding the current dial position by 100."
  [dial]
  (zero? (mod dial 100)))

(defn part-one
  [input]
  (let [instructions (parse-instructions input)]
    (loop [zero-points-cnt 0
           dial 50
           instructions instructions]
      (if-not (first instructions)
        zero-points-cnt
        (let [new-dial (rotate-dial dial (first instructions))]
          (recur (if (zero-point? dial) (inc zero-points-cnt) zero-points-cnt)
                 new-dial
                 (rest instructions)))))))

(comment
  (part-one (read-input-file "one")))