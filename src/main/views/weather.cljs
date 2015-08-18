(ns main.views.weather
  (:require
    [isomorphic.core :as isomorphic]
    [goog.string :as gstring]
    [goog.string.format]))

(enable-console-print!)

(defn kelvin-to-farenheit [k]
  (+ (* (- k 273.15) 1.8) 32.0))

(defn render [state]
  (let [value       @state
        city        (get-in value ["name"])
        weather     (get-in value ["weather" 0 "description"])
        temp-kelvin (get-in value ["main" "temp"])
        temp        (gstring/format "%.1f" (kelvin-to-farenheit temp-kelvin))
        location    (if isomorphic/clientSide? "client" "server")]
    [:div
     [:table
      [:tr
       [:td "City"]
       [:td city]]
      [:tr
       [:td "Weather"]
       [:td weather]]
      [:tr
       [:td "Temperature"]
       [:td temp "F"]]]
     [:p "rendered " location " side"]]))

