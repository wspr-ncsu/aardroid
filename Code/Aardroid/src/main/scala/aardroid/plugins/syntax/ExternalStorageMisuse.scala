/*
 * Copyright (c) 2021. Fengguo Wei and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Apache License v2.0
 * which accompanies this distribution, and is available at
 * https://www.apache.org/licenses/LICENSE-2.0
 *
 * Detailed contributors are listed in the CONTRIBUTOR.md
 */

package aardroid.plugins.syntax

import org.argus.amandroid.core.ApkGlobal
import org.argus.amandroid.plugin.{MASVSChecker, AnalysisResult}
import org.argus.jawa.core.JawaMethod
import org.argus.jawa.core.ast.CallStatement
import org.argus.jawa.core.util._
import org.argus.jawa.flow.dfa.InterProceduralDataFlowGraph
import org.argus.jawa.flow.util.ExplicitValueFinder

/**
  * @project argus-saf
  * @author samin on 4/4/21
  */
class ExternalStorageMisuse extends MASVSChecker{
  override def name: String = "External Storage Misuse"

  private final val MODE_WORLD_READABLE  = "1"
  private final val MODE_WORLD_WRITABLE  = "2"


  // Check if dara is read or written to external storage

  override def check(global: ApkGlobal, idfgOpt: Option[InterProceduralDataFlowGraph]): AnalysisResult = {

    val result: MMap[String, String] = mmapEmpty
    val code = global.getApplicationClassCodes

    //for newer versions check if getExternalStorageDirectory or getExternalFilesDir is accessed
    code foreach { case (typ, file) =>

      var pkg = typ.getPackageName


      val clazz = global.getClassOrResolve(typ)
      if (!clazz.isSystemLibraryClass && clazz.isConcrete) {
        clazz.getDeclaredMethods foreach { method =>
          val code = method.getBody.toCode
          if (code.contains("getExternalFilesDir:(Ljava/lang/String;)Ljava/io/File;") || code.contains("getExternalStorageDirectory:()Ljava/io/File;") ){

            var results = "Class: "+ method.getDeclaringClass +"\n"
            result((results)) = "S4 violated, call to getExternalStorageDirectory/ getExternalFilesDirectory found for attempting to read/write data from external storage "
          }

        }
      }
    }


    //for older versions check if WORLD READABLE/WRITABLE flags are used
    code foreach { case (typ, file) =>

      var pkg = typ.getPackageName


      val clazz = global.getClassOrResolve(typ)
      if (!clazz.isSystemLibraryClass && clazz.isConcrete) {
        clazz.getDeclaredMethods foreach { method =>
          val code = method.getBody.toCode
          if (code.contains("openFileOutput:(Ljava/lang/String;I)Ljava/io/FileOutputStream;") ){

            val results = ParameterCheck(method)
            //val results = "dummy"
            if (results.length>0) result((results)) = "S4 violated, file opened with MODE_WORLD_READABLE/WRITABLE flag for attempting to read/write data from external storage "
          }

        }
      }
    }


    if (result.size == 0) {
      result("") = "S4 maintained, data is not accessed to/from external storage"
    }
    AnalysisResult(name, result.toMap)
  }


  private def ParameterCheck (method: JawaMethod): String = {
    var result: String = ""
    method.getBody.resolvedBody.locations.foreach{ l =>
      l.statement match {
        case cs: CallStatement =>

          //openFileOutput:(Ljava/lang/String;I)Ljava/io/FileOutputStream;
          val sig = cs.signature.getSubSignature
          if (cs.signature.getSubSignature == "openFileOutput:(Ljava/lang/String;I)Ljava/io/FileOutputStream;" ) {

            val valuesForParam1 = ExplicitValueFinder.findExplicitLiteralForArgs(method, l, cs.arg(1))
            val v1 = valuesForParam1.filter(_.isInt).map(_.getInt)
            if(MODE_WORLD_WRITABLE.equals(v1.head.toString) || MODE_WORLD_READABLE.equals(v1.head.toString)){
              result = result+"Class: "+ method.getDeclaringClass + " "+ l.locationUri.toString+"\n"
            }

          }
        case _ =>
      }
    }
    result
  }

}
