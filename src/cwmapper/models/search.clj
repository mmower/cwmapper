(ns cwmapper.models.search
  (:require [clojure.string :as str]
            [environ.core :refer [env]]
            [org.httpkit.client :as http]
            [clojure.data.json :as json]
            [cemerick.url :refer [url url-encode]]))

;; Ordered because Googles mapping API will return results in the same order
(def locations [{:name     "Bloomsbury"
                 :postcode "WC1B3HD"}
                {:name     "City"
                 :postcode "EC2A4BT"}
                {:name     "Farringdon"
                 :postcode "EC1M5RJ"}
                {:name     "Paddington"
                 :postcode "W26BD"}
                {:name     "Shoreditch"
                 :postcode "EC2A4BX"}
                {:name     "Whitechapel"
                 :postcode "E14TT"}])

(def base-url "https://maps.googleapis.com/maps/api/distancematrix/json")

(defn location-postcodes [locations]
  (url-encode (str/join "|" (map :postcode locations))))

(defn search-url [postcode]
  (str base-url "?origins=" (url-encode postcode) "&destinations=" (location-postcodes locations) "&mode=transit&key=" (env :google-api-key)))

(defn map-durations [results]
  (let [results' (get-in results ["rows" 0 "elements"])]
    (map (fn [location result]
           (-> location
               (assoc :distance (get-in result ["distance" "text"]))
               (assoc :duration (get-in result ["duration" "text"])))) locations results')))

(defn search [postcode]
  (let [{:keys [status body error] :as resp} @(http/get (search-url postcode))]
    (if error
      (println "Failed, exception is: " error)
      (map-durations (json/read-str body)))))
