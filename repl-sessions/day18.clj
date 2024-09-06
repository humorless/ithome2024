(ns repl-sessions.day18
  "top n per group"
  (:require
   [dev.replware.ithome2024.db :as db]
   [dev.replware.ithome2024.db.init :as init]
   [datomic.api :as d]))

;; init database
(init/go)

(d/q '[:find ?country (max 3 ?city) (max 3 ?population)
       :in $
       :where
       [?ct :country/name ?country]
       [?ct :city/name ?city]
       [?ct :city/population ?population]]
     (db/db))
