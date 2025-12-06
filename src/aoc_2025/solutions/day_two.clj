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
  (let [num-of-digits (inc (long (Math/log10 n)))]
    ;; Odds are never split evenly so up-half != low-half of n
    (if (odd? num-of-digits)
      false
      (let [x (int (/ n (Math/pow 10 (/ num-of-digits 2))))
            y (int (mod n (Math/pow 10 (/ num-of-digits 2))))]
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

(defn partition-int
  [n partition-size]
  (let [xs (partition partition-size partition-size (str n))]
    (map (comp parse-long (partial apply str)) xs)))

(defn- repeated-pattern?
  [n]
  (let [num-of-digits (inc (long (Math/log10 n)))]
    (if (= 1 num-of-digits)
      false
      (reduce
       (fn [_ partition-size]
         (let [even-split? (zero? (mod num-of-digits partition-size))
               eq? (apply = (partition-int n partition-size))] 
           (if (and eq? even-split?)
             (reduced true)
             false)))
       false
       (range 1 (inc (/ num-of-digits 2)))))))


(defn part-two
  [input]
  (let [ranges (parse-ranges input)]
    (reduce
     (fn [acc [lower-bound upper-bound :as _range]]
       (let [xs (filter
                 (some-fn repeated-pattern? copied-number?)
                 (range lower-bound (inc upper-bound)))] 
         (+ acc (if (seq xs) (apply + xs) 0))))
     0
     ranges)))

;; 123123123

(comment
  (part-one (read-input-file "two"))
  (part-two (read-input-file "two"))
  

 (apply + (flatten (part-two "11-22,95-115,998-1012,1188511880-1188511890,222220-222224,1698522-1698528,446443-446449,38593856-38593862,565653-565659,824824821-824824827,2121212118-2121212124"))))
  

(part-two "1-10")

(filter
 repeated-pattern?
 (range 824824821 824824827))