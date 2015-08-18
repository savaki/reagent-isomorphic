(ns isomorphic.client
  (:require [clojure.string :as string]
            [reagent.core :as reagent :refer [atom]]
            [secretary.core :as secretary :refer-macros [defroute]]
            [pushy.core :as pushy :refer [push-state!]]
            [isomorphic.core :as isomorphic]
            [main.core :as main]
            ))

(enable-console-print!)

; retrieves the initial state directly from the page via the state var
;
(defn initial-state []
  (if (nil? js/state) {} (js->clj js/state)))

; database of client side state
;
(def app-state
  (let [state (initial-state)]
    (reagent/atom state)))

; the initial view that reagent binds to
;
(defn app-view [app-state]
  [main/render-page app-state])

; ->clj is a utility method that ensures value is a clj object
;
; ->clj takes either clj value or a #js object
(defn ->clj [value]
  (if (object? value)
    (js->clj value)
    value))

; HACK - because the server side requires us to make state local to each request (as opposed to global on client side)
;        we need secretary to accept more parameters than it does normally, requiring this custom dispatcher.
;
; TODO - find a better way of doing this
;
(defn dispatcher [path]
  (let [uri-path (-> path (string/split #"\?") first)]
    (isomorphic/dispatch! {:uri-path uri-path
                           :state    app-state
                           :callback #(reset! app-state (->clj %))})))


(def history (when isomorphic/clientSide?
               (pushy/pushy secretary/dispatch! dispatcher)))

; mount the application via reagent
;
(if isomorphic/clientSide?
  (do (main/register-routes)
      (reagent/render-component [app-view app-state]
                                (.getElementById js/document "app"))
      (pushy/start! history)))


