# reagent-isomorphic

An example of a reagent isomorphic application

This is a sample application intended to demonstrate reagent running on both the server and client side.
It is intended only as a proof of concept.  This is definitely not production grade.

## To Do

* Really too many to count
* Reduce the javascript bootstrap code, server.js
* Expand examples to show both server and client side requests
* More clearly delineate server and client side code
* Allow app to switch between production/debug mode 
 
## Prerequisite

* node must already be installed

## Setup

Download the node modules, closure nodejs shim, react library and other artifacts.

```
infra/setup.sh
```

## Starting the server

Shell #1

```
infra/lein cljsbuild auto dev node
```

Shell #2

```
infra/server.sh
```

The following URLs are enabled:

* (http://localhost:3000/home)[http://localhost:3000/home]
* (http://localhost:3000/weather/london)[http://localhost:3000/weather/london]

## License

Copyright © 2015 Matt Ho

Distributed under the Eclipse Public License either version 1.0 or (at
your option) any later version.
