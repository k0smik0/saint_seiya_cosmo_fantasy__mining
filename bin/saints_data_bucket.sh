#!/bin/bash

#DOWNLOAD=$1
#LOAD="-l"

MAIN="net.iubris.optimus_saint.crawler.main.Main"

if [ $# -lt 1 ];
then
    mvn -q exec:java -Dexec.mainClass=$MAIN -Dexec.args="-h"
    exit
fi


ARGS=("$@")

MAVEN_QUIET="-q"
if [ "${ARGS[0]}" == "-_e" ]; then
   MAVEN_QUIET="-e"
   ARGS=("${ARGS[@]:1}")
fi
mvn $MAVEN_QUIET exec:java -Dexec.mainClass=$MAIN -Dexec.args="${ARGS[*]}"
