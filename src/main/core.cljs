(ns main.core
  (:require [isomorphic.ajax :as ajax]
            [isomorphic.core :as isomorphic]
            [main.views.weather :as weather-page]
            [main.views.home :as home-page]
            ))

(enable-console-print!)

; with-page is a utility method to add a page key to a javascript map
;
; this is useful to add data to javascript
;
(defn assoc-js [js-state k v]
  (assoc (js->clj js-state) k v))

; echo provides a simple no-op method that passes through whatever state was provided
;
(defn echo [{:keys [callback state]}]
  (callback state))

; weather-in-london shows server and client side calls
;
(defn weather-in-london [{:keys [callback state]}]
  (let [url "http://api.openweathermap.org/data/2.5/weather?q=London,uk"]
    (ajax/GET url {:handler #(callback (assoc-js % "page" "weather"))})))

; render-page is the REQUIRED entry point for page rendering
;
(defn ^:export render-page [state]
  (let [page (get @state "page")
        page (if (nil? page) "home" page)]
    (println "rendering" page "page")
    (cond (= page "weather") (weather-page/render state)
          :else (home-page/render state))))

; register-routes is another REQUIRED entry point for both client and server side
;
(defn ^:export register-routes []
  (isomorphic/defroute! {:path    "/home"
                         :handler echo})

  (isomorphic/defroute! {:path    "/weather/london"
                         :handler weather-in-london})
  )

