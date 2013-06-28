# clj-jade

A thin wrapper around jade4j.

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
        li #{guitar.name} by #{guitar.maker} 
```

Java model

```clojure
{"pageName" "My Guitars"
 "guitars" [{"model" "Hummingbird" "maker" "Gibson"} 
            {"model" "Telecaster" "maker" "Fender"}]}
```

## License

Copyright © 2013 FIXME

Distributed under the Eclipse Public License, the same as Clojure.
