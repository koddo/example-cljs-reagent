(ns theproject.views
  (:require [re-frame.core :as re-frame]
            [clairvoyant.core :refer-macros [trace-forms]]
            [re-frame-tracer.core :refer [tracer]]))

(trace-forms {:tracer (tracer :color "gold")}

             ;; home

             (let [theaudio (new js/Audio "https://s3.eu-central-1.amazonaws.com/test-75730/arsonist_-_01_-_Hot_salsa_trip.mp3")
                   beep (new js/Audio "https://www.soundjay.com/button/beep-07.mp3")
                   name (re-frame/subscribe [:name])
                   ]
               (do
                 (aset theaudio "controls" true)
                 (aset theaudio "ontimeupdate" #(do (println (aget theaudio "currentTime")) (.play beep)))
                 ;; (aset theaudio "ontimeupdate" (fn dispatch-current-time [] (re-frame/dispatch [:current-time (aget theaudio "currentTime")])))
                 )
               (defn home-panel []
                 [:div (str "Hello from " @name ". This is the Home Page.")
                  [:div [:a {:href "/about"} "go to About Page"]]
                  [:div {:ref #(if % (.appendChild % theaudio))}]
                  ]))

             ;; {:ref fn} -- fn is run two times: when the element is created and when it is destroyed
             ;; we append audio element to the div when it's created

             ;; t = setTimeout( function () { console.log("hello") }, 10000);
             ;; clearTimeout(t);

             ;; node.cloneNode(deep)


             ;; about

             (defn about-panel []
               [:div "This is the About Page."
                [:div [:a {:href "/"} "go to Home Page"]]])


             ;; main

             (defn- panels [panel-name]
               (case panel-name
                 :home-panel [home-panel]
                 :about-panel [about-panel]
                 [:div]))

             (defn show-panel [panel-name]
               [panels panel-name])

             
             (let [active-panel (re-frame/subscribe [:active-panel])]
               (defn main-panel []
                 [show-panel @active-panel]))

             )
