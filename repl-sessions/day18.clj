(ns repl-sessions.day18
  "Prepare some testing data to transact into db for testing"
  (:require
   [dev.replware.ithome2024.db :as db]
   [datomic.api :as d]))

;; Aggregates

(def schema
  [{:db/ident       :country/name
    :db/valueType   :db.type/string
    :db/cardinality :db.cardinality/one}

   {:db/ident       :city/name
    :db/valueType   :db.type/string
    :db/cardinality :db.cardinality/one}

   {:db/ident       :city/population
    :db/valueType   :db.type/long
    :db/cardinality :db.cardinality/one}])

(d/transact db/conn schema)

(def data
  [{:country/name "United States"
    :city/name "New York"
    :city/population 8175133}
   {:country/name "United States"
    :city/name "Los Angeles"
    :city/population 3792621}
   {:country/name "United States"
    :city/name "Chicago"
    :city/population 2695598}
   {:country/name "France"
    :city/name "Paris"
    :city/population 2181000}
   {:country/name "France"
    :city/name "Marseille"
    :city/population 808000}
   {:country/name "France"
    :city/name "Lyon"
    :city/population 422000}
   {:country/name "United Kingdom"
    :city/name "London"
    :city/population 7825300}
   {:country/name "United Kingdom"
    :city/name "Birmingham"
    :city/population 1016800}
   {:country/name "United Kingdom"
    :city/name "Leeds"
    :city/population 770800}])

(d/transact db/conn data)

(d/q '[:find ?country (max 3 ?city) (max 3 ?population)
       :in $
       :where
       [?ct :country/name ?country]
       [?ct :city/name ?city]
       [?ct :city/population ?population]]
     (db/db))

