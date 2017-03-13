(ns theproject.db
  (:require [cljs.spec :as s])
  (:require-macros [theproject.core :refer [only-keys]])
  )


(s/def ::name string?)
(s/def ::active-panel #{:home-panel :about-panel})
(s/def ::pos (s/and number? #(>= % -1)))   ; it can be before the first beat
(s/def ::moves (s/coll-of number? :kind vector? :distinct true))

(s/def ::good-state (only-keys :req-un [::name ::active-panel ::pos ::moves]))


(def default-db
  {:name "re-frame"
   :active-panel :home-panel
   :pos 0
   :moves []
   ;; :whatever true
   })


