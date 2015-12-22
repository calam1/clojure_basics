(ns patterns.strategy)

(defn increase_by_one
  [number]
  (inc number))

(defn double_number
  [number]
  (* 2 number))

(def increase_and_double
  (juxt inc #(double_number %)))

(def inc-and-double
  (juxt inc #(* % 2)))
