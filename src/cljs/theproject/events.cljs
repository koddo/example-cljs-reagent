(ns theproject.events
  (:require [re-frame.core :as re-frame]
            [theproject.db :as db]
            [cljs.spec :as s]
            ;; [clairvoyant.core :refer-macros [trace-forms]]   [re-frame-tracer.core :refer [tracer]]
            ))

;; (trace-forms {:tracer (tracer :color "green")}

(def check-spec-interceptor
  (re-frame/after #(s/assert :theproject.db/good-state %)))

;; (re-frame/reg-event-db
;;  :initialize-db
;;  [check-spec-interceptor re-frame/debug]
;;  (fn  [_ _]
;;    db/default-db))

(re-frame/reg-cofx
 :local-store 
 (fn [coeffects local-store-key]
   (assoc coeffects 
          :local-store
          (.getItem js/localStorage local-store-key))))

(re-frame/reg-event-fx
 :initialize-db
 [check-spec-interceptor re-frame/debug (re-frame/inject-cofx :local-store "rueda-moves-array")]
 (fn [cofx _]
   (let [moves (:local-store cofx)]
     (if (nil? moves)
       {:db db/default-db}
       {:db (assoc db/default-db
                   :moves (js->clj (js/JSON.parse moves)))}))))

(.addEventListener js/window "storage"
                   #(re-frame/dispatch [:storage-update %]))

(re-frame/reg-event-db
 :storage-update
 [check-spec-interceptor re-frame/debug]
 (fn [db [_ storage-event]]
   (println (.-key storage-event) (.-newValue storage-event))
   (assoc db :moves (js->clj (js/JSON.parse (.getItem js/localStorage "rueda-moves-array"))))))

(re-frame/reg-event-db
 :set-active-panel
 [check-spec-interceptor re-frame/debug]
 (fn [db [_ active-panel]]
   (assoc db :active-panel active-panel)))

(re-frame/reg-event-db
 :set-pos
 [check-spec-interceptor
  ;; re-frame/debug
  ]
 (fn [db [_ pos]]
   (assoc db :pos pos)))

;; )   ; end of trace-forms

