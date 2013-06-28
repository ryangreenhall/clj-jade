(ns clj-jade.core
  (:import [de.neuland.jade4j JadeConfiguration]
           [de.neuland.jade4j.template FileTemplateLoader]))

;; Maintains the configuration to be used when rendering templates.
(defonce _config (atom (JadeConfiguration.)))


(defn set-template-dir
  [config template-dir-path]
  (.setTemplateLoader config (FileTemplateLoader. template-dir-path "UTF-8")))

;;TemplateLoader loader = new FileTemplateLoader("/templates/", "UTF-8");
;;config.setTemplateLoader(loader);

(defn default-config
  "Changes the underlying config to the config specified"
  [config]
  (reset! _config config))

(defmacro configure
  "Configures the underlying JadeConfig with the options passed. The config created upon
   the most recent invocation will be used when rendering templates"
  [opts]
  `(let [opts# ~opts]
     (println "Jade is being configured..." (:template-dir opts#) (:cache? opts#))
     (default-config @_config)))

(defn- template
  [template-path]
  (.getTemplate @_config template-path))

(defn render
  [template-path data]
  (.renderTemplate @_config (template template-path) data))
