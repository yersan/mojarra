#!/bin/bash

if [ "$#" -eq 0 ]; then
    declare -a arr=("unit" "agnostic" "servlet30" "servlet31" "servlet40" "javaee6web" "javaee6" "javaee7" "javaee8")
else
    declare -a arr=("$@")
fi

for i in "${arr[@]}"
do

   cd $i

   ../bin/test-glassfish-default.sh

   cd ..

   if [ "$?" -ne "0" ]; then
     exit $?
   fi
done





