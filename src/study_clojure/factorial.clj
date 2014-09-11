(ns study-clojure.factorial)

(defn factorial [num]
           (loop [num num i 1]
             (if (zero? num)
               i
               ;;*' basically allows for arbitrary precision, transforming the result of the calculation into bigint if needed.
               (recur (dec num) (*' num i)))))

(defn factorial_reduce [num]
  (reduce *' (range 1 (inc num))
          ))
