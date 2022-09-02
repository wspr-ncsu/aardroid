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
class WipeMemory extends MASVSChecker{
  override def name: String = "Wipe Memory"


  // Check if local memory is wiped anywhere

  override def check(global: ApkGlobal, idfgOpt: Option[InterProceduralDataFlowGraph]): AnalysisResult = {

    val result: MMap[String, String] = mmapEmpty

    val code = global.getApplicationClassCodes

    code foreach { case (typ, file) =>

      var pkg = typ.getPackageName

      val clazz = global.getClassOrResolve(typ)
      if (!clazz.isSystemLibraryClass && clazz.isConcrete) {
        clazz.getDeclaredMethods foreach { method =>
          val code = method.getBody.toCode
          if (code.contains("Landroid/app/ActivityManager;.clearApplicationUserData:()Z") ){

            var results = "Class: "+ method.getDeclaringClass +"\n"
            result((results)) = "S5 maintained local storage wiping code present"
          }

        }
      }
    }
    if (result.size == 0){
      result("") = "S5 violated, local memory wiping code not found"
    }
    AnalysisResult(name, result.toMap)
  }




}
