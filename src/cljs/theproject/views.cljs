(ns theproject.views
  (:require [re-frame.core :as re-frame]
            ;; [clairvoyant.core :refer-macros [trace-forms]]
            ;; [re-frame-tracer.core :refer [tracer]]
            ))

;; (trace-forms {:tracer (tracer :color "gold")}

;; home
;; (def beats (new js/Array))
(def
  beats
  (map-indexed
   vector
   [0.020
   0.19799
   0.509544
   0.765805
   1.045411
   1.309486
   1.581568
   1.845344
   2.109584
   2.349433
   2.597711
   2.806413
   3.061679
   3.317747
   3.565667
   3.813762
   4.069893
   4.349824
   4.618736
   4.877359
   5.133356
   5.38167
   5.637915
   5.861911
   6.117658
   6.389707
   6.645558
   6.885797
   7.157967
   7.389613
   7.64578
   7.885688
   8.174113
   8.421668
   8.685836
   8.941784
   9.189857
   9.437644
   9.685677
   9.893939
   10.157372
   10.413576
   10.734223
   10.957328
   11.213827
   11.457341
   11.709498
   11.93358
   12.221872
   12.453287
   12.733446
   12.959072
   13.213838
   13.454773
   13.74155
   13.94942
   14.246647
   14.485768
   14.757748
   15.021448
   15.29348
   15.549768
   15.805875
   16.037438
   16.293468
   16.517512
   16.813709
   17.053663
   17.326103
   17.557633
   17.829412
   18.069441
   18.325856
   18.557478
   18.821445
   19.053444
   19.33337
   19.597754
   19.853728
   20.078462
   20.358024
   20.573659
   20.845364
   21.07738
   21.34191
   21.597619
   21.885688
   22.133423
   22.405475
   22.65344
   22.910393
   23.150013
   23.413644
   23.677678
   23.941456
   24.173371
   24.437614
   24.661476
   24.94993
   25.205606
   25.469373
   25.693512
   25.957349
   26.213911
   26.485771
   26.717659
   26.981481
   27.215096
   27.493772
   27.741419
   27.997468
   28.229389
   28.485277
   28.718905
   28.981893
   29.221391
   29.477728
   29.742306
   29.997633
   30.222409]))
(def theaudio (new js/Audio "https://s3.eu-central-1.amazonaws.com/test-75730/arsonist_-_01_-_Hot_salsa_trip.mp3"))
(def thebeep (new js/Audio "https://www.soundjay.com/button/beep-07.mp3"))
(def timer nil)

(defn play
  ([beats]          (play beats (count beats)))
  ([beats total-length]   (play beats total-length nil))
  ([beats total-length scheduled-time]
   (let [current-time (aget theaudio "currentTime")
         next-beats (drop-while #(<= (second %) current-time) beats)
         [next-pos next-time] (first next-beats)
         pos (or next-pos total-length)
         ]
     (if (and scheduled-time (>= current-time scheduled-time))
       (println (str "----- " pos " " current-time)))
     (if next-time
       (set! timer (js/setTimeout #(play next-beats total-length next-time)
                                  (* 1000 (- next-time current-time))))))))

(let [name (re-frame/subscribe [:name])]
  (do
    (aset theaudio "controls" true)
    (aset theaudio "onplay" #(play beats))
    (aset theaudio "onpause" #(js/clearTimeout timer))
    )
  (defn home-panel []
    [:div
     (str "Hello from " @name ". This is the Home Page.")
     [:div [:a {:href "/about"} "go to About Page"]]
     [:div {:ref #(if % (.appendChild % theaudio))
            :on-key-down (fn [e] (.push beats (aget theaudio "currentTime")))}]
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

;; )   ; end of trace-forms
