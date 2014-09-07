(ns study-clojure.SinglyLinkedList)

(definterface INode
  (getValue [])
  (getNext [])
  (setValue [x])
  (setNext [n])
  (reverse []))
         
(deftype Node [^:volatile-mutable value ^:volatile-mutable ^INode next]
  INode
  (getValue [_] value)
  (getNext [_] next)
  (setValue [_ x] (set! value x))
  (setNext [_ n] (set! next n))
  
  (reverse [this]
    (loop [current this new-head nil]
    ;; check if current is nil
      (if-not current
        ;; falls in the first branch on final iteration - end of
        ;; recursive call - returns the non nil value of both - order matters
      (or new-head this)
      ;; if current is not nil we fall in here
      ;; recursively call passing the next node as first argument and
      ;; a newly created Node containing the current value and
      ;; next(which is nil the first pass) as the second argument
      (recur (.getNext current) (Node. (.getValue current) new-head)))))

  clojure.lang.Seqable
  (seq [this]
    (loop [current this acc ()]
      (if-not current
        acc
        (recur (.getNext current) (concat acc (list (.getValue current))))))))

