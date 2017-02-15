(ns theproject.subs
  (:require-macros [reagent.ratom :refer [reaction]])
  (:require [re-frame.core :as re-frame]
            ;; [clairvoyant.core :refer-macros [trace-forms]]
            ;; [re-frame-tracer.core :refer [tracer]]
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

;; )   ; end of trace-forms
