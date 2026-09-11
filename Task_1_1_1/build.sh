#!/bin/bash
set -e

mkdir -p out
javac -encoding UTF-8 -d out $(find src/main/java -name "*.java")

mkdir -p doc
javadoc -encoding UTF-8 -d doc -sourcepath src/main/java -subpackages org.example

jar --create --file app.jar --main-class org.example.Main -C out .

java -jar app.jar
