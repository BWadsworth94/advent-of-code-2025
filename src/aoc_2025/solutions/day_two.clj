(ns aoc-2025.solutions.day-two
  (:require
   [clojure.string :as str]
   [aoc-2025.util :refer [read-input-file]])
  (:import
   (java.lang Math)))

(defn parse-ranges
  [input]
  (map
   (comp
    #(map parse-long %)
    #(str/split % #"-"))
   (str/split input #"\,")))

(defn- copied-number?
  "Using math instead of string manipulation, wonder which is faster."
  [n]
  (let [digits (inc (long (Math/log10 n)))]
    ;; Odds are never split evenly so up-half != low-half of n
    (if (odd? digits)
      false
      (let [x (int (/ n (Math/pow 10 (/ digits 2))))
            y (int (mod n (Math/pow 10 (/ digits 2))))]
        (= x y)))))

(defn part-one
  [input]
  (let [ranges (parse-ranges input)]
    (reduce
     (fn [acc [lower-bound upper-bound :as _range]]
       (let [copied-number-cnt (count (filter copied-number? (range lower-bound upper-bound)))]
         (+ acc copied-number-cnt)))
     0
     ranges)))


(comment
  (part-one (read-input-file "two")))

