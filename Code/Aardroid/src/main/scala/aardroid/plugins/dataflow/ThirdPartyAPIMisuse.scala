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

import java.io.File

import aardroid.plugins.syntax.WebViewMisuse
import aardroid.plugins.utils.ParameterSensitivityFinder
import org.argus.amandroid.core.ApkGlobal
import org.argus.amandroid.plugin.{AnalysisResult, MASVSChecker}
import org.argus.jawa.core.util._
import org.argus.jawa.flow.dfa.InterProceduralDataFlowGraph
import org.argus.saf.cli.util.CliLogger

/**
  * @project argus-saf
  * @author samin on 4/4/21
  */
class ThirdPartyAPIMisuse extends MASVSChecker{
  override def name: String = "ThirdPartyAPIMisuse"

  private final val networksinks: ISet[String] = Set(

    "Ljava/io/OutputStream;.write:([B)V",
    "Ljava/io/FilterOutputStream;.write:([B)V",
    "Ljava/io/FilterOutputStream;.write:([BII)V",
    "Ljava/io/FilterOutputStream;.write:(I)V", //filteroS has bufferedOS which could be used to write files
    "Ljava/io/FilterWriter;.write:([C)V",
    "Ljava/io/FilterWriter;.write:([CII)V",
    "Ljava/io/FilterWriter;.write:(I)V",
    "Ljava/io/FilterWriter;.write:(Ljava/lang/String;)V",
    "Ljava/io/FilterWriter;.write:(Ljava/lang/String;II)V"
  )



  override def check(global: ApkGlobal, idfgOpt: Option[InterProceduralDataFlowGraph]): AnalysisResult = {

    val result: MMap[String, String] = mmapEmpty

    try{

      val paths = global.apkTaintResult.get.getTaintedPaths
      paths.foreach{p =>

        val src = p.getSource.descriptor.desc
        val snk = p.getSink.descriptor.desc

        val sink = p.getSink.node.node.getClass

        networksinks.foreach{s =>
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

            result((p.toString)) = "DF5 violated. Leakage of sensitive data in network sinks, determine if third party"+sensitivity
            print(result)
          }

        }

      }

    }
    catch {
      case e: Throwable =>
        print( "Error: " , e)
    }


    AnalysisResult(name, result.toMap)
  }

}
