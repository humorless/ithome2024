(ns repl-sessions.day18
  "top n per group"
  (:require
   [dev.replware.ithome2024.db :as db]
   [dev.replware.ithome2024.db.init :as init]
   [datomic.api :as d]))

;; init database
(init/day-18-go)

(d/q '[:find ?country (max 3 ?city) (max 3 ?population)
       :in $
       :where
       [?ct :country/name ?country]
       [?ct :city/name ?city]
       [?ct :city/population ?population]]
     (db/db))

(comment
 ;; => 
  [["France" ["Paris" "Marseille" "Lyon"] [2181000 808000 422000]]
   ["United Kingdom"
    ["London" "Leeds" "Birmingham"]
    [7825300 1016800 770800]]
   ["United States"
    ["New York" "Los Angeles" "Chicago"]
    [8175133 3792621 2695598]]])

(defn rank
  [coll]
  (let [s (count coll)]
    (range 1 (inc s))))

(d/q '[:find ?country (max 3 ?city) (max 3 ?population)
       (repl-sessions.day18/rank ?city)
       :in $
       :where
       [?ct :country/name ?country]
       [?ct :city/name ?city]
       [?ct :city/population ?population]]
     (db/db))

(comment
  ;; => 
  [["France"
    ["Paris" "Marseille" "Lyon"]
    [2181000 808000 422000]
    (1 2 3)]
   ["United Kingdom"
    ["London" "Leeds" "Birmingham"]
    [7825300 1016800 770800]
    (1 2 3)]
   ["United States"
    ["New York" "Los Angeles" "Chicago"]
    [8175133 3792621 2695598]
    (1 2 3)]])
