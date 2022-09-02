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
class ScreenCaptureMisuse extends MASVSChecker{
  override def name: String = "Screen Capture Misuse"

  private final val FLAG_SECURE : String = "8192" //Constant value of Windows.LayoutParams.FLAG_SECURE




  override def check(global: ApkGlobal, idfgOpt: Option[InterProceduralDataFlowGraph]): AnalysisResult = {

    val result: MMap[String, String] = mmapEmpty

    val code = global.getApplicationClassCodes

    code foreach { case (typ, file) =>

      var pkg = typ.getPackageName

      val clazz = global.getClassOrResolve(typ)
      if (!clazz.isSystemLibraryClass && clazz.isConcrete) {
        clazz.getDeclaredMethods foreach { method =>
          val code = method.getBody.toCode
          if (code.contains("Landroid/view/Window;.setFlags:(II)V") ){
            val results = ParameterCheck(method)
            //val results = "dummy"
            if (results.length>0) result((results)) = "S2 maintained, Screen capture disabled"
          }

        }
      }
    }
    if (result.size == 0){
      result("") = "S2 violated, Screen capture not disabled"
    }
    AnalysisResult(name, result.toMap)
  }


  private def ParameterCheck (method: JawaMethod): String = {
    var result: String = ""
    method.getBody.resolvedBody.locations.foreach{ l =>
      l.statement match {
        case cs: CallStatement =>

          if (cs.signature.getSubSignature == "setFlags:(II)V" ) {

            val valuesForParam1 = ExplicitValueFinder.findExplicitLiteralForArgs(method, l, cs.arg(0))
            val valuesForParam2 = ExplicitValueFinder.findExplicitLiteralForArgs(method, l, cs.arg(1))
            val v1 = valuesForParam1.filter(_.isInt).map(_.getInt)
            val v2 = valuesForParam2.filter(_.isInt).map(_.getInt)
            if(FLAG_SECURE.equals(v1.head.toString) && FLAG_SECURE.equals(v2.head.toString)){
              result = result+"Class: "+ method.getDeclaringClass + " "+ l.locationUri.toString+"\n"
            }

          }
        case _ =>
      }
    }
    result
  }



}
