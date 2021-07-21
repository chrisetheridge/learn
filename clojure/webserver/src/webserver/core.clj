(ns webserver.core
  (:require 
   [webserver.repl :as repl]))

(defn -main [& args]
  (println "webserver start")
  (repl/start-nrepl! 8888)
  (System/exit 0))
