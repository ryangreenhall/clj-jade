(defproject clj-jade "0.1.8-masztal"
  :description "Thin wrapper around jade4j"
  :url "https://github.com/ryangreenhall/clj-jade"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[de.neuland-bfi/jade4j "1.2.1"] [org.clojure/clojure "1.8.0"]]
  :repositories [["jade4j-releases" "https://raw.github.com/neuland/jade4j/master/releases"]]
  :aot          :all)
