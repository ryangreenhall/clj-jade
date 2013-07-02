(ns clj-jade.core
  (:import [de.neuland.jade4j JadeConfiguration]
           [de.neuland.jade4j.template FileTemplateLoader]))

;; Maintains the configuration to be used when rendering templates.
(def config (atom (JadeConfiguration.)))

(defn set-template-dir
  [config template-dir-path]
  (.setTemplateLoader config (FileTemplateLoader. template-dir-path "UTF-8")))

(defn pretty-print
  [config is-pretty]
  (.setPrettyPrint config is-pretty))

(defn cache
  [config should-cache]
  (.setCaching config false))

(defn create-config
  [opts]
  (let [jade-config (JadeConfiguration.)]
    (when (:template-dir opts)
      (set-template-dir jade-config (:template-dir opts)))

    (when (:pretty-print opts)
      (pretty-print jade-config (:pretty-print opts)))

    (when (:cache? opts)
      (cache jade-config (:cache? opts)))
    jade-config))

(defn default-config
  "Changes the underlying config to the config specified"
  [new-config]
  (reset! config new-config))

(defn configure
  "Configures the underlying JadeConfig with the options passed. The config created upon
   the most recent invocation will be used when rendering templates"
  [opts]
  (default-config (create-config opts)))

(defn- template
  [template-path]
  (.getTemplate @config template-path))

(defn render
  [template-path data]
  (.renderTemplate @config (template template-path) (clojure.walk/stringify-keys data)))
