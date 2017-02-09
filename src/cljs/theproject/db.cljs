(ns theproject.db
  (:require [cljs.spec :as s]))


(s/def ::name string?)
(s/def ::active-panel #{:home-panel :about-panel})

(s/def ::good-state (s/keys :req-un [::name ::active-panel]))

(def default-db
  {:name "re-frame"
   :active-panel :home-panel
   })


