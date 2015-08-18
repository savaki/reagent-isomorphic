(ns isomorphic.server
  (:require [cljs.nodejs :as nodejs]
            [isomorphic.tools :as tools]
            [isomorphic.core :as isomorphic]
            [main.core :as main]))

(enable-console-print!)

; handle-request is an adapter function to take requests from express and route them to our reagent app
;
(defn handle-request [req res]
  (let [path   (.-path req)
        params (js->clj (.-query req))]
    (isomorphic/dispatch! {:uri-path     path
                           :query-params params
                           :callback     #(.send res (tools/render-page %))})))


; nodejs application launches from this point
;
(defn ^:export start []
  (main/register-routes)

  (let [env-port    (-> nodejs/process .-env .-PORT)        ; what port to run on?
        port        (or env-port "3000")

        env-docroot (-> nodejs/process .-env .-DOCROOT)     ; location of static content
        docroot     (or env-docroot ".")

        express     (nodejs/require "express")              ; load express web server
        app         (express)

        st          (nodejs/require "st")                   ; load st module to serve static content
        mount       (st (clj->js {:path docroot, :url "/"}))

        route-keys  (isomorphic/routes-for-express)         ; register the routes
        with-route  (fn [app path] (.get app path handle-request))
        routed-app  (reduce with-route app route-keys)
        ]

    (println "Starting server:")
    (println "  DOCROOT:" docroot)
    (println "  PORT:   " port)

    (doto routed-app
      (.use mount)
      (.listen port)
      (println " Express server started on port:" port))))

