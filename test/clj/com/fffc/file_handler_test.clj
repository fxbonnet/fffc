(ns com.fffc.file-handler-test
  (:require [clojure.test :refer :all]

            [com.fffc.file-handler :as fh]
            [clojure.java.io :as io]))

(deftest column-definitions-test
  (testing "One of the column definition has only 2 fields"
    (try
      (fh/column-definitions (io/resource "meta/meta-missing-length.csv"))
      (catch Exception e
        (let [result (ex-data e)]
          (is (= (:message result) "Invalid integer value: string"))))))

  (testing "Invalid data type 'integer' in the column definition"
    (try
      (fh/column-definitions (io/resource "meta/meta-invalid-data-type.csv"))
      (catch Exception e
        (let [result (ex-data e)]
          (is (= (:message result) "Invalid column definition."))))))
  ;;TODO Can expand the test cases
  )

(deftest line->csv-data-array-test
  (let [column-defs (fh/column-definitions (io/resource "meta/meta-success.csv"))]
    (testing "Test invalid date in data file"
      (try
        (fh/line->csv-data-array column-defs "1970-13-01John           Smith           81.5")
        (catch Exception e
          (let [result (ex-data e)]
            (is (= (:message result) "Line doesn't match the column definition."))))))
    ;;TODO Can expand the test cases
    ))