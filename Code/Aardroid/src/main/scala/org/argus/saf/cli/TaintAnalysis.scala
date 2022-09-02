/*
 * Copyright (c) 2018. Fengguo Wei and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Apache License v2.0
 * which accompanies this distribution, and is available at
 * https://www.apache.org/licenses/LICENSE-2.0
 *
 * Detailed contributors are listed in the CONTRIBUTOR.md
 */

package org.argus.saf.cli

import java.io.{File, FileInputStream, PrintWriter}

import aardroid.plugins.dataflow._
import aardroid.plugins.layout.LayoutAnalysis
import aardroid.plugins.manifest.ManifestAnalysis
import aardroid.plugins.syntax._
import org.argus.amandroid.alir.dataRecorder.DataCollector
import org.argus.amandroid.alir.dataRecorder.DataCollector.template
import org.argus.saf.cli.util.CliLogger
import org.argus.amandroid.core.util.ApkFileUtil
import org.argus.amandroid.core.ApkGlobal
import org.argus.amandroid.core.appInfo.AppInfoCollector
import org.argus.amandroid.core.parser.ManifestParser
import org.argus.amandroid.plugin._
import org.argus.jawa.core.io.{FileReporter, MsgLevel, PrintReporter}
import org.argus.jawa.core.util.{FileResourceUri, IgnoreException, _}
import org.argus.jawa.flow.taintAnalysis.TaintAnalysisResult
import java.nio.file.{Files, Paths}

/**
 * @author <a href="mailto:fgwei521@gmail.com">Fengguo Wei</a>
 */ 
object TaintAnalysis{
//  private final val TITLE = "TaintAnalysis"
  def apply(module: TaintAnalysisModules.Value, debug: Boolean, sourcePath: String, outputPath: String, forceDelete: Boolean, guessPackage: Boolean, approach: TaintAnalysisApproach.Value,  interfacingApproach: InterfaceIntegrationApproach.Value, libraryInterfaceFile: String = ""): Unit = {
    val apkFileUris: MSet[FileResourceUri] = msetEmpty
    val fileOrDir = new File(sourcePath)
    fileOrDir match {
      case dir if dir.isDirectory =>
        apkFileUris ++= ApkFileUtil.getApks(FileUtil.toUri(dir))
      case file =>
        if(ApkGlobal.isValidApk(FileUtil.toUri(file)))
          apkFileUris += FileUtil.toUri(file)
        else println(file + " is not decompilable.")
    }
    taintAnalyze(module, apkFileUris.toSet, outputPath, debug, forceDelete, guessPackage, approach, interfacingApproach, libraryInterfaceFile)
  }
  
