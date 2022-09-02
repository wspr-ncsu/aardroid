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
import org.argus.jawa.core.util._
import org.argus.jawa.flow.dfa.InterProceduralDataFlowGraph

/**
  * @project argus-saf
  * @author samin on 4/4/21
  */
class LoggingMisuse extends MASVSChecker{
  override def name: String = "LoggingMisuse"

  private final val logSinks: ISet[String] = Set(
    "Landroid/util/Log;.d:(Ljava/lang/String;Ljava/lang/String;)I",
    "Landroid/util/Log;.v:(Ljava/lang/String;Ljava/lang/String;)I",
    "Landroid/util/Log;.i:(Ljava/lang/String;Ljava/lang/String;)I",
    "Landroid/util/Log;.w:(Ljava/lang/String;Ljava/lang/String;)I",
    "Landroid/util/Log;.e:(Ljava/lang/String;Ljava/lang/String;)I",
    "Landroid/util/Log;.wtf:(Ljava/lang/String;Ljava/lang/String;)I"
  )



  override def check(global: ApkGlobal, idfgOpt: Option[InterProceduralDataFlowGraph]): AnalysisResult = {

    val result: MMap[String, String] = mmapEmpty
    val paths = global.apkTaintResult.get.getTaintedPaths
    paths.foreach{p =>

      val src = p.getSource.descriptor.desc
      val snk = p.getSink.descriptor.desc
      logSinks.foreach{s =>
        if (snk.contains(s)){

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

          result((p.toString)) = "DF4 violated. Logging of sensitive data found"+sensitivity

          print(result)
        }

      }

    }

    AnalysisResult(name, result.toMap)
  }

}
