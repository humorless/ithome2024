(ns dev.replware.ithome2024.db.schema
  "Datomic schema, using shorthand vectors")

;; All available data type
;; bigdec | bigint | boolean | bytes | double | float | instant |
;; keyword | long | ref | string | symbol | tuple | uuid | uri

(def thin-schema-example
  [[:user/uuid :uuid "Unique user identifier"]
   [:user/name :string "User name"]
   ;; End user entity

   [:profile-link/user :ref "User this link belongs too"]
   [:profile-link/type :string "`mastodon`, `linkedin`, `personal-site`, etc."]
   [:profile-link/href :string "http/mailto URL"]
   ;; End profile entity
   ])

(defn inflate-schema [s]
  (for [[ident type doc & flags] s]
    (cond-> {:db/ident     ident
             :db/valueType (keyword "db.type" (name type))
             :db/doc       doc
             :db/cardinality (if (some #{:many} flags)
                               :db.cardinality/many
                               :db.cardinality/one)}
      (some #{:identity} flags)
      (assoc :db/unique :db.unique/identity)
      (some #{:value} flags)
      (assoc :db/unique :db.unique/value)
      (some #{:component} flags)
      (assoc :db/isComponent true))))

(defn schema-tx [thin-schema]
  (inflate-schema thin-schema))
