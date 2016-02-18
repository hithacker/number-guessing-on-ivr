(ns clojure-getting-started.web
  (:require [compojure.core :refer [defroutes GET PUT POST DELETE ANY]]
            [compojure.handler :refer [site]]
            [compojure.route :as route]
            [clojure.java.io :as io]
            [ring.adapter.jetty :as jetty]
            [environ.core :refer [env]]
            [clojure.data.xml :as xml]))

(def guessed-number (atom nil))

(defn create-xml [vec]
  (xml/emit-str (xml/sexp-as-element vec)))

(defn state-machine [{event :event data :data}]
  (if (= event "NewCall")
    (create-xml
      [:response
       [:collectdtmf
        [:playtext "Please enter the number"]]
       [:hangup]])
    (create-xml
      [:response
       [:playtext (str "You entered " data)]])))

(defn splash [params]
  (reset! guessed-number (rand-int 100))
  {:status 200
   :headers {"Content-Type" "application/xml"}
   :body (state-machine params)})


(defroutes app
  (GET "/" {{event :event} :params}
       (splash event))
  (ANY "*" []
       (route/not-found (slurp (io/resource "404.html")))))

(defn -main [& [port]]
  (let [port (Integer. (or port (env :port) 5000))]
    (jetty/run-jetty (site #'app) {:port port :join? false})))



;; For interactive development:
;; (.stop server)
;; (def server (-main))
