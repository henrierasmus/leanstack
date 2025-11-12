#!/bin/bash
rm -rf out
mkdir -p out/mods out/mlib

javac -d out/mods/git.logger $(find logger/src -name "*.java")

javac -d out/mods/git.api $(find api/src -name "*.java")

javac --module-path out/mods -d out/mods/git.fs $(find fs/src -name "*.java")

javac --module-path out/mods -d out/mods/git.cli $(find cli/src -name "*.java")

jar --create --file out/mlib/git.logger.jar -C out/mods/git.logger .
jar --create --file out/mlib/git.api.jar -C out/mods/git.api .
jar --create --file out/mlib/git.fs.jar -C out/mods/git.fs .
jar --create --file out/mlib/git.cli.jar -C out/mods/git.cli .

#java -cp out com.henrierasmus.leanstack.git.cli.Main init