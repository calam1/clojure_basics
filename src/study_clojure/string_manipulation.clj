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
