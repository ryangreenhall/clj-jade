(ns clj-jade.core
  (:import [de.neuland.jade4j JadeConfiguration]))

(def config (JadeConfiguration.))

(defn- template
  [template-path]
  (.getTemplate config template-path))

(defn render
  [template-path data]
  (.renderTemplate config (template template-path) data))
