(ns theproject.db
  (:require [cljs.spec :as s])
  (:require-macros [theproject.core :refer [only-keys]])
  )


(s/def ::name string?)
(s/def ::active-panel #{:home-panel :about-panel})
(s/def ::pos (s/and number? #(>= % -1)))   ; it can be before the first beat
(s/def ::moves (s/map-of string? (fn [_] true)))
(s/def ::history (s/coll-of vector? :kind list?))
(s/def ::history-counter number?)

(s/def ::good-state (only-keys :req-un [::name ::active-panel ::pos ::moves ::history ::history-counter]))


(def default-db
  {:name "re-frame"
   :active-panel :home-panel
   :pos 0
   :moves {}
   :history ()
   :history-counter 0
   ;; :whatever true
   })


