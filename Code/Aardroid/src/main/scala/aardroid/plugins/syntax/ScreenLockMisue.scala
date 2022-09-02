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
class ScreenLockMisue extends MASVSChecker{
  override def name: String = "Screen Lock Misuse"

  private final val SCREEN_LOCK_ENABLED : String = "lock_pattern_autolock"


  // Check if device is protected with screenlock

  override def check(global: ApkGlobal, idfgOpt: Option[InterProceduralDataFlowGraph]): AnalysisResult = {

    val result: MMap[String, String] = mmapEmpty

    val code = global.getApplicationClassCodes


    //for newer versions
    code foreach { case (typ, file) =>

      var pkg = typ.getPackageName


      val clazz = global.getClassOrResolve(typ)
      if (!clazz.isSystemLibraryClass && clazz.isConcrete) {
        clazz.getDeclaredMethods foreach { method =>
          val code = method.getBody.toCode
          if (code.contains("Landroid/app/KeyguardManager;.isDeviceSecure:()Z") ){

            var results = "Class: "+ method.getDeclaringClass +"\n"
            result((results)) = "S3 maintained, checked if device is protected with screenlock"
          }

        }
      }
    }

    //for older versions
    code foreach { case (typ, file) =>

      var pkg = typ.getPackageName

      val clazz = global.getClassOrResolve(typ)
      if (!clazz.isSystemLibraryClass && clazz.isConcrete) {
        clazz.getDeclaredMethods foreach { method =>
          val code = method.getBody.toCode
          if (code.contains("Landroid/provider/Settings$Secure;.getInt:(Landroid/content/ContentResolver;Ljava/lang/String;)I") ){
            val results = ParameterCheck(method)
            //val results = "dummy"
            if (results.length>0) result((results)) = "S3 maintained, checked if device is protected with screenlock"
          }

        }
      }
    }


    if (result.size == 0){
      result("") = "S3 violated, not checked if device is not protected with screenlock"
    }
    AnalysisResult(name, result.toMap)
  }

  private def ParameterCheck (method: JawaMethod): String = {
    var result: String = ""
    method.getBody.resolvedBody.locations.foreach{ l =>
      l.statement match {
        case cs: CallStatement =>

          if (cs.signature.getSubSignature == "getInt:(Landroid/content/ContentResolver;Ljava/lang/String;)I" ) {

            val valuesForParam1 = ExplicitValueFinder.findExplicitLiteralForArgs(method, l, cs.arg(1))
            val v1 = valuesForParam1.filter(_.isString).map(_.getString)
            if(SCREEN_LOCK_ENABLED.equals(v1.head.toString)){
              result = result+"Class: "+ method.getDeclaringClass + " "+ l.locationUri.toString+"\n"
            }

          }
        case _ =>
      }
    }
    result
  }



}
