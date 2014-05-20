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
  
  (testing "Rendering a page without passing the parameters map"
    (is (.contains (jade/render "examples/templates/noparam.jade") "Hello no passed params")))

  (testing "with base template directory specified"
    (jade/configure {:template-dir "examples/templates/"})
    (is (.contains (jade/render "home.jade" {"name" "Jade"}) "Hello Jade")))


  (testing "helpers"
    (jade/configure {:template-dir "examples/templates/"
                     :helpers {"math" (clj_jade.example_helper.math.)}})
    (is (.contains (jade/render "helpers.jade" {"number" 2.6}) "3"))))
