#!/bin/bash

DOWNLOAD=$1
#LOAD="-l"

ARGS=("$@")
MAIN="net.iubris.optimus_saint.crawler.main.Main"

MAVEN_QUIET="-q"
if [ "${ARGS[0]}" == "-_e" ]; then
   MAVEN_QUIET="-e"
   ARGS=("${ARGS[@]:1}") 
fi
mvn $MAVEN_QUIET exec:java -Dexec.mainClass=$MAIN -Dexec.args="${ARGS[*]}"
