#!/bin/bash

# allow users to specify location of node or nodemon
#
BIN=${NODE:=node}

# where is this script located; absolute path
#
SCRIPTPATH="$( cd "$(dirname "$0")" ; pwd -P )"

# define the docroot
#
export DOCROOT="${SCRIPTPATH}/../target/dev"

(cd $(dirname $0)/../target/node; ${BIN} server.js)
