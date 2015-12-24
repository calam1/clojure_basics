(ns patterns.visitor)

(defmulit export
  (fn [item format] [(:type item) format]))

;; Message
;{:type :message :content "Say what again!"}
;; Activity
;{:type :activity :content "Quoting Ezekiel"}
;; Formats
;:pdf, :xml


;; multimethods answer the question of languages with single dispatch
;; issues
;; this wont compile since i dont have an exporter package - more of
;; a non-working example
(defmethod export [:activity :pdf]
  [item format]
  (exporter/activity->pdf item))

(defmethod export [:activity :xml]
  [item format]
  (exporter/activity->xml))

(defmethod export [:message :pdf]
  [item format]
  (exporter/message->pdf item))

(defmethod export [:message :xml]
  [item format]
  (exporter/message->xml))
