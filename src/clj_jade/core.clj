(ns clj-jade.core
  (:import [de.neuland.jade4j JadeConfiguration]
           [de.neuland.jade4j.template FileTemplateLoader]))

;; Maintains the configuration to be used when rendering templates.
(def config (atom (JadeConfiguration.)))

(defn set-template-dir
  [config template-dir-path]
  (println "setting template dir")
  (.setTemplateLoader config (FileTemplateLoader. template-dir-path "UTF-8")))

(defn pretty-print
  [config is-pretty]
  (.setPrettyPrint config is-pretty))

(defn create-config
  [opts]
  (let [jade-config (JadeConfiguration.)]
    (when (:template-dir opts)
      (set-template-dir jade-config (:template-dir opts)))

    (when (:pretty-print opts)
      (pretty-print jade-config (:pretty-print opts)))
    jade-config))

(defn default-config
  "Changes the underlying config to the config specified"
  [new-config]
  (reset! config new-config))

(defmacro configure
  "Configures the underlying JadeConfig with the options passed. The config created upon
   the most recent invocation will be used when rendering templates"
  [opts]
  `(let [opts# ~opts]
     (println "Jade is being configured..." (:template-dir opts#) (:cache? opts#))
     (default-config (create-config opts#))))

(defn- template
  [template-path]
  (.getTemplate @config template-path))

(defn render
  [template-path data]
  (.renderTemplate @config (template template-path) data))
