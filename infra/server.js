// ----------------------------------------------------------------------
// fake required browser elements

global.XMLHttpRequest = {
  prototype: {
    ajax$core$AjaxImpl$: false,
  }
};
global.React = require("./react.inc.js");
global.window = {
  attachEvent: function (eventName, callback) {
    return {
      pathname: "/"
    };
  },
  location: {
    pathname: "/"
  }
};
global.document = {
  attachEvent: function (eventName, callback) {
    return {
      pathname: "/"
    };
  },
  location: {
    pathname: "/"
  }
};

// HACK - really hate having to define this here but couldn't figure out
// a way to satisfy the cljs requires on both server and client side
//
global.restler = require("./node_modules/restler");
global.state = {};

// ----------------------------------------------------------------------
// load the reagent app

require('./goog/bootstrap/nodejs');
require('./app');

goog.require('isomorphic.server');
isomorphic.server.start();
