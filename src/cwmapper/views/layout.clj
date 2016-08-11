(ns cwmapper.views.layout
  (:require [hiccup.page :refer [html5 include-css include-js]]))

(defn common-layout [& body]
  (html5
    [:head
     [:title "CW Mapper"]
     (include-css "/css/bootstrap.min.css")
     (include-css "/css/bootstrap-theme.min.css")
     (include-css "/css/cwmapper.css")
     (include-js "/js/jquery-2.2.4.js")
     (include-js "/js/bootstrap.min.js")]
    [:body#body body]))
