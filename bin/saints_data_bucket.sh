#!/bin/bash

DOWNLOAD=$1
#LOAD="-l"

ARGS="$@"
MAIN="net.iubris.optimus_saint.crawler.main.Main"
mvn exec:java -Dexec.mainClass=$MAIN -Dexec.args="$ARGS"
