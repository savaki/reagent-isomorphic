(ns main.views.home
  (:require
    [isomorphic.core :as isomorphic]))

(enable-console-print!)

(defn render [state]
  (let [location (if isomorphic/clientSide? "client" "server")]
    [:div "home page rendered on " location " side"]))
