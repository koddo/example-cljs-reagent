(ns theproject.routes
    (:require-macros [secretary.core :refer [defroute]])
    (:import goog.History)
    (:require [secretary.core :as secretary]
              [goog.events :as events]
              [goog.history.EventType :as EventType]
              [re-frame.core :as re-frame]
              [pushy.core :as pushy]))


(defn app-routes []
  ;; (secretary/set-config! :prefix "#")


  (defroute "/" []
    (re-frame/dispatch [:set-active-panel :home-panel]))

  (defroute "/about" []
    (re-frame/dispatch [:set-active-panel :about-panel]))


  ;; (doto (History.)
  ;;   (events/listen
  ;;    EventType/NAVIGATE
  ;;    (fn [event]
  ;;      (secretary/dispatch! (.-token event))))
  ;;   (.setEnabled true))

  (def history (pushy/pushy secretary/dispatch!
                            (fn [x] (when (secretary/locate-route x) x))))
  (pushy/start! history)
  )
