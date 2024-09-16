(ns repl-sessions.day19
  "sub query"
  (:require
   [dev.replware.ithome2024.db :as db]
   [dev.replware.ithome2024.db.init :as init]
   [datomic.api :as d]))

;; init the database
(let [{:keys [tempids]} @(init/day-29-go)]
  (def entity-id (get tempids "temp-1"))
  (def attr-id (d/entid (db/db) :person/roles)))

;;; Show how to use `d/datoms` to get 
;;  the content from datoms
(map (juxt :e :a :v)
     (d/datoms (db/db) :eavt
               entity-id))

;;; Compare the speed of `d/datoms` and ordinary query
(time (map :v
           (d/datoms (db/db) :eavt
                     entity-id
                     attr-id)))

(time (d/q '[:find ?r
             :in $ ?e
             :where [?e :person/roles ?r]]
           (db/db) entity-id))

