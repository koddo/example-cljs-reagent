(ns theproject.views
  (:require [re-frame.core :as re-frame]
            ;; [clairvoyant.core :refer-macros [trace-forms]]   [re-frame-tracer.core :refer [tracer]]
            [ajax.core :as ajax]
            [clojure.string :as string]
            [reagent.core :as reagent]
            ))

;; (trace-forms {:tracer (tracer :color "gold")}

;; home

(def <sub (comp deref re-frame.core/subscribe))
(def >evt re-frame.core/dispatch)

(defonce beats (atom []))

(defonce theaudio (new js/Audio "https://s3.eu-central-1.amazonaws.com/test-75730/arsonist_-_01_-_Hot_salsa_trip--re-encoded-to-constand-bitrate.mp3"))
(ajax/GET "https://s3.eu-central-1.amazonaws.com/test-75730/arsonist_-_01_-_Hot_salsa_trip.beats"
          {
           :handler #(reset! beats
                             (map-indexed (fn index-beat-pair [i s] [i (js/parseFloat s)])
                                          (string/split-lines %)))
           })
;; TODO: :error-handler

;; (defonce thebeep (new js/Audio "https://s3.eu-central-1.amazonaws.com/test-75730/beep.mp3"))
(defonce thebeep (new js/WebAudioAPISound "https://s3.eu-central-1.amazonaws.com/test-75730/beep.mp3"))
(defonce counts [(new js/WebAudioAPISound "https://s3.eu-central-1.amazonaws.com/test-75730/1-uno.mp3")
                 (new js/WebAudioAPISound "https://s3.eu-central-1.amazonaws.com/test-75730/2-dos.mp3")
                 (new js/WebAudioAPISound "https://s3.eu-central-1.amazonaws.com/test-75730/3-tres.mp3")
                 (new js/WebAudioAPISound "https://s3.eu-central-1.amazonaws.com/test-75730/4-cuatro.mp3")
                 (new js/WebAudioAPISound "https://s3.eu-central-1.amazonaws.com/test-75730/5-cinco.mp3")
                 (new js/WebAudioAPISound "https://s3.eu-central-1.amazonaws.com/test-75730/6-seis.mp3")
                 (new js/WebAudioAPISound "https://s3.eu-central-1.amazonaws.com/test-75730/7-siete.mp3")
                 (new js/WebAudioAPISound "https://s3.eu-central-1.amazonaws.com/test-75730/8-ocho.mp3")
                 ])
(def timer nil)
(def shift 0.14)

(def names-of-moves ["balsero"
                     "coca-cola"
                     "copelia"
                     "cubanita"
                     "cubanito"
                     "dame"
                     "dame dos"
                     "dile que no"
                     "echeverria"
                     "echufla y dame"
                     "el dedo"
                     "enchufla"
                     "enchufla arriba"
                     "enchufla con cuba"
                     "enchufla con vuelta"
                     "enchufla doble"
                     "kentucky"
                     "la familia"
                     "la preciosa"
                     "mambo"
                     "mantania"
                     "por atras"
                     "prima"
                     "prima con la hermana"
                     "sacala"
                     "setenta"
                     "sombrero"
                     "vasilala"
                     ])

;; TODO: rename all mp3 files to get rid of underscores
(let [construct-moves-audio-objects (fn [acc move] (assoc acc move (new js/WebAudioAPISound (str "https://s3.eu-central-1.amazonaws.com/test-75730/moves/" (js/encodeURIComponent move) ".mp3"))))]
  (def moves (reduce construct-moves-audio-objects {} names-of-moves)))

;; js/encodeURIComponent

;; (defn get-pos []
;;   (let [current-time (+ shift (aget theaudio "currentTime"))
;;         next-beats (drop-while #(<= (second %) current-time) beats)
;;         [next-pos next-time] (first next-beats)
;;         pos (- (or next-pos total-length) 1)
;;         ]
;;     pos
;;     ))


;; qqq [6:06 PM] 
;; @negaduck : based on the bindings, you can turn the "args" into a map; then in both functions, you can use {:keys [ .. ] }

;; jstew [6:07 PM] 
;; I was just going to write the same thing. Extract the common bindings into a function that returns a map.

(defn play
  ([beats func]          (play beats func (count beats) nil))
  ([beats func total-length scheduled-time]
   (let [current-time (+ shift (aget theaudio "currentTime"))
         next-beats (drop-while #(<= (second %) current-time) beats)
         [next-pos next-time] (first next-beats)
         pos (- (or next-pos total-length) 1)
         ]
     ;; (println (string/join " " ["c" current-time "s" scheduled-time "n" next-time "d" (- next-time current-time)]))
     (if (and scheduled-time (>= current-time scheduled-time))
       (func pos))
     (if next-time
       (set! timer (js/setTimeout #(play next-beats func total-length next-time)
                                  (* 1000 (- next-time current-time))))))))

(defn seek       ; not sure yet if we need this function, so I don't abstract away to reuse let bindings from the play func, but just copy them
  ([beats func]          (seek beats func (count beats) nil))
  ([beats func total-length scheduled-time]
   (let [current-time (+ shift (aget theaudio "currentTime"))
         next-beats (drop-while #(<= (second %) current-time) beats)
         [next-pos next-time] (first next-beats)
         pos (- (or next-pos total-length) 1)
         ]
     (func pos))))

(defn rhythm-func [pos]
  (re-frame/dispatch [:set-pos pos])
  (let [pl (fn [sound] (aset sound "currentTime" 0) (.play sound))]
    (do
      ;; (println (str "----- " pos))
      (case (mod pos 8)
        ;; (0 4) (.play thebeep)
        0 (.play thebeep)
        ;; 0 (pl (counts 0))
        ;; 1 (pl (counts 1))
        ;; 2 (pl (counts 2))

        4 (pl (moves (rand-nth names-of-moves)))
        ;; 4 (pl (counts 4))
        ;; 5 (pl (counts 5))
        ;; 6 (pl (counts 6))
        nil
        ))))

(do
  (aset theaudio "controls" true)
  (aset theaudio "volume" 0.5)
  (aset theaudio "onplay" #(play @beats rhythm-func))
  (aset theaudio "onpause" #(js/clearTimeout timer))
  (aset theaudio "onseeked" (fn [] (seek @beats #(re-frame/dispatch [:set-pos %]))))
  )

(let [name (re-frame/subscribe [:name])
      pos  (re-frame/subscribe [:pos])
      ]
  (defn home-panel []
    [:div
     (str "Hello from " @name ". This is the Home Page.")
     [:div [:a {:href "/about"} "go to About Page"]]
     [:div {:ref (fn [el]
                   (if (and el
                            (not (.hasChildNodes el)))
                     (.appendChild el theaudio)))}]
     [:input {:type "button" :value "Click me!" :on-click #(.play thebeep)}]
     [:div @pos]
     (into [:div]
           (for [x names-of-moves]
             ^{:key x}
             ;; [:p x]
             [:span
              [:input {:type "checkbox"
                       :checked (<sub [:move x])
                       :on-change #(>evt [:set-move [x (-> % .-target .-checked)]])
                       }]
              [:label x]
              ]
             ))
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


