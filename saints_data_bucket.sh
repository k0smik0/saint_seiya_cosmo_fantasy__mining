#!/bin/bash

MAIN="net.iubris.optimus_saint.crawler.main.Main"
mvn exec:java -Dexec.mainClass=$MAIN -Dexec.args="-l"
