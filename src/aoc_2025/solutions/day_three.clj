(ns aoc-2025.solutions.day-three 
  (:require
   [clojure.string :as str]
   [aoc-2025.util :refer [read-input-file]]))

(defn- get-max-tuple
  [battery-row]
  (let [digits (map parse-long (str/split battery-row #""))
        sorted-digits (->> digits ((partial sort >)))
        xs-past-max-digit (loop [digits digits]
                            (if (= (first sorted-digits) (first digits))
                              (rest digits)
                              (recur (rest digits))))]
    ;; if there are no elements after the max number, it's at the end of the list
    ;; and the second max digit would be before it
    (if-not (seq xs-past-max-digit)
      (reverse (take 2 sorted-digits))
      [(first sorted-digits) (apply max xs-past-max-digit)])))

(defn- combine-ints
  [[x y]]
  (parse-long (format "%d%d" x y)))

(defn part-one
  [input]
  (let [battery-rows (str/split-lines input)]
    (apply +
           (map
            (comp combine-ints get-max-tuple)
            battery-rows))))

(defn get-max-joltage
  [bank]
  (let [max-joltages (take 12 (reverse (sort bank)))
        candidates (frequencies max-joltages)]
    (loop [bank bank
           available-batteries candidates
           nums []]
      (let [candidate (first bank)
            matched? (get available-batteries candidate)]
        (if candidate
          (if (and matched? (not (zero? matched?)))
            (recur
             (rest bank)
             (update available-batteries candidate dec)
             (conj nums candidate))
            (recur
             (rest bank)
             available-batteries
             nums))
          nums)))))



(defn part-two
  [input]
  (let [battery-rows (->> (str/split-lines input)
                          (map #(str/split % #""))
                          (map #(map parse-long %)))
        banks (map get-max-joltage battery-rows)]
    
    (apply + (map (comp parse-long str/join) banks))))

(comment
  (part-one (read-input-file "three"))
  (part-two (read-input-file "three"))
  )

;; 818181911112111
;;   find max value of digits
;;   subs past that point
;;   find max of rest
