#!/bin/bash

var=$1
echo "plugins {
    id 'com.android.application'
}

android {
    compileSdkVersion 30
    buildToolsVersion \"29.0.3\"

    packagingOptions {
        exclude 'AndroidManifest.xml'
    }

    defaultConfig {
        applicationId \"com.example.app\"
        minSdkVersion 21
        targetSdkVersion 30
        versionCode 1
        versionName \"1.0\"

        testInstrumentationRunner \"androidx.test.runner.AndroidJUnitRunner\"
    }

    buildTypes {
        release {
            minifyEnabled false
            proguardFiles getDefaultProguardFile('proguard-android-optimize.txt'), 'proguard-rules.pro'
        }
    }
    compileOptions {
        sourceCompatibility JavaVersion.VERSION_1_8
        targetCompatibility JavaVersion.VERSION_1_8
    }
}

dependencies {

    implementation 'com.google.android.material:material:1.3.0'
    implementation(name:'$var', ext:'aar')
    testImplementation 'junit:junit:4.12'
    androidTestImplementation 'androidx.test.ext:junit:1.1.2'
    androidTestImplementation 'androidx.test.espresso:espresso-core:3.3.0'

}" > androidapp/app/build.gradle