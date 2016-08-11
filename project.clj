(defproject cwmapper "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :min-lein-version "2.0.0"
  :dependencies [[org.clojure/clojure "1.8.0"]
                 [compojure "1.5.1"]
                 [ring/ring-defaults "0.2.1"]
                 [ring/ring-jetty-adapter "1.2.2"]
                 [http-kit "2.1.18"]
                 [org.clojure/data.json "0.2.6"]
                 [com.cemerick/url "0.1.1"]
                 [environ "1.1.0"]
                 [prone "1.1.1"]]
  :plugins [[lein-ring "0.9.7"]
            [lein-environ "1.1.0"]]
  :uberjar-name "cwmapper-0.1.0-SNAPSHOT-standalone.jar"
  :ring {:handler cwmapper.handler/app}
  :profiles {:dev     {:dependencies [[javax.servlet/servlet-api "2.5"]
                                      [ring/ring-mock "0.3.0"]]
                       :ring         {:stacktrace-middleware prone.middleware/wrap-exceptions}}
             :uberjar {:aot :all}})
