(ns repl-sessions.day19
  "sub query"
  (:require
   [dev.replware.ithome2024.db :as db]
   [dev.replware.ithome2024.db.init :as init]
   [datomic.api :as d]))

;; init the database
(init/day-19-go)

(d/q '[:find ?e (count ?post)
       :where [?post :post/tag ?e]]
     (db/db))

;; =>
(comment
  [["adventure" 1]
   ["cooking" 1]
   ["fantasy" 1]
   ["fiction" 1]
   ["fitness" 2]
   ["funny" 1]
   ["healthy" 3]
   ["humor" 1]
   ["innovation" 1]
   ["joke" 3]
   ["long" 1]
   ["mastery" 2]
   ["men" 3]
   ["mystery" 1]
   ["random" 1]
   ["recipe" 1]
   ["sports" 1]
   ["story" 1]
   ["stupid" 1]
   ["tech" 1]
   ["women" 3]])

;; Find out all the tag which has appeared at least 3 times in different post.
(d/q '[:find ?tag ?n
       :where
       [(datomic.api/q
         (quote [:find ?e (count ?post)
                 :where [?post :post/tag ?e]]) $)
        [[?tag ?n]]]
       [(>= ?n 3)]] (db/db))

;; => 
(comment
  #{["women" 3] ["healthy" 3] ["joke" 3] ["men" 3]})

