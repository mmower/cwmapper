(ns cwmapper.views.mapper
  (:require [compojure.core :refer :all]
            [environ.core :refer [env]]
            [cwmapper.models.search :as search]
            [cwmapper.views.layout :refer [common-layout]]
            [cwmapper.views.nav :refer [nav-bar]]
            [cwmapper.views.search :refer [search-form]]
            [cemerick.url :refer [url-encode]]))

(def base-url "https://www.google.com/maps/preview?")

(defn directions-url [start end]
  (str base-url "saddr=" (url-encode start) "&daddr=" (url-encode end) "&dirflg=r"))

(defn home-page [request]
  (common-layout
    (nav-bar "main-nav")
    [:div.container
     [:h1 "Welcome to the Central Working Mapper"]
     [:p "Unofficial app to help you find the nearest Central Working club to any location."]
     [:p "Enter the post code of where you want to meet and the mapper will show you the distance to each of the Central Working clubs in London."]
     [:p "This app is using the Free tier of the Google Maps API and Heroku so it might not stand up to a lot of use. It's intended to demonstrate whether it's a useful function."]
     [:p.text-muted "Designed & Developed by " [:a {:href "http://mwrvld.com/" :title "Mower/Valdemarin"} "M/V"]]]))

(defn results-page [{{:keys [postcode]} :params}]
  (let [origin postcode
        results (search/search origin)]
    (clojure.pprint/pprint results)
    (common-layout
      (nav-bar "main-nav")
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
           [:td [:a {:href (directions-url postcode origin)} "directions"]]])]])))

(defroutes mapper-routes
           (GET "/" [] home-page)
           (POST "/" [] results-page))
