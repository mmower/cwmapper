(ns cwmapper.views.mapper
  (:require [compojure.core :refer :all]
            [ring.middleware.anti-forgery :refer [*anti-forgery-token*]]
            [cwmapper.models.search :as search]
            [cwmapper.views.layout :refer [common-layout]]
            [cemerick.url :refer [url-encode]]))

(def base-url "https://www.google.com/maps/preview?")

(defn directions-url [start end]
  (str base-url "saddr=" (url-encode start) "&daddr=" (url-encode end) "&dirflg=r"))

(defn home-page [request]
  (common-layout
    [:div.container
     [:form.form {:method "POST" :action "/search"}
      [:input {:type "hidden" :name "__anti-forgery-token" :value *anti-forgery-token*}]
      [:label "Postcode"]
      [:input.form-control {:name "postcode" :value "W1F 8GB"}]
      [:input.form-control {:type "submit"}]]]))

(defn results-page [{{:keys [postcode]} :params}]
  (let [origin postcode
        results (search/search origin)]
    (common-layout
      [:div.container
       [:table.table
        [:caption "Nearest Central Working to " origin]
        [:thead
         [:tr
          [:th "Club"]
          [:th "Postcode"]
          [:th "Distance"]
          [:th "Time"]
          [:th "Map"]]]
        (for [{:keys [name postcode distance duration]} results]
          [:tr
           [:td name]
           [:td postcode]
           [:td distance]
           [:td duration]
           [:td [:a {:href (directions-url origin postcode)} "directions"]]])]])))

(defroutes mapper-routes
           (ANY "/" [] home-page)
           (POST "/search" [] results-page))
