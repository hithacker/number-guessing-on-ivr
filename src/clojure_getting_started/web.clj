(ns clojure-getting-started.web
  (:require [compojure.core :refer [defroutes GET PUT POST DELETE ANY]]
            [compojure.handler :refer [site]]
            [compojure.route :as route]
            [clojure.java.io :as io]
            [ring.adapter.jetty :as jetty]
            [environ.core :refer [env]]
            [clojure.data.xml :as xml]
            [taoensso.timbre :as timbre]))

(timbre/refer-timbre)

(def guessed-number (atom nil))

(defn create-xml [vec]
  (xml/emit-str (xml/sexp-as-element vec)))

(defn state-machine [{event :event data :data}]
  (info event)
  (info data)
  (cond
    (= event "NewCall")
      (do
        (reset! guessed-number (rand-int 100))
        (create-xml
          [:response
           [:collectdtmf
            [:playtext "Guess the number"]]]))
    (= event "GotDTMF")
      (let [num (Integer/parseInt data)]
        (cond
          (< num @guessed-number)
            (create-xml
              [:response
               [:collectdtmf
                [:playtext "Too low. Please try again."]]])
          (> num @guessed-number)
            (create-xml
              [:response
               [:collectdtmf
                [:playtext "Too high. Please try again."]]])
          :else
            (create-xml
              [:response
               [:playtext "Bingo! You guessed it right. Congrats."]
               [:hangup]])))
    :else
      (create-xml
        [:response [:hangup]])))

(defn splash [params]
  (info params)
  {:status 200
   :headers {"Content-Type" "application/xml"}
   :body (state-machine params)})


(defroutes app
  (GET "/" {params :params}
       (splash params))
  (ANY "*" []
       (route/not-found (slurp (io/resource "404.html")))))

(defn -main [& [port]]
  (let [port (Integer. (or port (env :port) 5000))]
    (jetty/run-jetty (site #'app) {:port port :join? false})))



;; For interactive development:
;; (.stop server)
;; (def server (-main))
