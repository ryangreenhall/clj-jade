(ns clj-jade.example-helper.math
  (:gen-class
   :methods [[round [double] int]]))

(defn -round
  [this double]
  (Math/round double))
