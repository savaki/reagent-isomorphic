(ns isomorphic.tools
  (:require [reagent.core :as reagent :refer [atom]]
            [main.core :as routes]))

(enable-console-print!)

(defn template [{:keys [title body production state-js]}]
  (let [json (.stringify js/JSON state-js)]
    [:html
     [:head
      [:meta {:charset "utf-8"}]
      [:title title]
      [:meta {:name    "viewport"
              :content "width=device-width, initial-scale=1.0"}]
      [:script {:type                    "text/javascript"
                :dangerouslySetInnerHTML {:__html (str "var state = " json ";")}}]
      ]
     [:body
      [:div#app body]
      (when (not production)
        [:script {:type "text/javascript" :src "/scripts/goog/base.js"}])
      [:script {:type "text/javascript" :src "/scripts/app.js"}]
      (when (not production)
        [:script {:type "text/javascript" :dangerouslySetInnerHTML {:__html "goog.require('isomorphic.client');"}}])
      ]]))

(defn ^:export render-page [value]
  (let [value-clj (if (object? value) (js->clj value) value)
        value-js  (if (object? value) value (clj->js value))
        state     (reagent/atom value-clj)]
    (reagent/render-to-static-markup (do
                                       (template {:title      "title"
                                                  :body       [routes/render-page state]
                                                  :state-js   value-js
                                                  :production false})))))
