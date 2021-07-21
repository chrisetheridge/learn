;; For use with Babashka

(require '[clojure.java.io :as io]
         '[clojure.string :as string])

(def script-location
  (string/join "/" (butlast (string/split *file* #"/"))))

(def files
  ["deps.edn"
   "src/{{PROJECT_NAME}}/core.clj"
   "src/{{PROJECT_NAME}}/repl.clj"]
  #_(apply concat
           [(io/file "deps.edn")]
           (filter #(.isFile %) (file-seq (io/file "src")))
           (filter #(.isFile %) (file-seq (io/file "dev")))))

(def PROJECT_NAME_RX #"\{\{PROJECT_NAME\}\}")

(defn replace-project-name [s project-name]
  (string/replace s PROJECT_NAME_RX project-name))

(defn process [[output-dir project-name] files]
  (doseq [f files]
    (let [real-p (str output-dir "/"
                      project-name "/"
                      (replace-project-name f project-name))]
      (println "Creating" real-p "from" f)
      (io/make-parents real-p)
      (spit real-p (replace-project-name (slurp (str script-location "/" f)) project-name)))))

;; bb script.clj /path/for/new/project new-project-name
(process *command-line-args* files)
