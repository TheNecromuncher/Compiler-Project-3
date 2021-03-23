#!/bin/bash
for item in inputs/*; do
    path=$(basename $item)
    file="${path%%.*}"
    RES="$(./Micro.sh inputs/$file.micro)"
    echo "Executing Test $file"
    diff -B -b -s <(java Driver < inputs/$file.micro) <(cat outputs/$file.out)
    echo "---------------------------------------------------"
done
