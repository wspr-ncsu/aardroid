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

import aardroid.plugins.utils.NamespaceFilter
import org.argus.amandroid.core.ApkGlobal
import org.argus.amandroid.plugin.apiMisuse.CryptographicConstants
import org.argus.amandroid.plugin.{ApiMisuseResult, MASVSChecker, AnalysisResult}
import org.argus.jawa.core.ast.CallStatement
import org.argus.jawa.core.util._
import org.argus.jawa.core.{Global, JawaMethod}
import org.argus.jawa.flow.dfa.InterProceduralDataFlowGraph
import org.argus.jawa.flow.util.ExplicitValueFinder

/**
  * @project argus-saf
  * @author samin on 4/4/21
  */
class KeyStoreMisuse extends MASVSChecker{
  override def name: String = "KeyStore Misuse"

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

          if (code.contains("Ljava/security/KeyStore;.getInstance:(Ljava/lang/String;Ljava/lang/String;)Ljava/security/KeyStore;") ||
            code.contains("Ljava/security/KeyStore;.getInstance:(Ljava/lang/String;Ljava/security/Provider;)Ljava/security/KeyStore;")) {
            val results = ParameterCheckForKeyStore(method)
            //val results = "dummy"
            if (results.length>0) result((results)) = "S1 violated, Keystore initialized without Android KeyStore Instance"
            else result("") = "S1 maintained Keystore initialized with Android KeyStore Instance"
          }

          if (code.contains("Ljava/security/KeyPairGenerator;.getInstance:(Ljava/lang/String;Ljava/lang/String;)Ljava/security/KeyPairGenerator;") ||
            code.contains("Ljavax/crypto/KeyGenerator;.getInstance:(Ljava/lang/String;Ljava/lang/String;)Ljavax/crypto/KeyGenerator;")) {
            val results = ParameterCheckForKeyGenerator(method)
            //val results = "dummy"
            if (results.length>0) result((results)) = "S1 violated, Keystore initialized without Android KeyStore Instance"
            else result("") = "S1 maintained Keystore initialized with Android KeyStore Instance"
          }

          if (code.contains("Ljava/security/KeyStore;.getInstance:(Ljava/lang/String;)Ljava/security/KeyStore;") ||
            code.contains("Ljava/security/KeyPairGenerator;.getInstance:(Ljava/lang/String;)Ljava/security/KeyPairGenerator;") ){

            result(("Class: "+ method.getDeclaringClass+ "\n")) = "S1 violated, Keystore initialized without Android KeyStore Instance"

          }
        }
      }
    }

    AnalysisResult(name, result.toMap)
  }


  private def ParameterCheckForKeyStore (method: JawaMethod): String = {
    var result: String = ""
    method.getBody.resolvedBody.locations.foreach{ l =>
      l.statement match {
        case cs: CallStatement =>

          if ((cs.signature.getSubSignature == "getInstance:(Ljava/lang/String;)Ljava/security/KeyStore;") ||
            (cs.signature.getSubSignature == "getInstance:(Ljava/lang/String;Ljava/lang/String;)Ljava/security/KeyStore;") ||
            (cs.signature.getSubSignature == "getInstance:(Ljava/lang/String;Ljava/security/Provider;)Ljava/security/KeyStore;")) {

            val valuesForParam1 = ExplicitValueFinder.findExplicitLiteralForArgs(method, l, cs.arg(0))
            if(!androidKeyStore.equals(valuesForParam1)){
              result = result+"Class: "+ method.getDeclaringClass + " "+ l.locationUri.toString+"\n"
            }

          }
        case _ =>
      }
    }
    result
  }

  private def ParameterCheckForKeyGenerator(method: JawaMethod): String = {
    var result: String = ""
    method.getBody.resolvedBody.locations.foreach{ l =>
      l.statement match {
        case cs: CallStatement =>

          if ((cs.signature.getSubSignature == "getInstance:(Ljava/lang/String;Ljava/lang/String;)Ljava/security/KeyPairGenerator;") ||
            (cs.signature.getSubSignature == "getInstance:(Ljava/lang/String;Ljava/security/Provider;)Ljavax/crypto/KeyGenerator;")) {

            val valuesForParam1 = ExplicitValueFinder.findExplicitLiteralForArgs(method, l, cs.arg(1))
            if(!androidKeyStore.equals(valuesForParam1)){
              result = result+"Class: "+ method.getDeclaringClass + " "+ l.locationUri.toString+"\n"
            }

          }
        case _ =>
      }
    }
    result
  }

}
