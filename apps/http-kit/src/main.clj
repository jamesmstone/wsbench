(ns main
  (:gen-class)
  (:use org.httpkit.server
        [ring.middleware.file-info :only [wrap-file-info]]
        [clojure.tools.logging :only [info]]
        [clojure.data.json :only [json-str read-json]]
        (compojure [core :only [defroutes GET POST]]
                   [route :only [files not-found]]
                   [handler :only [site]]
                   [route :only [not-found]])))

(defn handler [request]
  (with-channel request channel
    (on-close channel (fn [status] (println "channel closed: " status)))
    (on-receive channel (fn [data] ;; echo it back
                          (do (send! channel data))))))

(defn -main [& args]
  (run-server handler {:port 8080})
  (info "server started. http://127.0.0.1:8080"))
