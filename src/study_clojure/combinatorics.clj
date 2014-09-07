(ns study-clojure.combinatorics
  (:require
   [clojure.math.combinatorics :as combo]))

(defn combinations_n_times
  [arg n] 
  (combo/combinations arg n))

(defn permutations
  [arg]
  (combo/permutations arg))
