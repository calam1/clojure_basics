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

(defn reverseRecursively
  [s]
  (loop [s (seq s)
         newColl ()]
    (if (empty? s)
      (apply str newColl)
      (recur (rest s) (conj newColl (first s))))))

; this method is the same as the anonymous function mapped in the
; firstNonRepeatingCharacter function - kept for prosperity sake
(defn getCountValueFromFrequencies
  [v]
  (let [ctr (second v)]
    (if (= 1 ctr)
      (first v))))

(defn firstNonRepeatingCharacter
  [s]
  (let [letters (mapcat list (frequencies s))
        results (map (fn [v] (let [ctr (second v)] (if (= 1 ctr) (first v)))) letters)]
    (->> results
         (filter (complement nil?))
         (first)
         (str))))

; this only works for removing one  provided character, which is the
; 2nd arg - this is not idiomatic
(defn removeCharacterFromString
  [s d]
  (loop [s (seq s)
         d (seq d)
         newColl []]
    (if (empty? s)
      (apply str newColl)
      (recur (rest s) d (if (not= (first s) (first d)) (conj newColl (first s)) newColl)))))

(defn removeCharactersFromString
  [rem s]
  (apply str (remove #((set rem) %) (seq s))))

(defn isRotated?
  [s s2]
  (->>
   (.indexOf (str s s) s2)
   (not= -1)))

(defn isPalindrome_1?
  [s]
  (= s (apply str (reverse s))))


(defn palindrome? [s]
  (or (<= (count s) 1)
    (and (= (first s) (last s))
      (palindrome? (rest (butlast s))))))
