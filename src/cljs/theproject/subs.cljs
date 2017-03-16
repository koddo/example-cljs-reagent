(ns theproject.subs
  (:require-macros [reagent.ratom :refer [reaction]])
  (:require [re-frame.core :as re-frame]
            ;; [clairvoyant.core :refer-macros [trace-forms]]   [re-frame-tracer.core :refer [tracer]]
            ))

;; (trace-forms {:tracer (tracer :color "brown")}

(re-frame/reg-sub
 :name
 (fn [db]
   (:name db)))

(re-frame/reg-sub
 :active-panel
 (fn [db _]
   (:active-panel db)))

(re-frame/reg-sub
 :pos
 (fn [db _]
   (:pos db)))

(re-frame/reg-sub
 :move
 (fn [db [_ move]]
   (get-in db [:moves move])))

(re-frame/reg-sub
 :moves
 (fn [db _]
   (get db :moves)))

(re-frame/reg-sub
 :history
 (fn [db _]
   (get db :history)))

;; )   ; end of trace-forms
