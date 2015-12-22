(ns patterns.command)

(defn login
  [user pw]
  (println "Logged in" user))

(defn logout
  [user]
  (println "Logged out" user))

(defn execute
  [command]
  (command))


;usage
;(execute #(login "chris" "pw"))
