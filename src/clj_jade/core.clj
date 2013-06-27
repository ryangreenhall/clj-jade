(ns clj-jade.core
  (:import [de.neuland.jade4j JadeConfiguration]))

(defonce _config (atom (JadeConfiguration.)))

(defn default-config
  ""
  [config]
  (reset! _config config))

(defn configure
  "Configures the underlying JadeConfig with the options passed. The config created upon
   the most recent invocation will be used when rendering templates"
  [opts]
  (default-config config))

(defn- template
  [template-path]
  (.getTemplate @_config template-path))

(defn render
  [template-path data]
  (.renderTemplate @_config (template template-path) data))
