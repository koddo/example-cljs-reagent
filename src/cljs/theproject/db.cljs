(ns theproject.db
  (:require [cljs.spec :as s])
  (:require-macros [theproject.core :refer [only-keys]])
  )


(s/def ::name string?)
(s/def ::active-panel #{:home-panel :about-panel})

(s/def ::good-state (only-keys :req-un [::name ::active-panel]))


(def default-db
  {:name "re-frame"
   :active-panel :home-panel
   ;; :whatever true
   })


