(ns theproject.views
  (:require [re-frame.core :as re-frame]))


;; home

(let [theaudio (new js/Audio "https://s3.eu-central-1.amazonaws.com/test-75730/arsonist_-_01_-_Hot_salsa_trip.mp3")
      beep (new js/Audio "https://www.soundjay.com/button/beep-07.mp3")
      ]
  (do
    (aset theaudio "controls" true)
    (aset theaudio "ontimeupdate" #(do (println (aget theaudio "currentTime")) (.play beep)))
    )
  (defn home-panel []
    (let [name (re-frame/subscribe [:name])]
      (fn []
        [:div (str "Hello from " @name ". This is the Home Page.")
         [:div [:a {:href "/about"} "go to About Page"]]
         [:div {:ref #(if % (.appendChild % theaudio))}]
         ]))))

;; {:ref fn} -- fn is run two times: when the element is created and when it is destroyed
;; we append audio element to the div when it's created


;; about

(defn about-panel []
  (fn []
    [:div "This is the About Page."
     [:div [:a {:href "/"} "go to Home Page"]]]))


;; main

(defn- panels [panel-name]
  (case panel-name
    :home-panel [home-panel]
    :about-panel [about-panel]
    [:div]))

(defn show-panel [panel-name]
  [panels panel-name])

(defn main-panel []
  (let [active-panel (re-frame/subscribe [:active-panel])]
    (fn []
      [show-panel @active-panel])))
