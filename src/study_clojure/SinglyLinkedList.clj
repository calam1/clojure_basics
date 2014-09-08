(ns study-clojure.SinglyLinkedList)

(definterface INode
  (getValue [])
  (getNext [])
  (setValue [x])
  (setNext [n])
  (reverse [])
  (isCircular [])
  (findBeginningOfCircularList []))
         
(deftype Node [^:volatile-mutable value ^:volatile-mutable ^INode next]
  INode
  (getValue [_] value)
  (getNext [_] next)
  (setValue [_ x] (set! value x))
  (setNext [_ n] (set! next n))
  
  (reverse [this]
    (loop [current this new-head nil]
    ;; check if current is nil - if-not tests for logical false
      ;; opposite of if which tests for logical true
      (if-not current
        ;; falls in the first branch on final iteration because value
        ;; is nil - base case 
      (or new-head this)
      ;; if current is not nil we fall in here
      ;; recursively call passing the next node as first argument and
      ;; a newly created Node containing the current value and
      ;; next(which is nil the first pass) as the second argument
      (recur (.getNext current) (Node. (.getValue current) new-head)))))

  ;;this only checks if the list is circular in the sense that the
  ;;last node points to the first
  (isCircular [this]
    (loop [one this two this]
      (if-not (and two (.getNext two))
        false
          (if (= (.getValue one) (.. two getNext getValue))
            (println true (.getValue one) (.. two getNext getValue))
            (recur one (.getNext two))))))

  ;;this finds the beginning of the circular list where the last node
  ;;can refer to any node not just the head
  (findBeginningOfCircularList [this]
    (loop [one this two this]
      (println "debug" (.getValue one) (.getValue two))
      (if-not (and two (.getNext two))
        (recur (.getNext one) (.getNext one))
        (if (= (.getValue one) (.. two getNext getValue))
          (println (.getValue one) (.. two getNext getValue))
          (recur one (.getNext two))))))
  
  clojure.lang.Seqable
  (seq [this]
    (loop [current this acc ()]
      (if-not current
        acc
        (recur (.getNext current) (concat acc (list (.getValue current))))))))
