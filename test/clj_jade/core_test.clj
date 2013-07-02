(ns clj-jade.core-test
  (:require [clj-jade.core :as jade])
  (:use clojure.test))

(jade/configure {:pretty-print true
                 :cache? true})

(deftest rendering
  (testing "Parameter substitution"
    (is (.contains (jade/render "examples/templates/home.jade" {"name" "Jade"}) "Hello Jade")))

  (testing "Parameter substitution with clojure keywords"

    (is (.contains (jade/render "examples/templates/home.jade" {:name "Jade"}) "Hello Jade")))

  (testing "with base template directory specified"
    (jade/configure {:template-dir "examples/templates/"})

    (is (.contains (jade/render "home.jade" {"name" "Jade"}) "Hello Jade"))))
