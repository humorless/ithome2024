(ns dev.replware.ithome2024.db
  "Basic db initialization"
  (:require
   [dev.replware.ithome2024.db.schema :as schema]
   [datomic.api :as d]))

(def url "datomic:mem://ithome")
(d/create-database url)

(def conn (d/connect url))

(defn db []
  (d/db conn))

(defn init []
  @(d/transact conn (schema/schema-tx)))
