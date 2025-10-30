#!/bin/bash
rm -rf out
mkdir -p out

javac -d out $(find -name "*.java")

java -cp out com.henrierasmus.leanstack.git.cli.Main init