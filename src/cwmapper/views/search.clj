(ns cwmapper.views.search
  (:require [clojure.string :as str]
            [environ.core :refer [env]]
            [ring.middleware.anti-forgery :refer [*anti-forgery-token*]]))

(defn search-form [classes]
  [:form {:class (str/join " " classes) :method "POST" :action "/"}
   [:input {:type "hidden" :name "__anti-forgery-token" :value *anti-forgery-token*}]
   [:input.form-control {:name "postcode" :placeholder "Postcode"}]
   [:input.form-control {:type "submit" :value "Find"}]])
