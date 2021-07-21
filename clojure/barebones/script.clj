;; For use with Babashka

(require '[clojure.java.io :as io]
         '[clojure.string :as string])

(def files (apply concat
                  [(io/file "deps.edn")]
                  (filter #(.isFile %) (file-seq (io/file "src")))
                  (filter #(.isFile %) (file-seq (io/file "dev")))))

(def paths (map #(.getPath %) files))

(def PROJECT_NAME_RX #"\{\{PROJECT_NAME\}\}")

(defn replace-project-name [s project-name]
  (string/replace s PROJECT_NAME_RX project-name))

(defn process [[output-dir project-name] paths]
  (doseq [p paths]
    (let [real-p (str output-dir "/"
                      project-name "/"
                      (replace-project-name p project-name))]
      (println "Creating" real-p)
      (io/make-parents real-p)
      (spit real-p (replace-project-name (slurp p) project-name)))))

;; bb script.clj /path/for/new/project new-project-name
(process *command-line-args* paths)
