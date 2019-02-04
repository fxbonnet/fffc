(defproject fffc "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.9.0"]
                 [org.clojure/data.csv "0.1.4"]
                 [clj-time "0.15.0"]]

  :source-paths ["src/clj"]
  :test-paths ["test/clj"]
  :resource-paths ["resources" "resources/test" "resources/test/meta"]

  :aot [com.fffc.main]
  :main com.fffc.main

  :profiles {:dev [{:resource-paths ["resources" "resources/test"]}]})
