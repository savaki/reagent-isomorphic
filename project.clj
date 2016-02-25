(defproject reagent-isomorphic "0.1.0-SNAPSHOT"
  :description "reagent-isomorphic"
  :url "https://github.com/savaki/reagent-isomorphic"
  :dependencies [[org.clojure/clojure "1.7.0"]
                 [org.clojure/clojurescript "1.7.48"]
                 [secretary "1.2.3"]
                 [kibu/pushy "0.3.2"]
                 [reagent "0.5.0"]
                 [cljs-ajax "0.3.14"]]

  :node-dependencies [[express "4.13.3"]
                      [st "0.5.5"]
                      [restler "3.3.0"]]

  :plugins [[lein-environ "1.0.0"]
            [lein-cljsbuild "1.0.4"]
            [lein-npm "0.6.1"]]

  :npm {:root "target/node"}

  :clean-targets ["target"]

  :cljsbuild {:builds [{:id           "dev"
                        :source-paths ["src"]
                        :compiler     {:optimizations :none
                                       :output-to     "target/dev/scripts/app.js"
                                       :output-dir    "target/dev/scripts"
                                       :externs       ["lib/externs.js"]
                                       :pretty-print  true
                                       :source-map    true}}
                       {:id           "prod"
                        :source-paths ["src"]
                        :compiler     {:optimizations :advanced
                                       :output-to     "target/prod/scripts/app.js"
                                       :output-dir    "target/prod/scripts"
                                       :externs       ["lib/externs.js"]
                                       :pretty-print  true}}
                       {:id           "node"
                        :source-paths ["src"]
                        :compiler     {:optimizations :none
                                       :output-to     "target/node/app.js"
                                       :output-dir    "target/node"
                                       :externs       ["lib/externs.js"]
                                       :pretty-print  true
                                       :source-map    true}}
                       ]}

  :min-lein-version "2.0.0")
