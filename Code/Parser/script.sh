#!/bin/sh
echo "Initiating AARDroid. . ."

aar=$1
name=$2
output= "generated_apks/${name}.apk"
			
echo "Running AST parser. . ."    
java -jar Tool.jar $aar
cd androidapp
./gradlew clean
echo "Gradle clean done"
echo "Building dummy app"
./gradlew build
echo "Building dummy app successful"
cp app/build/outputs/apk/debug/app-debug.apk $output