# AARDroid
[![License](https://img.shields.io/badge/License-Apache%202.0-blue.svg)](https://opensource.org/licenses/Apache-2.0)

This is official reporitory for the academic work on analysis of payment SDKs in Android, a.k.a [AARDroid](https://saminmahmud.com/files/papers/acsac22-mahmud.pdf).

AARDroid is a static analysis tool to identify non compliances with industry regulations of payment SDKs in Android. This work is heavily motivated by Mobile Application Security Verification Standard [(MASVS)](https://owasp.org/www-pdf-archive/OWASP_Mobile_AppSec_Verification_Standard_v0.9.2.pdf) and Payment Card Industry's Data Security Standard [(PCI-DSS)](https://docs-prv.pcisecuritystandards.org/PCI%20DSS/Standard/PCI-DSS-v4_0.pdf).

## Repository structure

```
aardroid/
+--AAR/Test/     The SDK files (.aar) should be put here.
+--Code/Aardroid   Core static analysis framework that is based upon Argus-SAF.
+--Code/Parser    AST parser for extracting sensitive API of the code.
+--SNAPSHOT-1.0   A wrapper for scripts and executable binaries of the underlying tools.

```

## Running AARDroid using the script

Using the pre built binaries and scripts you can easily run AARDroid from the commandline.

1. Make sure the requried SDK files are in AAR/Test folder

2. Go to SNAPSHOT-1.0 folder

```
cd SNAPSHOT-1.0
```

3. Make sure build files from previous run are deleted


4. Run the aardroid_runner.py script 

```
python3 aardroid_runner.py
```

5. The output should be generated in final_out.txt. 


## Interpreting the output file

final_out.txt contains the summary of different MSTG checks that are violated in different SDKs.
For different MSTG checks refer to OWASP's Mobile Security Testing Guide.

For the output: Y denotes the property is violated, N denotes the property is not violated, N/A denotes the property does not apply.

For example,
```
SDK, MSTG S1, MSTG S2
test_SDK, Y, N
```
Denotes, in test_SDK the MSTG S1 (Data Storage 1) property is violated but S2 is not.

A more detailed output for each SDK is available on output/<sdk_name>/AARDroid.txt file.


## Developing AARDroid

In order to take part in AARDroid development, you need to:

1. Install the following software:
    - IntelliJ IDEA 14 or higher with compatible version of Scala plugin

2. Fork this repository and clone it to your computer

  ```
  $ git clone https://github.com/wspr-ncsu/aardroid.git
  ```

3. Open IntelliJ IDEA, select `File -> New -> Project from existing sources`
(if from initial window: `Import Project`), point to
the directory where AARdroid repository is and then import it as `SBT project`.

4. When importing is finished, go to Code/Aardroid repo directory and run

  ```
  $ git checkout .idea
  ```

  in order to get artifacts and run configurations for IDEA project.

5. [Optional] To build AARDroid more smooth you should give 2GB of the heap size to the compiler process.
   - if you use Scala Compile Server (default):
   `Settings > Languages & Frameworks > Scala Compile Server > JVM maximum heap size`

   - if Scala Compile Server is disabled:
   `Settings > Build, Execution, Deployment > Compiler > Build process heap size`
   
6. Build AARDroid from command line: go to Aardroid repo directory and run

  ```
  $ tools/bin/sbt clean compile test
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
