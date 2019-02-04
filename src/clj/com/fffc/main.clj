(ns com.fffc.main
  (:gen-class)
  (:require [com.fffc.file-handler :as fh]))

(defn -main [arg1 arg2]
  (fh/fixed-file->csv! arg1 arg2))

(comment (-main))