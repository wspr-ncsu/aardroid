# AARDroid
[![License](https://img.shields.io/badge/License-Apache%202.0-blue.svg)](https://opensource.org/licenses/Apache-2.0)

This is official reporitory for [AARDroid](https://github.com/wspr-ncsu/aardroid).

AARDroid is a static analysis tool to identify non compliances with industry regulations of payment SDKs in Android. This work is heavily motivated by Mobile Application Security Verification Standard [(MASVS)](https://owasp.org/www-pdf-archive/OWASP_Mobile_AppSec_Verification_Standard_v0.9.2.pdf) and Payment Card Industry's Data Security Standard [(PCI-DSS)](https://docs-prv.pcisecuritystandards.org/PCI%20DSS/Standard/PCI-DSS-v4_0.pdf).

## Repository structure

```
aardroid/
+--AAR/Test/     The SDK files (.aar) should be put here.
+--Code/Aardroid   Core static analysis framework that is based upon Argus-SAF.
+--Code/Parser    AST parser for extracting sensitive API of the code.
+--SNAPSHOT-1.0   A wrapper for scripts and executable binaries of the underlying tools.

```

## Running AARDroid

Depend on Jawa
[![Maven Central](https://maven-badges.herokuapp.com/maven-central/com.github.arguslab/jawa_2.12/badge.svg)](https://maven-badges.herokuapp.com/maven-central/com.github.arguslab/jawa_2.12)
by editing
`build.sbt`:

```
libraryDependencies += "com.github.arguslab" %% "jawa" % VERSION
```

Depend on Amandroid
[![Maven Central](https://maven-badges.herokuapp.com/maven-central/com.github.arguslab/amandroid_2.12/badge.svg)](https://maven-badges.herokuapp.com/maven-central/com.github.arguslab/amandroid_2.12)
by editing
`build.sbt`:

```
libraryDependencies += "com.github.arguslab" %% "amandroid" % VERSION
```

> Note that: Depend on Amandroid will automatically add Jawa as dependency. If you use Maven or Gradle, you should translate it to corresponding format.

## Obtaining Argus-SAF CLI Tool

**Requirement: Java 10**

1. Click [![Download](https://api.bintray.com/packages/arguslab/maven/argus-saf/images/download.svg)](https://bintray.com/arguslab/maven/argus-saf/_latestVersion)
2. Download argus-saf_***-version-assembly.jar
3. Get usage by:
  
 ```
 $ java -jar argus-saf_***-version-assembly.jar
 ```

## Developing AARDroid

In order to take part in Argus-SAF development, you need to:

1. Install the following software:
    - IntelliJ IDEA 14 or higher with compatible version of Scala plugin

2. Fork this repository and clone it to your computer

  ```
  $ git clone https://github.com/arguslab/Argus-SAF.git
  ```

3. Open IntelliJ IDEA, select `File -> New -> Project from existing sources`
(if from initial window: `Import Project`), point to
the directory where Argus-SAF repository is and then import it as `SBT project`.

4. When importing is finished, go to Argus-SAF repo directory and run

  ```
  $ git checkout .idea
  ```

  in order to get artifacts and run configurations for IDEA project.

5. [Optional] To build Argus-SAF more smooth you should give 2GB of the heap size to the compiler process.
   - if you use Scala Compile Server (default):
   `Settings > Languages & Frameworks > Scala Compile Server > JVM maximum heap size`

   - if Scala Compile Server is disabled:
   `Settings > Build, Execution, Deployment > Compiler > Build process heap size`
   
6. Build Argus-SAF from command line: go to Argus-SAF repo directory and run

  ```
  $ tools/bin/sbt clean compile test
  ```

7. Generate fat jar: go to Argus-SAF repo directory and run
  ```
  $ tools/bin/sbt assembly
  ```
  

  
## Publication

Full information on how AARDroid works can be found on our academic paper that was accepted at Annual Computer Security Applications Conference (ACSAC) 2022.   

Samin Yaseer Mahmud, K. Virgil English, Seaver Thorne, William Enck, Adam Oest and Muhhamad Saad.  **Analysis of Payment Service Provider SDKs in Android.** In Proceedings of the 2022 Annual Computer Security Applications Conference (ACSAC) , December 2022, Austin, TX, USA. [\[PDF\]](https://saminmahmud.com/files/papers/acsac22-mahmud.pdf)

If you use AARDroid in your research, consider citing our work using this Bibtex entry:

```
@conference{aardroid-acsac22,
  title = {{Analysis of Payment Service Provider SDKs in Android}},
  author = {Mahmud, Samin Yaseer and English, K. Virgil and Thorne, Seaver and Enck, William and Oest, Adam and Saad, Muhammad},
  booktitle = {{Proceedings of the Annual Computer Security Applications Conference (ACSAC)}},
  year = {2022}
}
```

## License

Details about the license can be found on LICENSE.txt
