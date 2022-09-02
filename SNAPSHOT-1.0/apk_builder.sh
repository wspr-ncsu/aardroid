#!/bin/sh
echo "Initiating AARDroid. . ."

#aar=$1
name=$1
output="generated_apks/${name}.apk"
ss="generated_ss/${name}.txt"
sen="generated_sensitivity/${name}.txt"
mf="sdk_manifests/${name}.xml"
ly="sdk_layouts/${name}/"
			
#echo "Running AST parser. . ."    
#java -jar tools/parser/parser.jar $aar

./gradlew clean
echo "Gradle clean done"
echo "Building dummy app"
./gradlew build
echo "Building dummy app successful"
cp app/build/outputs/apk/debug/app-debug.apk $output
cp temp/SS.txt $ss
cp temp/Sensitivity.txt $sen
cp temp/unzip/AndroidManifest.xml $mf
cp -R temp/unzip/res/layout $ly