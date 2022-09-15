import os
import re
import json
import time


aar_directory = '../AAR/Test/'
parser = "tools/parser/parser.jar"
amandroid = "tools/amandroid/amandroid.jar"
cryptoguard = "tools/cryptoguard/cryptoguard.jar"
apk_directory ="app/build/outputs/apk/debug/app-debug.apk"
source_sink_directory = "temp/SS.txt"
library_directory = "app/libs/library.aar"
#timing_info = "SDK, preprocessing time, DF analysis time, Crypto analysis time, Elapsed time\n"
output_string_header = "SDK, DS1,DS2,DS3,DS4,DS5,DS6,DS7,DS8,DS9,DS10,DS11,DS12,CRYPTO1,CRYPTO2,CRYPTO3,CRYPTO4,TLS1,TLS2,TLS3,TLS4,PLAT1,PLAT2,PLAT3,PLAT4,PLAT5,PLAT6,PLAT8,PLAT7\n"
output_file = "final_out.txt"


def run(filename):

    sdk_name = filename[0:len(filename)-4]
    aar_path = aar_directory+filename


    if filename.endswith('.aar'):



        start_time = round(time.time(),2)

        print("Analyzing SDK: " + sdk_name)
        print("Phase 1: Beginning preprocessing step")
        os.system('java -jar ' + parser + ' ' + aar_path )
        print("Removing old library file. . .")
        os.system("rm "+library_directory)
        print("Copying new library file. . .")
        os.system("cp "+ aar_path+ " "+ library_directory)
        print("Phase 2: Generating APK for "+sdk_name)
        os.system('sh apk_builder.sh '+sdk_name)



        preprocessing_time_start = round(time.time(),2)
        preprocessing_time = round(preprocessing_time_start - start_time, 2)


        newapk = "app/build/outputs/apk/debug/"+sdk_name+".apk"
        os.system('mv '+apk_directory+ ' '+ newapk)
        print("Phase 3: Running dataflow analysis")
        os.system('java -jar '+amandroid+ ' t '+newapk+' -l '+source_sink_directory+ ' -a AUTOMATIC')

        

        dfanalysis_time_start = round(time.time(), 2)
        dfanalysis_time_ = round(dfanalysis_time_start - preprocessing_time_start, 2)
        

        print("Phase 4: Running Crypto/ TLS analysis")
        os.system("java -jar "+cryptoguard+" -in apk -s"+newapk)
        rename_cryptoguard_file(sdk_name)

        end_time = time.time()
        cryptoguard_time = round(end_time-dfanalysis_time_start,2)

        elapsed_time = round(end_time-start_time,2)
        timing_info = sdk_name+ "," + str(preprocessing_time) + "," +str(dfanalysis_time_ )+ "," + str(cryptoguard_time) + "," +str(elapsed_time)+"\n"

        #file1 = open("timing.txt", "a")  # append mode
        #file1.write(timing_info)
        #file1.close()
        
        
        #print("Writing report for "+sdk_name)
        o = generate_output(sdk_name)
        output_string = sdk_name+","+o+'\n'
        f = open(output_file, "a")
        f.write(output_string)
        f.close()


        



        

        


        #print("Analysis done")


def rename_cryptoguard_file(sdkname):
    for filename in os.listdir("./"):
        if filename.startswith('_CryptoGuard'):
            os.system("mv "+filename+ " output/"+sdkname+"/cryptoguard_"+sdkname+".json")


def parse_cryptoguard_output(sdk):

    fl = "output/" + sdk + "/cryptoguard_" + sdk + ".json"
    f = open(fl,)
    content = f.read()
    f.close()
    data = json.loads(content)
    s = set()
    for i in data['Issues']:
        rule =i['RuleNumber']
        fpath: str = i['_FullPath']
        if(not (fpath.startswith(sdk+".apk/androidx") | fpath.startswith(sdk+".apk/android"))):
            s.add(rule)
    return s

