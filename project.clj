(defproject clj-jade "0.1.3"
  :description "Thin wrapper around jade4j"
  :url "https://github.com/ryangreenhall/clj-jade"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[de.neuland/jade4j "0.3.12"]]
  :profiles {:dev {:dependencies [[org.clojure/clojure "1.5.1"]]}}
  :repositories [["jade4j-releases" "https://raw.github.com/neuland/jade4j/master/releases"]]
  :aot          :all
  )
