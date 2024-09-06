(ns repl-sessions.day19
  "Demo custom aggregation function"
  (:require
   [dev.replware.ithome2024.db :as db]
   [dev.replware.ithome2024.db.init :as init]
   [datomic.api :as d]))

(init/go)

(defn rank
  [coll]
  (let [s (count coll)]
    (range s)))

(d/q '[:find ?country (into [] ?city) (repl-sessions.day19/rank ?city)
       :in $
       :where
       [?ct :country/name ?country]
       [?ct :city/name ?city]
       [?ct :city/population ?population]]
     (db/db))

