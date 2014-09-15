(ns study-clojure.fibonacci)

;; returns the whole sequence of fibs will overflow
(defn fibonacci_sequence [num]
  (take num
        (map first (iterate (fn [[a b]] [b (+ a b)]) [0 1]))))

;; returns the nth of a sequence will also overflow
(defn fibonacci_nth [num]
  (nth (fibonacci_sequence num) (dec num)))

;; naive recursive method  will return the nth (provided as arg) will overflow
(defn fibonacci_naive [num]
  (if (= num 0) 0
     (if (= num 1) 1
         (+ (fibonacci_naive (- num 2)) (fibonacci_naive (- num 1))))))

;; lazy seq will infinitely generate fibonacci sequence - will not
;; overflow cause of the quote after the addition sign - converts it
;; to BigInteger  if needed
(def fibonacci-seq
  ((fn recursiveFibonacci [a b]
     (lazy-seq (cons a (recursiveFibonacci b (+' a b)))))
   0 1))

;; uses the above seq and cons the results - will not overflow
(defn lazy-fib [number]
   (take number fibonacci-seq))
