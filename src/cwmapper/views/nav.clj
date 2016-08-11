(ns cwmapper.views.nav
  (:require [cwmapper.views.search :refer [search-form]]))

(defn suffix-id [id suffix]
  (-> id
      (name)
      (str "-" suffix)
      (keyword)))

(defn nav-bar [id]
  (let [collapse-id (suffix-id id "collapse")]
    [:nav.navbar.navbar-inverse.navbar-fixed-top {:id id}
     [:div.container-fluid
      [:div.navbar-header
       [:button.navbar-toggle.collapsed {:type          "button"
                                         :data-toggle   "collapse"
                                         :data-target   (str "#" collapse-id)
                                         :aria-expanded "false"}
        [:span.sr-only "Toggle Navigation"]
        [:span.icon-bar]
        [:span.icon-bar]
        [:span.icon-bar]]
       [:a.navbar-brand {:href "#"} "Central Working Mapper"]]
      [:div.collapse.navbar-collapse {:id collapse-id}
       (search-form ["navbar-form" "navbar-right"])]]]))

