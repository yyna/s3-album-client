(ns client.core
  (:require
    [reagent.dom :as rdom]
    [reagent.core :as reagent]
    [re-frame.core :as re-frame]
    [client.events :as events]
    [client.views :as views]
    [client.config :as config]
    ["aws-amplify" :default Amplify :as amp]
    ["aws-amplify-react" :refer (withAuthenticator)]
    ["/aws-exports.js" :default aws-exports]))


(defn dev-setup []
      (when config/debug?
                (println "dev mode")))

(def root-view
  (reagent/adapt-react-class
    (withAuthenticator
       (reagent/reactify-component views/main-panel) true)))

(defn ^:dev/after-load mount-root []
      (re-frame/clear-subscription-cache!)
      (.configure Amplify aws-exports)
      (re-frame/dispatch-sync [::events/initialize-db])
      (rdom/render [root-view] (.getElementById js/document "app")))

(defn init []
      (dev-setup)
      (mount-root))