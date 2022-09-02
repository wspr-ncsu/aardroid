/*
 * Copyright (c) 2021. Fengguo Wei and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Apache License v2.0
 * which accompanies this distribution, and is available at
 * https://www.apache.org/licenses/LICENSE-2.0
 *
 * Detailed contributors are listed in the CONTRIBUTOR.md
 */

package aardroid.plugins.manifest

import java.io.{File, FileInputStream}

import org.argus.amandroid.core.ApkGlobal
import org.argus.amandroid.core.parser.ManifestParser
import org.argus.amandroid.plugin.{AnalysisResult, MASVSChecker}
import org.argus.jawa.core.util.{FileUtil, ISet, MMap, mmapEmpty}
import org.argus.jawa.flow.dfa.InterProceduralDataFlowGraph
import org.argus.saf.cli.util.CliLogger




/**
  * @project argus-saf
  * @author samin on 10/14/21
  */
class ManifestAnalysis (manifestUri : String){

  private final val dangerousPermissions: ISet[String] = Set(

    "android.permission.READ_CALENDAR",
    "android.permission.WRITE_CALENDAR",
    "android.permission.READ_CALL_LOG",
    "android.permission.WRITE_CALL_LOG",
    "android.permission.PROCESS_OUTGOING_CALLS",
    "android.permission.CAMERA",
    "android.permission.READ_CONTACTS",
    "android.permission.WRITE_CONTACTS",
    "android.permission.GET_ACCOUNTS",
    "android.permission.ACCESS_FINE_LOCATION",
    "android.permission.ACCESS_COARSE_LOCATION",
    "android.permission.RECORD_AUDIO",
    "android.permission.READ_PHONE_STATE",
    "android.permission.READ_PHONE_NUMBERS",
    "android.permission.CALL_PHONE",
    "android.permission.ANSWER_PHONE_CALLS",
    "android.permission.ADD_VOICEMAIL",
    "android.permission.USE_SIP",
    "android.permission.BODY_SENSORS",
    "android.permission.SEND_SMS",
    "android.permission.RECEIVE_SMS",
    "android.permission.READ_SMS",
    "android.permission.RECEIVE_WAP_PUSH",
    "android.permission.RECEIVE_MMS",
    "android.permission.READ_EXTERNAL_STORAGE",
    "android.permission.WRITE_EXTERNAL_STORAGE"

  )

  private val name: String = "Android Manifest Analysis\n"
  val uri = FileUtil.toUri(manifestUri)
  val manifestIS = new FileInputStream(FileUtil.toFile(uri))
  private val mfp = new ManifestParser


  mfp.loadClassesFromTextManifest(manifestIS)
  manifestIS.close()


  def checkManifest(): AnalysisResult = {

    val result: MMap[String, String] = mmapEmpty

    //See if EXTERNAL STORAGE WRITE permission is on (MSTG DS2)

    val permissions = mfp.getPermissions
    var writePerm = false
    if(permissions.contains("android.permission.WRITE_EXTERNAL_STORAGE"))writePerm = true

    if(writePerm) result(("android.permission.WRITE_EXTERNAL_STORAGE")) = "M3 violated. Permission to write on external storage granted\n"
    else result(("No writing on external storage")) = "M3 maintained.. No such permission found\n"



    //See if componenets are exported (MSTG P4)

    val compInfos = mfp.getComponentInfos
    var exported = false
    compInfos.foreach{c =>


      if(c.exported && !c.compType.baseType.name.equalsIgnoreCase("MainActivity")){
        result(("Exported Component: "+c.compType)) = "M2 violated. Exported component found\n"
        exported = true
      }


    }
    if(!exported) result(("No exported component")) = "M2 maintained. No exported component found\n"


    //See if backup is turned on (MSTG DS8)

    val allowbackup = mfp.getAllowBackupFlag()

    if(allowbackup != "false" )result(("Allow Backup: "+allowbackup)) = "M1 violated. Auto backup not turned off explicitly\n"
    else result(("Allow Backup: "+allowbackup)) = "M1 maintained. Auto backup turned off\n"


    //Certificate pinning  (MSTG N4)
    val nsc = mfp.getNetworkSecurityConfig()
    if(nsc!= ""){

      val f = "temp/unzip/res/"+nsc.substring(1)+".xml"
      try{
        val lines = scala.io.Source.fromFile(f).mkString
        if(lines.contains("pin digest")){
          result((nsc)) = "M6 maintained. Certificate pinning found\n"
        }
        else{
          result(("No certificate pinned")) = "M6 violated. No pinned certificate found\n"
        }
      }

      catch {
        case e: Throwable =>
          result(("No certificate pinned")) = "M6 violated. No pinned certificate found\n"
      }

    }
    else{

      result(("No certificate pinned")) = "M6 violated. No pinned certificate found\n"
    }

    //See if dangerous permissions used (MSTG P1)

    var dangerousperm = ""
    permissions.foreach { x =>
      if(dangerousPermissions.contains(x)){
        dangerousperm = dangerousperm+x+"\n"
      }
    }
    if(dangerousperm.length<1) {
      result(("No dangerous perission used")) = "M4 maintained. No unnecessary permission asked.\n"
    }
    else {
      result(("Dangerous permission used")) = "M4 violated. List of dangerous permissions asked listed below:\n"+dangerousperm+"\n"
    }

    //See if custom URL scheme used (MSTG P3)

    val ifs = mfp.getIntentFilters()
    if(ifs==null)  result(("No Custom URL schemes used")) = "M5 maintained.\n"
    else{
      val d = ifs.getData.getSchemes
      if(d.size>0){
        result((d.toString())) = "M5 violated. Sensitive functionality exposed through Custom URL schemes\n"
      }
      else{
        result(("No Custom URL schemes used")) = "M5 maintained.\n"
      }

    }

    //Custom keyboard should not be used (MSTG P11)
    var customKeyboard = false
    if(permissions.contains("android.permission.BIND_INPUT_METHOD"))customKeyboard = true

    if(customKeyboard) result(("android.permission.BIND_INPUT_METHOD")) = "M7 violated. Custom keyboard used\n"
    else result(("Custom keyboard not used")) = "M7 maintained. No usage of custom keyboard found\n"



    AnalysisResult(name, result.toMap)
  }

}
