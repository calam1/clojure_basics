(ns study-clojure.hashtable)

;;http://macromancy.com/2014/02/03/data-structures-clojure-hash-tables.html

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
  (calcSize [])
  (maybeResize [])
  (bucketIdx [k])
  (getBucket [k])
  (setBucket [k v])
  (findNode [k])
  (insert [k v resize?])
  (insert [k v])
  (lookup [k]l))

(deftype HashTable
    [^:volatile-mutable ^objects buckets
     ^:volatile-mutable ^long occupancy
     ^:volatile-mutable size
     ^float load-factor]
  IHashTable

  ;;helper method to determine if we need to resize
  (calcSize [_]
    (when (-> occupancy zero? not)
      (cond
       ;;shrink
       (< occupancy (/ (* size load-factor) 2)) (/ size 2)
       ;;grow
       (> (/ occupancy size) load-factor) (* size 2))))

  (maybeResize [this]
    (when-let [new-size (.calcSize this)]
      (let [old-buckets buckets]
        ;;set size and buckets prior to re-inserting from old buckets
        (set! size new-size)
        (set! buckets (make-array INode new-size))

        ;;loop over old buckets. For any pubckets containing lined
        ;;list nodes, walk these nodes.  Extract their keys and
        ;;values, inserting them into the new buckets array without
        ;;triggering a call to maybeResize
        (dotimes [i (alength old-buckets)]
          (if-let [bucket (aget old-buckets i)]
          (loop [^INode node bucket]
            (when node
              (do (.insert this (.getKey node)
                           (.getValue node)
                           false)
                  (recur (.getNext node))))))))))
  
  ;;generate index by taking the modulus of the hash of the key and size
  (bucketIdx [_ k] (mod (hash k) size))

  ;;generate the hash to the proper array slot and return value
  (getBucket [this k] (aget buckets (.bucketIdx this k)))

  ;;generate hash to place the value in the prpoer array slot
  (setBucket [this k v] (aset buckets (.bucketIdx this k) v))

  ;;helper method - takes a key and tries to locate the key in the
  ;;bucket array, if it finds it it returns the node containing the
  ;;key and the node directly before it
  (findNode [this k]
    (when-let [bucket (.getBucket this k)]
      (loop [^INode node bucket prev nil]
        ;;either node is null - base case or you find a node with the
        ;;same key
        (if (or (nil? node) (= (.getKey node) k))
          (vector prev node)
          (recur (.getNext node) node)))))

  ;;insert - wrapper around setBucket plus handles collisions
  (insert [this k v resize?]
    (when resize? (.maybeResize this))
    
    (let [[^INode prev ^INode node] (.findNode this k)]
      (cond
       ;;bucket contains a linked list but not the key; set the next
       ;;node - if prev is true and the present node is nil then
       ;;setNext with the current key/value - this handles the collision
       (and prev (nil? node)) (.setNext prev (Node. k v nil))
       ;;bucket contains a linked list and the key; reset value - this
       ;;is same key so replace value
       node (.setValue node v)
       ;;bucket is empty; create a new node and set the bucket - this
       ;;part is basically a wrapper of setBucket
       :else (.setBucket this k (Node. k v nil)))))

  (insert [this k v] (.insert this k v true))
  
  ;;find - wrapper around getBucket
  (lookup [this k]
    (when-let [^INode node (.getBucket this k)]
      (.getValue node))))
