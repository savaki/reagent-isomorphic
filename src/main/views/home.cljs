(ns main.views.home)

(enable-console-print!)

(defn render [state]
  [:div "home page" @state])
