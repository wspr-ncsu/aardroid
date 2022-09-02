#!/bin/sh
echo "Initiating AARDroid. . ."

aar=$1
			
    

echo "Changing directory to androidapp"
cd androidapp
echo "Directory changed to androidapp. Cleaning prior builds. . ."
./gradlew clean
rm androidapp/app/libs/library.aar
echo "Cleaning older builds complete. Now copying .aar files to androidapp/app/libs/ directory"
cd ../
cp $aar ./androidapp/app/libs/library.aar
echo "Building dummy app"
cd androidapp
./gradlew build
cd ..
echo "Building dummy app successful"