# clj-jade

A thin wrapper around [jade4j](https://github.com/neuland/jade4j)

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
        li #{guitar.maker} #{guitar.name} 
```


```clojure

(require '[clj-jade.core :as jade])

(jade/render "index.jade" {"pageName" "My Guitars"
                           "guitars" [{"model" "Hummingbird" "maker" "Gibson"} 
                                      {"model" "Telecaster" "maker" "Fender"}]})
```

Produces:

```html
<!DOCTYPE html>
<html>
  <head>
    <title>My Bookshelf</title>
  </head>
  <body>
    <ol class="guitars">
      <li>Gibson Hummingbird</li>
      <li>Fender Telecaster</li>
    </ol>
  </body>
</html>
```

## License

Copyright © 2013 FIXME

Distributed under the Eclipse Public License, the same as Clojure.
