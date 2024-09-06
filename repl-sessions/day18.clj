(ns repl-sessions.day18
  "top n per group"
  (:require
   [dev.replware.ithome2024.db :as db]
   [dev.replware.ithome2024.db.init :as init]
   [datomic.api :as d]))

;; init database
(init/day-18-go)

(d/q '[:find ?country (max 2 ?population)
       :in $
       :where
       [?ct :country/name ?country]
       [?ct :city/name ?city]
       [?ct :city/population ?population]]
     (db/db))

(comment
 ;; => 
  [["France" [2181000 808000]]
   ["United Kingdom" [7825300 1016800]]
   ["United States" [8175133 3792621]]])

(defn max-by-population
  [coll]
  (let [result (sort-by second > coll)]
    (take 2 result)))

(d/q '[:find ?country (repl-sessions.day18/max-by-population ?tup)
       :in $
       :where
       [?ct :country/name ?country]
       [?ct :city/name ?city]
       [?ct :city/population ?population]
       [(tuple ?city ?population) ?tup]]
     (db/db))

(comment
  ;; => 
  [["France" (["Paris" 2181000] ["Marseille" 808000])]
   ["United Kingdom" (["London" 7825300] ["Birmingham" 1016800])]
   ["United States" (["New York" 8175133] ["Los Angeles" 3792621])]])