  def taintAnalyze(module: TaintAnalysisModules.Value, apkFileUris: ISet[FileResourceUri], outputPath: String, debug: Boolean, forceDelete: Boolean, guessPackage: Boolean, approach: TaintAnalysisApproach.Value, interfacingApproach: InterfaceIntegrationApproach.Value, libraryInterfaceFile: String = ""): Unit = {
    println("Total apks: " + apkFileUris.size)
    val outputUri = FileUtil.toUri(outputPath)
    try{
      var i: Int = 0
      apkFileUris.foreach{ fileUri =>
        i += 1
        try{
          println("Analyzing #" + i + ":" + fileUri)
          val reporter =
            if(debug) new FileReporter(getOutputDirUri(outputUri, fileUri), MsgLevel.INFO)
            else new PrintReporter(MsgLevel.ERROR)
          var res = TaintAnalysisTask(module, Set((fileUri, outputUri)), forceDelete, reporter, guessPackage, approach, interfacingApproach, libraryInterfaceFile).run

          var result : String = ""

          //Performing MASVS Checks here

          try{

            try{
              //Data persistence checker (MSTG DS13)
              val dpchecker : MASVSChecker = new DataPersistenceMisuse
              val dp = dpchecker.check(res.head,None)
              result = result + dp.toString
            }
            catch {
              case e: Throwable =>
                CliLogger.logError(new File(outputPath), "Error: " , e)
            }

            try{
              //Data encryption checker (MSTG DS14)
              val dechecker : MASVSChecker = new DataEncryptionMisuse
              val de = dechecker.check(res.head,None)
              result = result + de.toString
            }
            catch {
              case e: Throwable =>
                CliLogger.logError(new File(outputPath), "Error: " , e)
            }

            try{

              //Input validation checker (MSTG P2)
              val ivchecker : MASVSChecker = new InputValidationMisuse
              val iv = ivchecker.check(res.head,None)
              result = result + iv.toString
            }
            catch {
              case e: Throwable =>
                CliLogger.logError(new File(outputPath), "Error: " , e)
            }

            try{

              //TP API Misuse checker (MSTG DS4)
              val tpchecker : MASVSChecker = new ThirdPartyAPIMisuse
              val tp = tpchecker.check(res.head,None)
              result = result + tp.toString

            }
            catch {
              case e: Throwable =>
                CliLogger.logError(new File(outputPath), "Error: " , e)
            }

            try{

              //IPC leakage checker (MSTG DS6)
              val ipcchecker : MASVSChecker = new IPCMisuse
              val ipc = ipcchecker.check(res.head,None)
              result = result + ipc.toString
            }
            catch {
              case e: Throwable =>
                CliLogger.logError(new File(outputPath), "Error: " , e)
            }

            try{

              //Keystore checker (MSTG DS1)
              val kchecker : MASVSChecker = new KeyStoreMisuse
              val ke = kchecker.check(res.head,None)
              result = result + ke.toString

            }
            catch {
              case e: Throwable =>
                CliLogger.logError(new File(outputPath), "Error: " , e)
            }

            try{

              //Screen capture checker (MSTG DS9)
              val scchecker : MASVSChecker = new ScreenCaptureMisuse
              val sc = scchecker.check(res.head,None)
              result = result + sc.toString


            }
            catch {
              case e: Throwable =>
                CliLogger.logError(new File(outputPath), "Error: " , e)
            }

            try{

              //Certificate Pinning Checker (MSTG N4)
              val cpchecker : MASVSChecker = new CertificatePinningMisuse
              val cp = cpchecker.check(res.head,None)
              if(cp.misusedApis.size==0) result = result + "S6 violated. No pinned certificate found\n"
              else result = result + cp.toString


            }
            catch {
              case e: Throwable =>
                CliLogger.logError(new File(outputPath), "Error: " , e)
            }

            try{

              //Screen lock presence checker (MSTG DS11)
              val slchecker : MASVSChecker = new ScreenLockMisue
              val sl = slchecker.check(res.head,None)
              result = result + sl.toString

            }
            catch {
              case e: Throwable =>
                CliLogger.logError(new File(outputPath), "Error: " , e)
            }

            try{

              //External Write checker (MSTG DS2)
              val exchecker : MASVSChecker = new ExternalStorageMisuse
              val ex = exchecker.check(res.head,None)
              result = result + ex.toString
            }
            catch {
              case e: Throwable =>
                CliLogger.logError(new File(outputPath), "Error: " , e)
            }

            try{

              //Memory wipe checker (MSTG DS15)
              val wchecker : MASVSChecker = new WipeMemory
              val w = wchecker.check(res.head,None)
              result = result + w.toString
            }
            catch {
              case e: Throwable =>
                CliLogger.logError(new File(outputPath), "Error: " , e)
            }


            try{

              //Screen Overlay Attack (MSTG Plat9)
              val ovhecker : MASVSChecker = new ScreenOverlayMisuse
              val ov = ovhecker.check(res.head,None)
              result = result + ov.toString
            }
            catch {
              case e: Throwable =>
                CliLogger.logError(new File(outputPath), "Error: " , e)
            }


            try{

              //Analyze manifest analysis
              val fnames = fileUri.split("/").last
              val fname = fnames.substring(0, fnames.length-4)
              //val mfchecker = new ManifestAnalysis("output/"+fname+"/AndroidManifest.xml")
              val mfchecker = new ManifestAnalysis("temp/unzip/AndroidManifest.xml")
              //val mfchecker = new ManifestAnalysis("AndroidManifest.xml")
              val mf = mfchecker.checkManifest()
              result = result + mf.toString

            }
            catch {
              case e: Throwable =>
                CliLogger.logError(new File(outputPath), "Error: " , e)
            }

            try{

              //Layout analysis
              val lay_directory = "temp/unzip/res/layout/"

              val d = new File(lay_directory)
              if (d.exists && d.isDirectory) {
                var lysum = ""
                d.listFiles.filter(_.isFile).toList.filter { file =>
                  val fname = file.getName
                  if(fname.endsWith(".xml")){
                    val uri = FileUtil.toUri(file.getAbsolutePath)
                    val lychecker = new LayoutAnalysis(uri)
                    var ly = lychecker.loadLayoutFromTextXml()
                    lysum = lysum + ly
                  }
                  true
                }
                if(lysum.length<1) result  = result + "L1 maintained. No sensitive text input widget providing suggestion.\nL2 maintained. No text input widget leaking credit card number or CVC\n"
                else{
                  var L1violated = true
                  var L2violated = true
                  if(!lysum.contains("L1 violated")) L1violated = false
                  if(!lysum.contains("L2 violated")) L2violated = false

                  if(L1violated && L2violated)result =  result + lysum
                  else if (!L1violated)result =  result + lysum+ "L1 maintained. No sensitive text input widget providing suggestion.\n"
                  else if (!L2violated)result =  result + lysum+ "L2 maintained. No text input widget leaking credit card number or CVC.\n"
                  else result  = result + "L1 maintained. No sensitive text input widget providing suggestion.\nL2 maintained. No text input widget leaking credit card number or CVC\n"

                }
              }
              else{
                result  = result + "L1,L2 not applicable. No layout files found"
              }

            }
            catch {
              case e: Throwable =>
                CliLogger.logError(new File(outputPath), "Error: " , e)
            }


            try{
              //Webview checker
              val wvchecker : MASVSChecker = new WebViewMisuse
              val wv = wvchecker.check(res.head,None)
              result = result + wv.toString
            }
            catch {
              case e: Throwable =>
                CliLogger.logError(new File(outputPath), "Error: " , e)
            }

            try{
              //Log checker (MSTG DS3)
              val lchecker : MASVSChecker = new LoggingMisuse
              val r = lchecker.check(res.head,None)
              result = result + r.toString
            }
            catch {
              case e: Throwable =>
                CliLogger.logError(new File(outputPath), "Error: " , e)
            }







          }
          catch {
            case e: Throwable =>
              CliLogger.logError(new File(outputPath), "Error: " , e)
          }



          writeAARDroidResult(result,getOutputDirUri(outputUri, fileUri))

          println("Done!")
          if(debug) println("Debug info write into " + reporter.asInstanceOf[FileReporter].f)
        } catch {
          case _: IgnoreException => println("No interesting element found for " + module)
          case e: Throwable =>
            CliLogger.logError(new File(outputPath), "Error: " , e)
        } finally {
          System.gc()
        }
      }
    } catch {
      case e: Throwable => 
        CliLogger.logError(new File(outputPath), "Error: " , e)
    }
  }
  
  private def getOutputDirUri(outputUri: FileResourceUri, apkUri: FileResourceUri): FileResourceUri = {
    outputUri + {if(!outputUri.endsWith("/")) "/" else ""} + apkUri.substring(apkUri.lastIndexOf("/") + 1, apkUri.lastIndexOf("."))
  }

  private def writeAARDroidResult(result: String, outputDirUri:FileResourceUri): Unit = {

    val out = new PrintWriter(FileUtil.toFile(FileUtil.appendFileName(outputDirUri, "AARDroid.txt")))
    out.print(result)
    out.close()

  }
}
