(ns study-clojure.string_manipulation
  (:require
   [clojure.string :as s]))

(defn reverseStringUsingStringReverseMethod
  [arg]
  (s/reverse arg))

(defn reverseStringBuiltInReverseMethod
  [arg]
  (apply str (reverse arg)))

(defn reverseStringUsingJavaMethod
  [arg]
  (.toString (.reverse (StringBuilder. arg)))) 

(defn reverseStringNoReverseMethods
  [arg]
  (apply str
    (for [i (range (dec (count arg)) -1 -1)]
      (get arg i))))

(defn sameAsReverseStringNoReverseMethodsNoForMacro
  [arg]
  (apply str
    (map (partial get arg) (range (dec (count arg)) -1 -1))))

(defn getCountValueFromFrequencies [v]
  (let [ctr (second v)]
    (if (= 1 ctr)
      (first v))))

(defn firstNonRepeatingCharacter [s]
  (let [letters (mapcat list (frequencies s))
        results (map getCountValueFromFrequencies letters)]
    (->> results
         (filter (complement nil?))
         (first)
         (str))))
