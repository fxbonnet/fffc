(ns com.fffc.file-handler
  (:require [clojure.spec.alpha :as spec]
            [clojure.spec.alpha :as spec]
            [clojure.data.csv :as csv]
            [clojure.java.io :as io]
            [com.fffc.csv :as fffc-csv]
            [com.fffc.data :as data]))

(def data-types #{:string :date :numeric})

;;Specifications for column definitions
(spec/def ::column-name (comp not nil?))
(spec/def ::column-length (spec/and int? #(> % 0)))
(spec/def ::column-type (spec/and keyword? #(% data-types)))
(spec/def ::column-def
  (spec/keys
    :req-un [::column-name ::column-length ::column-type]))
(spec/def ::column-defs
  (spec/coll-of ::column-def :min-count 1))

(def spec-error-message-map
  {:column-name   "Column name should not be null."
   :column-length "Column length should be a number greater than 0."
   :column-type   "Column type should be string, date or numeric."})

(defn- spec-validation-error-message [spec-explain-data]
  "Compose the readable error message for a spec/explain-data"
  (let [errors (reduce #(conj %1 (str " Value: " (:val %2) " Error: "
                                      ((first (:path %2)) spec-error-message-map)))
                       [] (::spec/problems spec-explain-data))]
    (clojure.string/join ", " errors)))

(defn- ->column-def-map [column-def-vec]
  "Construct the column definition"
  (let [column-def (-> (zipmap [:column-name :column-length :column-type] column-def-vec)
                       (update-in [:column-length] data/string->int)
                       (update-in [:column-type] keyword))]
    column-def))

(defn column-definitions [meta-data-file-path]
  "Read the meta data from the file and constructs the list of column definitions"
  (let [column-definitions (->> (fffc-csv/read-csv meta-data-file-path)
                                (map ->column-def-map))]
    (if-let [explain-data (spec/explain-data ::column-defs column-definitions)]
      (throw (ex-info (str "Invalid column definition." (spec-validation-error-message explain-data))
                      {:message "Invalid column definition." :explain-data explain-data}))
      column-definitions)))

(defn- format-value [col-def val]
  (cond
    (= :string (:column-type col-def)) (clojure.string/trim val)
    (= :date (:column-type col-def)) (data/format-date (clojure.string/trim val))
    (= :numeric (:column-type col-def)) (data/sting->numeric (clojure.string/trim val))
    :default (throw (ex-info (str "Invalid data type") {:message "Invalid data type" :column-type (:column-type col-def)}))))

(defn line->csv-data-array [col-def line]
   (try
     (loop [line line
            col-def-loop col-def
            values []]
       (if (= (count values) (count col-def))
         values
         (recur (subs line (:column-length (first col-def-loop)))
                (rest col-def-loop)
                (conj values (format-value (first col-def-loop)
                                           (subs line 0 (:column-length (first col-def-loop))))))))
       (catch Exception e
         (throw (ex-info (str "Following line doesn't match the column definition. Line: " line
                              ". Column Definition: " (pr-str col-def))
                         {:exception e :line line :columns col-def :message "Line doesn't match the column definition."})))))

(defn- write-csv [writer data]
  (csv/write-csv writer data))

(defn fixed-file->csv!
  [meta-data-file data-file]
  "Convert the fixed length data file based on the metadata and write to a csv file"
  (try
    (let [col-defs (column-definitions meta-data-file)
          out-file-name (str "data-" (quot (System/currentTimeMillis) 1000) ".csv")]
      (with-open [reader (io/reader data-file)
                  writer (io/writer out-file-name)]
        (do
          (csv/write-csv writer [(reduce #(conj %1 (:column-name %2)) [] col-defs)]) ;; write the header
          (->> (line-seq reader)
               (map #(line->csv-data-array col-defs %))
               (write-csv writer))
          (println (str "Successfully written to " out-file-name)))))
    (catch Exception e
      (println (.getMessage e))
      (ex-data e))))