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

(defn- ->zero-passes
  "Checks every number between old-dial and new-dial to see
   if n % 100 = 0, and if it does inc zero count.
   if old-dial is = 0 to prevent double counting, it gets shifted
   pos or neg towards new-dial.
   
   Maybe sometime soon ill actually try to solve this idomatically"
  [old-dial new-dial]
  ;; first impl real dum lol
  (let [off-0-old-dial (if (zero? (mod old-dial 100))
                         (if (< old-dial new-dial)
                           (inc old-dial)
                           (dec old-dial))
                         old-dial)
        [x y] (sort [off-0-old-dial new-dial])]
    (reduce
     (fn [acc i]
       (if (zero? (mod i 100))
         (inc acc)
         acc))
     0
     (range x (inc y)))))

(defn part-two
  [input]
  (let [instructions (parse-instructions input)]
    (loop [zero-hit-cnt 0
           dial 50
           instructions instructions]
      (if-not (first instructions)
        zero-hit-cnt
        (let [new-dial (rotate-dial dial (first instructions))
              zero-passes (->zero-passes dial new-dial)]
          (recur (+ zero-hit-cnt zero-passes)
                 new-dial
                 (rest instructions)))))))

(comment
  (part-one (read-input-file "one"))
  (part-two (read-input-file "one")))