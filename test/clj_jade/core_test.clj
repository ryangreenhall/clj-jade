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

  (testing "Parameter substitution with clojure keywords transformed to camel-back notation"
        (is (.contains (jade/render "examples/templates/camel-back-home.jade" {:user-name "Jade"
                                                                               :user-last-name "Jadisson"}) "Hello Jade Jadisson")))
  (testing "Parameter substitution with strings transformed to camel-back notation"
    (is (.contains (jade/render "examples/templates/camel-back-home.jade" {"user-name" "Jade"
                                                                           "user-last-name" "Jadisson"}) "Hello Jade Jadisson")))

  (testing "with base template directory specified"
    (jade/configure {:template-dir "examples/templates/"})
    (is (.contains (jade/render "home.jade" {"name" "Jade"}) "Hello Jade")))


  (testing "helpers"
    (jade/configure {:template-dir "examples/templates/"
                     :helpers {"math" (clj_jade.example_helper.math.)}})
    (is (.contains (jade/render "helpers.jade" {"number" 2.6}) "3"))))
