(ns study-clojure.hashtable)

(definterface INode
  (getKey [])
  (setKey [key])
  (getValue [])
  (setValue [value])
  (getNext [])
  (setNext [next]))

(deftype Node
  [^:volatile-mutable key
   ^:volatile-mutable value
   ^:volatile-mutable next]
  INode
  (getKey [this] key)
  (setKey [this k] (set! key k))
  (getValue [this] value)
  (setValue [this v] (set! value v))
  (getNext [this] next)
  (setNext [this n] (set! next n)))

(definterface IHashTable
  (bucketIdx [k])
  (getBucket [k])
  (setBucket [k v])
  (insert [k v])
  (lookup [k]l))

(deftype HashTable
    [buckets size]
  IHashTable
  (bucketIdx [_ k] (mod (hash k) size))
  (getBucket [this k] (aget buckets (.bucketIdx this k)))
  (setBucket [this k v] (aset buckets (.bucketIdx this k) v))
  (insert [this k v]
    (.setBucket this k (Node. k v nil)))
  (lookup [this k]
    (when-let [node (.getBucket this k)]
      (.getValue node))))
