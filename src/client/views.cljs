(ns client.views
  (:require
    [reagent.core :as reagent]
    [re-frame.core :as re-frame]
    [re-com.core :as re-com :refer [at]]
    [client.styles :as styles]
    [client.events :as events]
    [client.routes :as routes]
    [client.subs :as subs]))

;; home

(def rounded-panel (merge (re-com/flex-child-style "1")
                          {:background-color "#ffffff"
                           :border           "1px solid lightgray"
                           :padding          "0px 20px 0px 20px"}))

(defn splitter-panel-title
      [text]
      [re-com/title :src (at)
       :label text
       :level :level3
       :style {:margin-top "20px"}])

(defn menu-panel
      []
      [re-com/v-box
       :src (at)
       :size "auto"
       :children [[re-com/box :child "album-1"]
                  [re-com/box :child "album-2"]
                  [re-com/box :child "album-3"]]])

(defn right-panel
      []
      [re-com/box :src (at)
       :size "auto"
       :child [:div {:style rounded-panel}
               [re-com/v-box
                :children [[re-com/box :child "image1"]
                           [re-com/box :child "image2"]
                           [re-com/box :child "image3"]]]]])

(defn home-title []
  (let [name (re-frame/subscribe [::subs/name])]
    [re-com/h-split
     :height "calc(100vh - 81px)"
     :src   (at)
     :panel-1 [menu-panel]
     :panel-2 [right-panel]
     :initial-split "20%"]))

(defn home-panel []
  [re-com/v-box
   :src      (at)
   :gap      "1em"
   :children [[home-title]]])

(defmethod routes/panels :home-panel [] [home-panel])

;; main

(defn main-panel []
  (let [active-panel (re-frame/subscribe [::subs/active-panel])]
    [re-com/v-box
     :src      (at)
     :height   "100%"
     :children [[home-panel]]]))
