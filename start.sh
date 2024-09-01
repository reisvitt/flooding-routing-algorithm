#!/bin/bash

find ./ -type f -name "*.class" -exec rm -f {} +

find ./ -name "*.java" > sources.txt
javac @sources.txt

rm sources.txt

java Principal || find ./ -type f -name "*.class" -exec rm -f {} +