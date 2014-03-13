(ns clj-jade.core-test
  (:import [clj_jade.example_helper.math])
  (:require [clj-jade.core :as jade])
  (:use clojure.test))

(jade/configure {:pretty-print true
                 :cache? true})

(deftest rendering
  (testing "Parameter substitution with strings as keys"
    (is (.contains (jade/render "examples/templates/home.jade" {"name" "Jade"}) "Hello Jade")))

  (testing "Parameter substitution with clojure keywords as keys"
    (is (.contains (jade/render "examples/templates/home.jade" {:name "Jade"}) "Hello Jade")))

  (testing "with base template directory specified"
    (jade/configure {:template-dir "examples/templates/"})
    (is (.contains (jade/render "home.jade" {"name" "Jade"}) "Hello Jade")))

  (testing "An empty option can be removed"
    (is (= (jade/render "home.jade") (jade/render "home.jade" {}))))


  (testing "helpers"
    (jade/configure {:template-dir "examples/templates/"
                     :helpers {"math" (clj_jade.example_helper.math.)}})
    (is (.contains (jade/render "helpers.jade" {"number" 2.6}) "3"))))
