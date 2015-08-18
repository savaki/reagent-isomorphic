(ns isomorphic.ajax
  (:require [ajax.core :as cljs-ajax]
            [isomorphic.core :as isomorphic]))

(enable-console-print!)

; The dirty flag keeps track of whether or not an ajax call should be invoked CLIENT side.
; The intent is to 'debounce' the first request.  Without what would happen is the server side
; would request a resource and as soon as the page was loaded on the client side, the client side
; would immediatey do the same thing resulting in two back to back requests for the same resource.
;
(def dirty
  (atom false))

; restler is an instance of the restler object on windows
;
(def restler
  (if isomorphic/serverSide? js/restler nil))

; get-restler invokes a GET request via restler.  This will only be executed on the server side
;
(defn get-restler [path opts]
  (let [handler       (get opts :handler)
        error-handler (get opts :error-handler)]
    (-> restler
        (.get path)
        (.on "complete" #(do (println "on complete")
                             (handler %)))
        (.on "timeout" #(do (println "on timeout")
                            (error-handler %))))))

; isomorphic GET request.  Good on both client and server side
;
(defn GET [path opts]
  (if isomorphic/clientSide?
    (if @dirty (cljs-ajax/GET path opts))
    (get-restler path opts)))

; POST request - should only ever be used on the client side
;
(defn POST [path opts]
  (if isomorphic/clientSide?
    (cljs-ajax/POST path opts)
    (println "ERROR - POST may only be invoked on the client side!")))
