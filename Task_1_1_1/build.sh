#!/bin/bash

mkdir -p out
javac -d out (find /src/java -name "*.java")

mkdir -p doc
javadoc -d doc -sourcepath src/main/java -subpackages org.example

jar --create --file app.jar --main-class org.example.Main -C out .

java -jar app.jar
