(ns dev.replware.ithome2024.db.init
  "Prepare some testing data to transact into db for testing"
  (:require
   [dev.replware.ithome2024.db.schema :as s]
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

(defn day-18-go []
  (d/transact db/conn schema)
  (d/transact db/conn data))

(def day-19-schema
  [[:post/tag :string "Post's hash tag" :many]
   ;; End post's entity
   ])

(def day-19-data
  [{:db/id "temp-1"
    :post/tag "joke"}
   {:db/id "temp-1"
    :post/tag "stupid"}
   {:db/id "temp-1"
    :post/tag "men"}
   {:db/id "temp-1"
    :post/tag "humor"}

   {:db/id "temp-2"
    :post/tag "funny"}
   {:db/id "temp-2"
    :post/tag "random"}
   {:db/id "temp-2"
    :post/tag "joke"}

   {:db/id "temp-3"
    :post/tag "story"}
   {:db/id "temp-3"
    :post/tag "long"}
   {:db/id "temp-3"
    :post/tag "women"}

   {:db/id "temp-4"
    :post/tag "adventure"}
   {:db/id "temp-4"
    :post/tag "fiction"}
   {:db/id "temp-4"
    :post/tag "mystery"}
   {:db/id "temp-4"
    :post/tag "fantasy"}
   {:db/id "temp-4"
    :post/tag "men"}

   {:db/id "temp-5"
    :post/tag "mastery"}
   {:db/id "temp-5"
    :post/tag "tech"}
   {:db/id "temp-5"
    :post/tag "innovation"}
   {:db/id "temp-5"
    :post/tag "women"}

   {:db/id "temp-6"
    :post/tag "cooking"}
   {:db/id "temp-6"
    :post/tag "recipe"}

   {:db/id "temp-7"
    :post/tag "sports"}
   {:db/id "temp-7"
    :post/tag "fitness"}
   {:db/id "temp-7"
    :post/tag "healthy"}

   {:db/id "temp-8"
    :post/tag "mastery"}
   {:db/id "temp-8"
    :post/tag "men"}

   {:db/id "temp-9"
    :post/tag "healthy"}
   {:db/id "temp-9"
    :post/tag "joke"}
   {:db/id "temp-9"
    :post/tag "fitness"}

   {:db/id "temp-10"
    :post/tag "women"}
   {:db/id "temp-10"
    :post/tag "healthy"}])

(defn day-19-go []
  (let [schema (s/schema-tx day-19-schema)]
    (d/transact db/conn schema)
    (d/transact db/conn day-19-data)))
