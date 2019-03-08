#!/bin/bash

MAIN="net.iubris.optimus_saint.model.saint.data._utils.SaintsDataBucket"
mvn exec:java -Dexec.mainClass=$MAIN -Dexec.args="-l"
