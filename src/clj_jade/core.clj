(ns clj-jade.core
  (:require clojure.walk)
  (:import [de.neuland.jade4j JadeConfiguration]
           [de.neuland.jade4j.template ClasspathTemplateLoader]
           [de.neuland.jade4j.template FileTemplateLoader]))

;; Maintains the configuration to be used when rendering templates.
(def ^:private config (atom {:conf (JadeConfiguration.)
                   :template-dir ""}))

(defn- set-template-dir!
  [config {:keys [classpath-loader template-dir]}]
  (when classpath-loader (.setTemplateLoader config (ClasspathTemplateLoader.))))

(defn- create-config
  [opts]
  (let [jade-config (JadeConfiguration.)]
    (set-template-dir! jade-config opts)

    (when-let [pretty-print (:pretty-print opts)]
      (.setPrettyPrint jade-config pretty-print))

    ; (when-let [cache (:cache? opts)]
    ;   (.setCaching config false)
    ;   (cache jade-config cache))

    (when-let [helpers (:helpers opts)]
      (.setSharedVariables jade-config (assoc {} "helpers" helpers)))
    jade-config))

(defn- default-config
  "Changes the underlying config to the config specified"
  [new-config]
  (reset! config new-config))

(defn configure
  "Configures the underlying JadeConfig with the options passed. The config created upon
   the most recent invocation will be used when rendering templates"
  [opts]
  (default-config {:conf (create-config opts)
                   :template-dir (:template-dir opts)}))

(defn- template
  [template-path]
  (.getTemplate (:conf @config) template-path))

(defn full-path [dir name]
  (if dir (str dir "/" name) name))

(defn render
  ([template-path] (render template-path {}))
  ([template-path data]
   (let [{:keys [conf template-dir]} @config]
     (.renderTemplate conf (template (full-path template-dir template-path)) (clojure.walk/stringify-keys data)))))
