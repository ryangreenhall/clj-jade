(ns clj-jade.core
  (:require [clojure.string :refer [split capitalize lower-case]]
            [clojure.walk :refer [postwalk]])
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

(defn set-shared-vars
  [config vars]
  (.setSharedVariables config vars))

(defn create-config
  [opts]
  (let [jade-config (JadeConfiguration.)]
    (when (:template-dir opts)
      (set-template-dir jade-config (:template-dir opts)))

    (when (:pretty-print opts)
      (pretty-print jade-config (:pretty-print opts)))

    (when (:cache? opts)
      (cache jade-config (:cache? opts)))

    (when (:helpers opts)
      (set-shared-vars jade-config (assoc {} "helpers" (:helpers opts))))
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

(defn- to-camel-back
  "Transfroms a string into camel-back notation if it has any non ASCII characters.
   Example:
   username  -> username
   user-name -> userName
   user.name -> userName"
  [^String key]
  (let [[word & more] (split key #"[^a-zA-Z]")]
    (apply str (cons (lower-case word) (map capitalize more)))))

(defn- camel-back-keys
  "Recursively transforms all map keys from keywords to strings in camel-back notation."
  [m]
  (let [f (fn [[k v]] (if (keyword? k) [(to-camel-back (name k)) v] [(to-camel-back k) v]))]
    ;; only apply to maps
    (postwalk (fn [x] (if (map? x) (into {} (map f x)) x)) m)))

(defn render
  [template-path data]
  (.renderTemplate @config (template template-path) (camel-back-keys data)))
