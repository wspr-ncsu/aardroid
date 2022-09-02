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
  * @author samin on 1/9/22
  */
class WebViewMisuse extends MASVSChecker{

  override def name: String = "WebView Misuse"

  //Landroid/webkit/WebView;.addJavascriptInterface:(Ljava/lang/Object;Ljava/lang/String;)V
  //Landroid/webkit/WebView;.clearCache:(Z)V

  // Check if Webview is initialized

  override def check(global: ApkGlobal, idfgOpt: Option[InterProceduralDataFlowGraph]): AnalysisResult = {

    val result: MMap[String, String] = mmapEmpty

    var jsresult = ""

    var jsbresult = ""

    val code = global.getApplicationClassCodes

    code foreach { case (typ, file) =>

      var pkg = typ.getPackageName

      val clazz = global.getClassOrResolve(typ)
      if (!clazz.isSystemLibraryClass ) {
        clazz.getDeclaredMethods foreach { method =>
          val code = method.getBody.toCode
          if (code.contains("Landroid/webkit/WebSettings;.setJavaScriptEnabled:(Z)V") ){
            val results = parameterCheckJSEnabled(method)
            if (results.length>0) jsresult = "W1 violated, JS enabled in webview "+ results
          }

        }
      }
    }
    if (jsresult.size == 0){
      jsresult = "W1 maintained, JS not enabled in webview\n"
    }

    code foreach { case (typ, file) =>

      var pkg = typ.getPackageName

      val clazz = global.getClassOrResolve(typ)
      if (!clazz.isSystemLibraryClass ) {
        clazz.getDeclaredMethods foreach { method =>
          val code = method.getBody.toCode
          if (code.contains("Landroid/webkit/WebView;.addJavascriptInterface:(Ljava/lang/Object;Ljava/lang/String;)V") ){

            jsbresult = "W2 violated, JS bridge created in webview \n" + method.getDeclaringClass.toString
          }

        }
      }
    }
    if (jsbresult.size == 0){
      jsbresult = "W2 maintained, JS bridge not created in webview"
    }


    var cacheresult = ""
    var wbv = "WBV: "
    code foreach { case (typ, file) =>

      var pkg = typ.getPackageName
      var hasWebView = false



      val clazz = global.getClassOrResolve(typ)
      if (!clazz.isSystemLibraryClass ) {
        clazz.getDeclaredMethods foreach { method =>
          val code = method.getBody.toCode
          if (code.contains("Landroid/webkit/WebView;.<init>:(Landroid/content/Context;)V")| code.contains("Landroid/webkit/WebView;.addJavascriptInterface:(Ljava/lang/Object;Ljava/lang/String;)V") | (code.contains("Landroid/webkit/WebSettings;.setJavaScriptEnabled:(Z)V")) | code.contains("Landroid/webkit/WebView;.loadUrl:(Ljava/lang/String;)V") ){
            hasWebView = true
          }



        }
        if(hasWebView){
          var cleared_cache = false
          //default true, still
          var fileaccess = false
          var contentaccess = false
          //default false
          var fileAccessFromFileURL  = false
          var universalAccessFromFileUrl = false
          clazz.getDeclaredMethods foreach { method =>
            val code = method.getBody.toCode
            if (code.contains("Landroid/webkit/WebView;.clearCache:(Z)V") ){
              cleared_cache = parameterCheckBoolean(method)
            }

            if (code.contains("Landroid/webkit/WebSettings;.setAllowFileAccess:(Z)V") ){
              fileaccess = parameterCheckBoolean(method)
              wbv = wbv + clazz.getCanonicalName+ " file-access: "+fileaccess.toString + "\n"

            }

            if (code.contains("Landroid/webkit/WebSettings;.setAllowContentAccess:(Z)V") ){
              contentaccess = parameterCheckBoolean(method)
              wbv = wbv + clazz.getCanonicalName+ " content-access: "+contentaccess.toString + "\n"
            }

            if (code.contains("Landroid/webkit/WebSettings;.setAllowFileAccessFromFileURLs:(Z)V") ){
              fileAccessFromFileURL = parameterCheckBoolean(method)
              wbv = wbv + clazz.getCanonicalName+ "file-from-url: "+fileAccessFromFileURL.toString + "\n"
            }

            if (code.contains("Landroid/webkit/WebSettings;.setAllowUniversalAccessFromFileURLs:(Z)V") ){
              universalAccessFromFileUrl = parameterCheckBoolean(method)
              wbv = wbv + clazz.getCanonicalName+ " univ-from-url: "+universalAccessFromFileUrl.toString + "\n"
            }

          }
          if(cleared_cache)cacheresult = cacheresult+ "W3 maintained, webview cache cleared in "+ clazz.getCanonicalName+"\n"
          else cacheresult =  cacheresult + "W3 violated, webview cache not cleared in "+ clazz.getCanonicalName+"\n"

          if(fileaccess | contentaccess |fileAccessFromFileURL | universalAccessFromFileUrl)cacheresult = cacheresult+ "W4 violated, webview has  access to file/contents \n"+ clazz.getCanonicalName+"\n"
          else cacheresult =  cacheresult + "W4 maintained, webview does not have unnecessary protocol access "+ clazz.getCanonicalName+"\n"

        }

      }

    }
    if(cacheresult.length<1)cacheresult = "W1,W2,W3,W4 not applicable, webview not found\n"



    val finRes = jsresult + "\n" + jsbresult + "\n"  + cacheresult+wbv
    result("") = finRes

    AnalysisResult(name, result.toMap)
  }

  private def parameterCheckJSEnabled (method: JawaMethod): String = {
    var result: String = ""
    method.getBody.resolvedBody.locations.foreach{ l =>
      l.statement match {
        case cs: CallStatement =>

          if (cs.signature.getSubSignature == "setJavaScriptEnabled:(Z)V" ) {

            val valuesForParam1 = ExplicitValueFinder.findExplicitLiteralForArgs(method, l, cs.arg(0))
            val v1 = valuesForParam1.filter(_.isInt).map(_.getInt)
            if("1".equals(v1.head.toString)){
              result = result+"Class: "+ method.getDeclaringClass + " "+ l.locationUri.toString+"\n"
            }

          }
        case _ =>
      }
    }
    result
  }

  private def parameterCheckBoolean (method: JawaMethod): Boolean = {
    var result =  false
    method.getBody.resolvedBody.locations.foreach{ l =>
      l.statement match {
        case cs: CallStatement =>

          if (cs.signature.getSubSignature == "clearCache:(Z)V" | cs.signature.getSubSignature == "setAllowFileAccess:(Z)V" |  cs.signature.getSubSignature == "setAllowContentAccess:(Z)V" |  cs.signature.getSubSignature == "setAllowFileAccessFromFileURLs:(Z)V" |  cs.signature.getSubSignature == "setAllowUniversalAccessFromFileURLs:(Z)V" ) {

            val valuesForParam1 = ExplicitValueFinder.findExplicitLiteralForArgs(method, l, cs.arg(0))
            val v1 = valuesForParam1.filter(_.isInt).map(_.getInt)
            if("1".equals(v1.head.toString)){
              result = true
            }
            if("0".equals(v1.head.toString)){
              result = false
            }

          }
        case _ =>
      }
    }
    result
  }

}
