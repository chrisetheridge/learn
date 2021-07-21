(ns webserver.repl
    (:require
     [nrepl.server :as nrepl]))

(defonce *nrepl-server (atom nil))

(defn nrepl-handler []
  (require 'cider.nrepl)
  (ns-resolve 'cider.nrepl 'cider-nrepl-handler))

(defn start-nrepl! [port & [wrap]]
  (let [server (nrepl.server/start-server :bind "0.0.0.0"
                                          :port port
                                          :handler (cond-> (nrepl-handler)
                                                     wrap wrap))]
    (println "[nrepl] Starting nrepl on port" port)
    (reset! *nrepl-server server)
    server))

(defn stop-nrepl! []
  (nrepl.server/stop-server @*nrepl-server)
  (reset! *nrepl-server nil))