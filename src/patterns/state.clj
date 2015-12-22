(ns patterns.state)

;data - fake db call
(def todays-news '("news1" "news2" "news3" "news4" "news5"))

;multimethod
(defmulti news-feed :user-state)

(defmethod news-feed :subscription
  [user]
  todays-news)

(defmethod news-feed :no-subscription
  [user]
  (take 3 todays-news))


(def user (atom {:name "Jackie Brown"
                 :balance 0
                 :user-state :no-subscription}))

(def ^:const SUBSCRIPTION_COST 30)

(defn pay
  [user amount]
  (swap! user update-in [:balance] + amount)
  (when (and (>= (:balance @user) SUBSCRIPTION_COST)
             (= :no-subscription (:user-state @user)))
    (swap! user assoc :user-state :subscription)
    (swap! user update-in [:balance] - SUBSCRIPTION_COST)))
