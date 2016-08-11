(ns cwmapper.handler
  (:require [compojure.core :refer :all]
            [compojure.route :as route]
            [ring.middleware.defaults :refer [wrap-defaults site-defaults]]
            [cwmapper.views.mapper :refer [mapper-routes]]))

(defroutes app-routes
           (route/resources "/")
           (route/not-found "Not Found"))

(def app (-> (routes mapper-routes app-routes)
             (wrap-defaults site-defaults)))
