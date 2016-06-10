(defproject photon-client "0.1.3"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :javac-options ["-target" "1.8" "-source" "1.8" "-Xlint:-options"]
  :dependencies [[org.clojure/clojure "1.8.0"]
                 [io.muoncore/muon-clojure "6.4-20160401135945"
                   :exclusions [[org.slf4j/slf4j-log4j12]]] ])
