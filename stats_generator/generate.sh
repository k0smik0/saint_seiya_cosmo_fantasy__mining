#!/bin/bash

[ $# -lt 1 ] && exit 1

map_putters=()
while IFS='' read -r line || [[ -n "$line" ]]; do
   stat_name="$line"
   class_name=$(echo $line | sed 's/ /_/g' | sed 's/\.//g')
   map_putter=$(echo "numericalClassesMap.put(\"$stat_name\",${class_name}.class);")
   map_putters=(${map_putters[@]} $map_putter)
#   echo -e "package net.iubris.optimus_saint.model.saint.data;\n\npublic class $class_name extends NumericStat { }\n" > "${class_name}.java"
done < "numerical.txt"

for i in ${map_putters[@]}; do
   echo $i
done

