(ns isomorphic.core
  (:require [secretary.core :as secretary :refer-macros [defroute]]))

(enable-console-print!)

; running on server side?
;
(def serverSide? (nil? (.-XMLHttpRequest js/window)))

; running on client side?
;
(def clientSide? (not serverSide?))

; routes for application
;
(def routes (atom nil))

; utility to allow routes to be defined on both the server and client side in one call
;
; client side - we default to secretary, so go ahead and bind to secretary directly
; server side - we'll probably use express, but in any event store the meta data so we
;               can decide later how the data should be formatted
;
(defn defroute! [route]
  (let [path    (get route :path)
        handler (get route :handler)]
    (println "registering route," path)

    ; register route with secretary
    ;
    (defroute (str path) {:as params}
              (handler params))

    ; register route
    ;
    (swap! routes (fn [x]
                    (if (nil? x)
                      (list route)
                      (vec (flatten (conj (list route) x))))))))

; extract a list of paths for consumption by express
;
(defn routes-for-express []
  (map #(get % :path) @routes))

; dispatch the request to the appropriate action
;
; we've taken the standard secretary and modified it to pass state and callback parameters
; required for handling server side requests
;
(defn dispatch!
  "Dispatch an action for a given route if it matches the URI path."
  [{:keys [uri-path query-params callback state]}]
  (let [{:keys [action params]} (secretary/locate-route uri-path)
        action (or action identity)
        params (merge params query-params)
        params (assoc params :callback callback :state state)]
    (action params)
    nil))                                                   ; HACK - without this, secretary/core.cljs:305 throws a s.replace is not a function
