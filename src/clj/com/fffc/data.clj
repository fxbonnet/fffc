(ns com.fffc.data
  (:require [clj-time.format :as tf]))

(defn format-date [date]
  "Convert date format from yyyy-MM-dd to dd/MM/yyyy"
  (try
    (->> date
         (tf/parse (tf/formatter "yyyy-MM-dd"))
         (tf/unparse (tf/formatter "dd/MM/yyyy")))
    (catch Exception e
      (throw (ex-info (str "Invalid date in the file :" date) {:exception e :message "Invalid date in the file" :date date})))))

(defn sting->numeric [str]
  (try
    (Double/parseDouble str)
    (catch Exception e
      (throw (ex-info (str "Invalid numeric value in the file :" str) {:exception e})))))

(defn string->int [val]
  (try
    (Integer/parseInt val)
    (catch Exception e
      (throw (ex-info (str "Invalid integer value in the file :" val)
                      {:exception e :message (str "Invalid integer value: " val)})))))
