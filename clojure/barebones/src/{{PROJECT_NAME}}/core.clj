(ns {{PROJECT_NAME}}.core
  (:require 
   [{{PROJECT_NAME}}.repl :as repl]))

(defn -main [& args]
  (println "{{PROJECT_NAME}} start")
  (repl/start-nrepl! 8888)
  (System/exit 0))
