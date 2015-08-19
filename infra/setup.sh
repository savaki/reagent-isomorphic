#!/bin/bash

set -eu

# location of the closure nodejs shim
#
CLOSURE_BOOTSTRAP='https://raw.githubusercontent.com/google/closure-library/master/closure/goog/bootstrap/nodejs.js'


# where should these packages be installed to
#
BASEDIR="$(dirname $0)/../target/node"
mkdir -p $(dirname ${BASEDIR})


# download the closure nodejs bootstrap
#
FILENAME="${BASEDIR}/goog/bootstrap/nodejs.js"
if [ ! -f ${FILENAME} ] ; then
  echo ""
  echo "downloading closure nodejs bootstrap"
  mkdir -p $(dirname ${FILENAME})
  (cd $(dirname ${FILENAME}); curl -s -o $(basename ${FILENAME}) ${CLOSURE_BOOTSTRAP})
fi

# install express and other node modules
#
./infra/lein npm install


# copy the server script file
#
echo ""
echo "copying the server files"
cp $(dirname $0)/server.js ${BASEDIR}

