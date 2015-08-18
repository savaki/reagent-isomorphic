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

For now, you'll want to perform the following operations in two shells.

Shell #1

```
infra/lein cljsbuild auto dev node
```

Shell #2

```
infra/server.sh
```

The following URLs are enabled:

* [http://localhost:3000/home](http://localhost:3000/home)
* [http://localhost:3000/weather/london](http://localhost:3000/weather/london)

## Concepts

As much as possible, I've tried to minimize the need for ```serverSide?``` and ```clientSide?``` 
checks within user code.

Challenges encountered:

* Using single atom instance to represent state works client side, but doesn't work server side.  The
  global state atom needs to be instanced per request.
  
* Ideally, routes should only be defined once and work both server and client side.  Server side, because the
  state is instanced, there needs to be some way to pass the state along through the route.
  
* I hoped that cljs-ajax could be used both server and client side, but alas, without some additional 
  shimming this isn't the case.  Consequently, restler is used server side and cljs-ajax is used
  client side with a facade to hide the differences from the app.
  
* When a page is first rendered that calls an API, both the server side and client side will attempt to execute
  the query unless additional logic is added.
  
* More to come ...   
 
## Components

* express - web server (server-side)
* restler - rest client (server-side)
* cljs-ajax - rest client (client-side)
* st - file server (server-side)
* secretary - provides routing (server-side, client-side)
* pushy - handles push state (client-side)

## License

Copyright © 2015 Matt Ho

Distributed under the Eclipse Public License either version 1.0 or (at
your option) any later version.
