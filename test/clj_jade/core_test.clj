(ns clj-jade.core-test
  (:require [clj-jade.core :as jade])
  (:use clojure.test))

(jade/configure {})

(deftest rendering
  (testing "Parameter substitution"
    (is (.contains (jade/render "examples/templates/home.jade" {"name" "Jade"}) "Hello Jade"))))
