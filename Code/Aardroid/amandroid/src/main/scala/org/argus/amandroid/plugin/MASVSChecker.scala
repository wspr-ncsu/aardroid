package org.argus.amandroid.plugin

import org.argus.amandroid.core.ApkGlobal
import org.argus.jawa.flow.dfa.InterProceduralDataFlowGraph
import org.argus.jawa.core.util.IMap


object ApiMisuseModules extends Enumeration {
  val LOGGING_MISUSE, DATA_PERSISTENCE_MISUSE = Value
}

trait MASVSChecker {
  def name: String
  def check(global: ApkGlobal, idfgOpt: Option[InterProceduralDataFlowGraph]): AnalysisResult
}

case class AnalysisResult(checkerName: String, misusedApis: IMap[String, String]) {
  override def toString: String = {
    val sb = new StringBuilder
    sb.append(checkerName + ":\n")
    if(misusedApis.isEmpty) sb.append("  No issue found.\n")
    misusedApis.foreach {
      case (path, des) => sb.append(des + " \n " + path + "\n\n")
    }
    sb.toString().intern()
  }
}
