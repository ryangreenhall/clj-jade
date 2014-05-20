# clj-jade

A thin wrapper around [jade4j](https://github.com/neuland/jade4j) to promote use with the Clojure community.

## Artifacts

```clojure
[clj-jade "0.1.5"]
```

## Example

index.jade

```
!!! 5
html
  head
    title= pageName
  body
    ol.guitars
      for guitar in guitars
        li #{guitar.maker} #{guitar.model} 
```


```clojure

(require '[clj-jade.core :as jade])

(jade/render "index.jade" {"pageName" "My Guitars"
                           "guitars" [{"model" "Hummingbird" "maker" "Gibson"} 
                                      {"model" "Telecaster" "maker" "Fender"}]})
                                      
;; or

(jade/render "index.jade" {:pageName "My Guitars"
                           :guitars [{:model "Hummingbird" :maker "Gibson"} 
                                     {:model "Telecaster"  :maker "Fender"}]})
```

Produces:

```html
<!DOCTYPE html>
<html>
  <head>
    <title>My Guitars</title>
  </head>
  <body>
    <ol class="guitars">
      <li>Gibson Hummingbird</li>
      <li>Fender Telecaster</li>
    </ol>
  </body>
</html>
```

## Configuration 

The default clj-jade configuration can be configured as follows.

```clojure

(require '[clj-jade.core :as jade])

(jade/configure {:template-dir "examples/templates/"
                 :pretty-print true
                 :cache? true})

(jade/render "index.jade" {"pageName" "My Guitars"
                           "guitars" [{"model" "Hummingbird" "maker" "Gibson"} 
                                      {"model" "Telecaster" "maker" "Fender"}]})
```
## Layouts and Includes

Please refer to the examples in the source to see how templates can be managed using layouts and includes. 

## Helpers 

Helpers provide a mechanism to register custom view related functions that can be called directly from templates. For example, it might be useful to
define helpers for date formatting, rounding etc.

### Defining a helper

```clojure
(ns clj-jade.example-helper.math
  (:gen-class
   :methods [[round [double] int]]))

(defn -round
  [this double]
  (Math/round double))
```
### Configuring your helper

```clojure
(jade/configure {:helpers {"math" (clj_jade.example_helper.math.)}})

```

### Helper invocation from with templates

```
!!! 5
html
  body
    h1 #(helpers.math.round(number))
```

```clojure
(jade/render "index.jade" {:number 2.6})
```

Produces:

```html
<!DOCTYPE html>
<html>
  <body>
    <h1>3</h1>
  </body>
</html>
```

Note that as helper implementations use :gen-class you will need to ensure that you apply AOT compiling for all helper classes. 
This can be specified using the :aot lein option.

## Acknowledgements

Thanks to the authors of [jade4j](https://github.com/neuland/jade4j) for bringing jade to the JVM.

## License

Copyright © 2013 

Distributed under the Eclipse Public License, the same as Clojure.
