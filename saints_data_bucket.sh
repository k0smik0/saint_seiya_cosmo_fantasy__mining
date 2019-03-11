#!/bin/bash

DOWNLOAD=$1
#LOAD="-l"
MAIN="net.iubris.optimus_saint.crawler.main.Main"
mvn -q exec:java -Dexec.mainClass=$MAIN -Dexec.args="$DOWNLOAD $LOAD"
