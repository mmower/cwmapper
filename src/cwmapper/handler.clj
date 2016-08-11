(ns cwmapper.handler
  (:require [compojure.core :refer :all]
            [compojure.route :as route]
            [compojure.handler :refer [site]]
            [environ.core :refer [env]]
            [ring.middleware.defaults :refer [wrap-defaults site-defaults]]
            [ring.adapter.jetty :as jetty]
            [cwmapper.views.mapper :refer [mapper-routes]]))

(defroutes app-routes
           (route/resources "/")
           (route/not-found "Not Found"))

(def app (-> (routes mapper-routes app-routes)
             (wrap-defaults site-defaults)))

(defn -main [& [port]]
  (let [port (Integer. (or port (env :port) 3000))]
    (jetty/run-jetty (site #'app) {:port port :join? false})))
