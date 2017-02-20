(ns theproject.events
  (:require [re-frame.core :as re-frame]
            [theproject.db :as db]
            [cljs.spec :as s]
            ;; [clairvoyant.core :refer-macros [trace-forms]]   [re-frame-tracer.core :refer [tracer]]
            ))

;; (trace-forms {:tracer (tracer :color "green")}

(def check-spec-interceptor
  (re-frame/after #(s/assert :theproject.db/good-state %)))

(re-frame/reg-event-db
 :initialize-db
 [check-spec-interceptor re-frame/debug]
 (fn  [_ _]
   db/default-db))

(re-frame/reg-event-db
 :set-active-panel
 [check-spec-interceptor re-frame/debug]
 (fn [db [_ active-panel]]
   (assoc db :active-panel active-panel)))


(re-frame/reg-event-db
 :current-time
 [check-spec-interceptor re-frame/debug]
 (fn asdf [db _]
   db))

;; )   ; end of trace-forms

