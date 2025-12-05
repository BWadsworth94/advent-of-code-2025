(ns aoc-2025.util)

(defn read-input-file
  [day]
  (let [file-name (str "day_" day ".txt")]
    (slurp (str "advent-of-code-2025-inputs/" file-name))))