def generate_output(sdkname):
    try:
        cryptoguard_res = parse_cryptoguard_output(sdkname)
        amandroid_out: str = open("output/" + sdkname + "/AARDroid.txt").read()
        output = ""
        no_ui = False

        '''
        if (amandroid_out.__contains__("WBV:  \n")):
            print(sdkname+":nope \n")
        else:
            print(sdkname+":yes \n")

        '''
        #MSTG S1 Android Keystore not used
        if (amandroid_out.__contains__("S1 violated")):
            output += "Y,"
        else:
            output += "N,"

        #MSTG  S2 External storage used
        if (amandroid_out.__contains__("S4 violated") or amandroid_out.__contains__("M3 violated") ):
            output += "Y,"
        else:
            output += "N,"

        #MSTG S3 Logging
        if (amandroid_out.__contains__("DF4 violated")):
            sens = "("
            if (amandroid_out.__contains__("DF4 violated. Logging of sensitive data found Sensitivity: HIGH")):
                sens = sens + "H"
            if (amandroid_out.__contains__("DF4 violated. Logging of sensitive data found Sensitivity: MEDIUM")):
                sens = sens + "M"
            if(amandroid_out.__contains__("DF4 violated. Logging of sensitive data found Sensitivity: LOW")):
                sens = sens+"L"
            sens = sens+")"
            output += "Y"+sens+","
        else:
            output += "N,"

        #MSTG S4 Data shared with Third party
        if (amandroid_out.__contains__("DF5 violated")):
            sens = "("
            if (amandroid_out.__contains__("DF5 violated. Leakage of sensitive data in network sinks, determine if third party Sensitivity: HIGH")):
                sens = sens + "H"
            if (amandroid_out.__contains__("DF5 violated. Leakage of sensitive data in network sinks, determine if third party Sensitivity: MEDIUM")):
                sens = sens + "M"
            if (amandroid_out.__contains__("DF5 violated. Leakage of sensitive data in network sinks, determine if third party Sensitivity: LOW")):
                sens = sens + "L"
            sens = sens + ")"
            output += "Y" + sens + ","
        else:
            output += "N,"

        #MSTG S5 Keyboard Cache enabled
        if (amandroid_out.__contains__("L1 violated")):
            output += "Y,"
        elif( amandroid_out.__contains__("L1 maintained")):
            output += "N,"
        else:
            output += "N/A,"
            no_ui = True

        #MSTG S6 Data leakage through IPC
        if (amandroid_out.__contains__("DF6 violated")):
            sens = "("
            if (amandroid_out.__contains__("DF6 violated. Leakage of sensitive data through IPC Sensitivity: HIGH")):
                sens = sens + "H"
            if (amandroid_out.__contains__("DF6 violated. Leakage of sensitive data through IPC Sensitivity: MEDIUM")):
                sens = sens + "M"
            if (amandroid_out.__contains__("DF6 violated. Leakage of sensitive data through IPC Sensitivity: LOW")):
                sens = sens + "L"
            sens = sens + ")"
            output += "Y" + sens + ","
        else:
            output += "N,"

        #MSTG S7 Data leakage through UI
        if (amandroid_out.__contains__("L2 violated")):
            output += "Y,"
        elif (amandroid_out.__contains__("L2 maintained")):
            output += "N,"
        else:
            output += "N/A,"

        #MSTG S8 Autobackup
        if (amandroid_out.__contains__("M1 violated")):
            output += "Y,"
        else:
            output += "N,"

        #MSTG S9 Screenshot possible
        if(no_ui==True):
            output += "N/A,"
        elif (amandroid_out.__contains__("S2 violated")):
            output += "Y,"
        else:
            output += "N,"


        #MSTG S11 Screenlock presence test skipped
        if(no_ui==True):
            output += "N/A,"
        elif (amandroid_out.__contains__("S3 violated")):
            output += "Y,"
        else:
            output += "N,"

        #MSTG  S13 Data Persistence
        if (amandroid_out.__contains__("DF1 violated")):
            sens = "("
            if (amandroid_out.__contains__("DF1 violated. Persistence of sensitive data in local storage found Sensitivity: HIGH")):
                sens = sens + "H"
            if (amandroid_out.__contains__("DF1 violated. Persistence of sensitive data in local storage found Sensitivity: MEDIUM")):
                sens = sens + "M"
            if (amandroid_out.__contains__("DF1 violated. Persistence of sensitive data in local storage found Sensitivity: LOW")):
                sens = sens + "L"
            sens = sens + ")"
            output += "Y" + sens + ","
        else:
            output += "N,"

        #MSTG  S14  Unencrypted data persistence
        if (amandroid_out.__contains__("DF2 violated")):
            sens = "("
            if (amandroid_out.__contains__("DF2 violated. Persistence of sensitive data without encryption in local storage found Sensitivity: HIGH")):
                sens = sens + "H"
            if (amandroid_out.__contains__("DF2 violated. Persistence of sensitive data without encryption in local storage found Sensitivity: MEDIUM")):
                sens = sens + "M"
            if (amandroid_out.__contains__("DF2 violated. Persistence of sensitive data without encryption in local storage found Sensitivity: LOW")):
                sens = sens + "L"
            sens = sens + ")"
            output += "Y" + sens + ","
        else:
            output += "N,"


        #-----------------------------

        #MSTG  C1  Hardcoded keys
        if (3 in cryptoguard_res or  14 in cryptoguard_res):
            output += "Y,"
        else:
            output += "N,"

        #MSTG C3  Crypto with  Wrong config
        if (9 in cryptoguard_res or  10 in cryptoguard_res or  5 in cryptoguard_res or  8 in cryptoguard_res):
            output += "Y,"
        else:
            output += "N,"

        #MSTG C4 Deprecated Crypto
        if (0 in cryptoguard_res or  1 in cryptoguard_res or  2 in cryptoguard_res ):
            output += "Y,"
        else:
            output += "N,"

        #MSTG  C6 Broken PRNG
        if (11 in cryptoguard_res or  13 in cryptoguard_res ):
            output += "Y,"
        else:
            output += "N,"

        # -----------------------------

        #MSTG T1 HTTP?
        if (7 in cryptoguard_res ):
            output += "Y,"
        else:
            output += "N,"

        #MSTG T2 TLS broken
        if (4 in cryptoguard_res or  6 in cryptoguard_res or 12 in cryptoguard_res):
            output += "Y,"
        else:
            output += "N,"

        #MSTG T3 Untrusted CA
        if (4 in cryptoguard_res ):
            output += "Y,"
        else:
            output += "N,"

        #MSTG  T4  Certificate not pinned
        if (amandroid_out.__contains__("M6 violated") and amandroid_out.__contains__("S6 violated")):
            output += "Y,"
        else:
            output += "N,"

        # -----------------------------


        #MSTG P1  Dangerous permissions
        if(amandroid_out.__contains__("M4 violated")):
            output+="Y,"
        else:
            output += "N,"

        #MSTG P3 Export functionality via Custom URL scheme
        if (amandroid_out.__contains__("M5 violated")):
            output += "Y,"
        else:
            output += "N,"

        #MSTG P4 Export functionality via IPC
        if (amandroid_out.__contains__("M2 violated")):
            output += "Y,"
        else:
            output += "N,"

        #MSTG P5 JS enabled webview
        if (amandroid_out.__contains__("W1 violated")):
            output += "Y,"
        elif (amandroid_out.__contains__("W1 maintained")):
            output += "N,"
        else:
            output += "N/A,"

        #MSTG P6 Unnecessary protocol handler in Webview
        if (amandroid_out.__contains__("W4 violated")):
            output += "Y,"
        elif (amandroid_out.__contains__("W4 maintained")):
            output += "N,"
        else:
            output += "N/A,"

        #MSTG  P7 JS Bridge used in Webview
        if (amandroid_out.__contains__("W2 violated")):
            output += "Y,"
        elif (amandroid_out.__contains__("W2 maintained")):
            output += "N,"
        else:
            output += "N/A,"

        #MSTG P10 Webview Cache not cleard
        if (amandroid_out.__contains__("W3 violated")):
            output += "Y,"
        elif (amandroid_out.__contains__("W3 maintained")):
            output += "N,"
        else:
            output += "N/A,"

        # MSTG  P9 Screen overlay
        if(no_ui==True):
            output += "N/A,"
        elif (amandroid_out.__contains__("S7 violated")):
            output += "Y"
        else:
            output += "N"



        return output
    except:
        print("Error generating report for "+sdkname)



f = open(output_file, "a")
f.write(output_string_header)
f.close()
for filename in os.listdir(aar_directory):


    try:

       run(filename)


    except:
        print("Error generating "+filename)

    






