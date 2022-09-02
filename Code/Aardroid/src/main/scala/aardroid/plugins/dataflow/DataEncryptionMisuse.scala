/*
 * Copyright (c) 2021. Fengguo Wei and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Apache License v2.0
 * which accompanies this distribution, and is available at
 * https://www.apache.org/licenses/LICENSE-2.0
 *
 * Detailed contributors are listed in the CONTRIBUTOR.md
 */

package aardroid.plugins.dataflow

import aardroid.plugins.utils.ParameterSensitivityFinder
import org.argus.amandroid.core.ApkGlobal
import org.argus.amandroid.plugin.{AnalysisResult, MASVSChecker}
import org.argus.jawa.core.elements.Signature
import org.argus.jawa.core.util._
import org.argus.jawa.flow.dfa.InterProceduralDataFlowGraph
import org.argus.jawa.flow.taintAnalysis.TaintPath

/**
  * @project argus-saf
  * @author samin on 4/4/21
  */
class DataEncryptionMisuse extends MASVSChecker{
  override def name: String = "DataEncryptionMisuse"

  private final val dataPersistenceSinks: ISet[String] = Set(
    "Ljava/io/FileOutputStream;.write:([B)V",
    "Ljava/io/FileOutputStream;.write:([BII)V",
    "Ljava/io/FileOutputStream;.write:(I)V",
    "Ljava/io/FilterOutputStream;.write:([B)V",
    "Ljava/io/FilterOutputStream;.write:([BII)V",
    "Ljava/io/FilterOutputStream;.write:(I)V", //filteroS has bufferedOS which could be used to write files
    "Ljava/io/FileWriter;.write:([C)V",
    "Ljava/io/FileWriter;.write:([CII)V",
    "Ljava/io/FileWriter;.write:(I)V",
    "Ljava/io/FileWriter;.write:(Ljava/lang/String;)V",
    "Ljava/io/FileWriter;.write:(Ljava/lang/String;II)V",
    "Ljava/io/FilterWriter;.write:([C)V",
    "Ljava/io/FilterWriter;.write:([CII)V",
    "Ljava/io/FilterWriter;.write:(I)V",
    "Ljava/io/FilterWriter;.write:(Ljava/lang/String;)V",
    "Ljava/io/FilterWriter;.write:(Ljava/lang/String;II)V",
    "Landroid/content/SharedPreferences$Editor;.putBoolean:(Ljava/lang/String;Z)Landroid/content/SharedPreferences$Editor;",
    "Landroid/content/SharedPreferences$Editor;.putFloat:(Ljava/lang/String;F)Landroid/content/SharedPreferences$Editor;",
    "Landroid/content/SharedPreferences$Editor;.putInt:(Ljava/lang/String;I)Landroid/content/SharedPreferences$Editor;",
    "Landroid/content/SharedPreferences$Editor;.putLong:(Ljava/lang/String;J)Landroid/content/SharedPreferences$Editor;",
    "Landroid/content/SharedPreferences$Editor;.putString:(Ljava/lang/String;Ljava/lang/String;)Landroid/content/SharedPreferences$Editor;",
    "Landroid/content/SharedPreferences$Editor?;.putString:(Ljava/lang/String;Ljava/lang/String;)Landroid/content/SharedPreferences$Editor;",
    "Landroid/database/sqlite/SQLiteDatabase;.exeSQL:(Ljava/lang/String;)V",
    "Landroid/database/sqlite/SQLiteDatabase;.exeSQL:(Ljava/lang/String;[Ljava/lang/Object;)V"
  )


  private final val encryptionMethods: ISet[String] = Set(

    "doFinal",
    "digest",
    "update",
    "updateAAD",
    "encrypt",
    "encode"
  )

  def isObfuscated (tp: TaintPath): Boolean ={

    var sanitized = false
    for(em<-encryptionMethods){
      if(intermediateMethodExists(tp,em)){
        sanitized = true
      }
    }
    sanitized
  }

  def intermediateMethodExists (tp:TaintPath, signature: String): Boolean={

    for(n <- tp.getPath){
      try{
        val method = n.node.propertyMap.get("callee_sig").get.asInstanceOf[Signature].methodName
        if(method.toString().equalsIgnoreCase(signature)) {
          return true
        }
      }catch {
        case e:Exception=>{
          print("Exception in determining obfuscated path for "+tp)
        }
      }


    }
    return false
  }



  override def check(global: ApkGlobal, idfgOpt: Option[InterProceduralDataFlowGraph]): AnalysisResult = {

    val result: MMap[String, String] = mmapEmpty
    val paths = global.apkTaintResult.get.getTaintedPaths
    paths.foreach{p =>

      val src = p.getSource.descriptor.desc
      val snk = p.getSink.descriptor.desc
      dataPersistenceSinks.foreach{s =>
        if (snk.contains(s)){
          val doesEncrypt = isObfuscated(p)
          if (!doesEncrypt){

            var n = -1
            try{
              n = p.getSource.node.pos.get.pos
            }catch {
              case e: Throwable =>
                print("Param number not found")
            }
            var sensitivity = ""
            if(n == -1) sensitivity = " Sensitivity: LOW"
            else sensitivity = " Sensitivity: "+ParameterSensitivityFinder.detectSensitivity(src,n)

            result((p.toString)) = "DF2 violated. Persistence of sensitive data without encryption in local storage found"+sensitivity
          }
        }

      }

    }

    AnalysisResult(name, result.toMap)
  }

}
