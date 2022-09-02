/*
 * Copyright (c) 2022. Fengguo Wei and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Apache License v2.0
 * which accompanies this distribution, and is available at
 * https://www.apache.org/licenses/LICENSE-2.0
 *
 * Detailed contributors are listed in the CONTRIBUTOR.md
 */

package aardroid.plugins.syntax

import org.argus.amandroid.core.ApkGlobal
import org.argus.amandroid.plugin.{AnalysisResult, MASVSChecker}
import org.argus.jawa.core.JawaMethod
import org.argus.jawa.core.ast.CallStatement
import org.argus.jawa.core.util.{MMap, mmapEmpty}
import org.argus.jawa.flow.dfa.InterProceduralDataFlowGraph
import org.argus.jawa.flow.util.ExplicitValueFinder

/**
  * @project argus-saf
  * @author samin on 1/18/22
  */

class CertificatePinningMisuse extends MASVSChecker{
  override def name: String = "Certificate Pinning Misuse"

  private final val androidKeyStore : String = "AndroidKeyStore"


  // Check if KeyStore is initialized without Android KeyStore instance

  override def check(global: ApkGlobal, idfgOpt: Option[InterProceduralDataFlowGraph]): AnalysisResult = {

    val result: MMap[String, String] = mmapEmpty
    val code = global.getApplicationClassCodes

    code foreach { case (typ, file) =>

      var pkg = typ.getPackageName

      val clazz = global.getClassOrResolve(typ)
      if (!clazz.isSystemLibraryClass && clazz.isConcrete) {
        clazz.getDeclaredMethods foreach { method =>
          val code = method.getBody.toCode

          if (code.contains("Ljavax/net/ssl/TrustManagerFactory;.init:(Ljava/security/KeyStore;)V") ) {
            result(("Class: " + method.getDeclaringClass + "\n")) = "S6 maintained, certificate pinned using TrustManager"
          }




        }
      }
    }

    AnalysisResult(name, result.toMap)
  }




}
