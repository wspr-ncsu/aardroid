/*
 * Copyright (c) 2021. Fengguo Wei and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Apache License v2.0
 * which accompanies this distribution, and is available at
 * https://www.apache.org/licenses/LICENSE-2.0
 *
 * Detailed contributors are listed in the CONTRIBUTOR.md
 */

package aardroid

import java.io.PrintWriter

import org.apache.commons.cli.{CommandLine, CommandLineParser, DefaultParser, HelpFormatter, Option, OptionGroup, Options, ParseException}
import org.argus.amandroid.alir.componentSummary.ApkYard
import org.argus.amandroid.alir.pta.model.AndroidModelCallHandler
import org.argus.amandroid.alir.pta.reachingFactsAnalysis.{AndroidReachingFactsAnalysis, AndroidReachingFactsAnalysisConfig}
import org.argus.amandroid.alir.pta.summaryBasedAnalysis.AndroidSummaryProvider
import org.argus.amandroid.alir.taintAnalysis.{AndroidDataDependentTaintAnalysis, DataLeakageAndroidSourceAndSinkManager}
import org.argus.amandroid.core.AndroidGlobalConfig
import org.argus.amandroid.core.decompile.{DecompileLayout, DecompileStrategy, DecompilerSettings}
import org.argus.amandroid.plugin.aardroid.LoggingMisuse
import org.argus.amandroid.plugin.{ApiMisuseResult, InterfaceIntegrationApproach, TaintAnalysisApproach, TaintAnalysisModules}
import org.argus.jawa.core.elements.JawaType
import org.argus.jawa.core.io.{MsgLevel, PrintReporter}
import org.argus.jawa.core.util.FileUtil
import org.argus.jawa.core.{ClassLoadManager, JawaClass, JawaField, JawaMethod}
import org.argus.jawa.flow.cfg.{ICFGNode, InterProceduralControlFlowGraph}
import org.argus.jawa.flow.dda.InterProceduralDataDependenceAnalysis
import org.argus.jawa.flow.pta.PTAResult
import org.argus.jawa.flow.{Context, JawaAlirInfoProvider}
import org.argus.saf.Main.Mode.Value
import org.argus.saf.Main.{apiMisuseOptions, apkSubmitterOptions, commandLine, decompilerOptions, normalOptions, taintOptions, _}
import org.argus.saf.cli.TaintAnalysis

import scala.sys.process._

/**
  * @project argus-saf
  * @author samin on 4/4/21
  */
object Runner extends App{

  private val generalOptionGroup: OptionGroup = new OptionGroup
  private val taintOptions: Options = new Options
  private val allOptions: Options = new Options

  private def createOptions(): Unit = {
    // create options

    val outputOption: Option = Option.builder("o").longOpt("output").desc("Set output directory. [Default: .]").hasArg(true).argName("dir").build()
    val approachOption: Option = Option.builder("a").longOpt("approach").desc("Set interface integration approach. [Default: Automated]").hasArg(true).argName("approach").build()
    val libraryInterfaceOption: Option = Option.builder("l").
      longOpt("lib").desc("Set library interface file directory. [Default: .]").
      hasArg(true).argName("lib").build()
    val taintmoduleOption: Option = Option.builder("mo")
      .longOpt("module").desc("Taint analysis module to use. [Default: DATA_LEAKAGE, Choices: (COMMUNICATION_LEAKAGE, OAUTH_TOKEN_TRACKING, PASSWORD_TRACKING, INTENT_INJECTION, DATA_LEAKAGE)]")
      .hasArg(true).argName("name").build()

    generalOptionGroup.addOption(outputOption)
    generalOptionGroup.addOption(approachOption)
    generalOptionGroup.addOption(libraryInterfaceOption)
    taintOptions.addOptionGroup(generalOptionGroup)
    taintOptions.addOption(taintmoduleOption)
    allOptions.addOption(outputOption)
    allOptions.addOption(approachOption)
    allOptions.addOption(libraryInterfaceOption)
    allOptions.addOption(taintmoduleOption)

  }

  private def usage(mode: Mode.Value): Unit ={
    val formatter: HelpFormatter = new HelpFormatter
    formatter.setWidth(120)
    mode match {
      case Mode.DEFAULT =>
        println(s"""AAARdroid is a static analysis framework for analyzing .aar files""".stripMargin)
        println("")
        println("""Usage: sh aardroid_runner.sh <.aar file directory> <library interface file directory> <approach = a/m>""".stripMargin)
        println("")
      case Mode.TAINT =>
        println("AAR file or Library Interface file path is invalid")
        println("Usage: sh aardroid_runner.sh <.aar file directory> <library interface file directory>")

    }

  }

  object Mode extends Enumeration {
    val DEFAULT, TAINT = Value
  }

  //this is the starting point

  // create the command line parser
  val parser: CommandLineParser = new DefaultParser()
  var commandLine: CommandLine = _

  createOptions()

  try {
    // parse the command line arguments
    commandLine = parser.parse(allOptions, args)
    //println(commandLine)
  }
  catch {
    case exp: ParseException =>
      println("ParseException:" + exp.getMessage)
      exp.printStackTrace()
      usage(Mode.DEFAULT)
      System.exit(1)
  }

  var cmdFound: Boolean = false

  try {

    for (opt <- commandLine.getArgs) {

      if (opt.equalsIgnoreCase("t") || opt.equalsIgnoreCase("taint")) {
        cmdTaintAnalysis(commandLine)
        cmdFound = true
      }
    }
  } catch {
    case exp: Exception =>
      println("Unexpected exception:" + exp.getMessage)
      exp.printStackTrace()
  } finally {
    // if no commands ran, run the version / usage check.
    if (!cmdFound) {

      usage(Mode.DEFAULT)

    }
  }

  case class ArgNotEnoughException(msg: String) extends Exception(msg)

  private def cmdTaintAnalysis(cli: CommandLine): Unit = {
    var debug = true
    var guessPackage = false
    var outputPath: String = "./output"
    var libraryInterfacePath: String = ""
    var forceDelete: Boolean = true
    var module: TaintAnalysisModules.Value = TaintAnalysisModules.AARDROID
    var approach: TaintAnalysisApproach.Value = TaintAnalysisApproach.COMPONENT_BASED
    var interfacingApproach : InterfaceIntegrationApproach.Value = InterfaceIntegrationApproach.AUTOMATIC
    var sourcePath: String = null

    if(cli.hasOption("a") || cli.hasOption("approach")) {
      interfacingApproach = cli.getOptionValue("a") match {
        case "AUTOMATIC" => InterfaceIntegrationApproach.AUTOMATIC
        case "MANUAL" => InterfaceIntegrationApproach.MANUAL

      }
    }

    try {
      libraryInterfacePath = cli.getOptionValue("l")
      if (!scala.reflect.io.File(libraryInterfacePath).exists){
        usage(Mode.TAINT)
        System.exit(0)
      }
    } catch {
      case _: Exception =>
        usage(Mode.TAINT)
        System.exit(0)
    }

    try {
      sourcePath = cli.getArgList.get(1)
    } catch {
      case _: Exception =>
        usage(Mode.TAINT)
        System.exit(0)
    }
    TaintAnalysis(module, debug, sourcePath, outputPath, forceDelete, guessPackage, approach, interfacingApproach, libraryInterfacePath)
  }


  /*
  //val fileUri = FileUtil.toUri(args(0))
  //val outputUri = FileUtil.toUri(args(1))
  val fileUri = "file:/Users/samin/Documents/Work/Amandroid/Aardroid/Aardroid/androidapp/app/build/outputs/apk/debug/app-debug.apk"
  val outputUri = "file:/Users/samin/Documents/Work/Amandroid/Aardroid/Aardroid/output2"
  val reporter = new PrintReporter(MsgLevel.ERROR)
  // Yard is the apks manager
  val yard = new ApkYard(reporter)
  val layout = DecompileLayout(outputUri)
  val strategy = DecompileStrategy(layout)
  val settings = DecompilerSettings(debugMode = false, forceDelete = true, strategy, reporter)
  // apk is the apk meta data manager, class loader and class manager
  val apk = yard.loadApk(fileUri, settings, collectInfo = true, resolveCallBack = true)

  //retriving unimportant stuffs
  val appName = apk.model.getAppName
  val certificate = apk.model.getCertificates
  val uses_permissions = apk.model.getUsesPermissions
  val component_infos = apk.model.getComponentInfos // ComponentInfo(compType: [class type], typ: [ACTIVITY, SERVICE, RECEIVER, PROVIDER], exported: Boolean, enabled: Boolean, permission: ISet[String])
  val intent_filter = apk.model.getIntentFilterDB // IntentFilterDB contains intent filter information for each component.
  val environment_map = apk.model.getEnvMap // environment method map

  //API Misuse checkers
  val checker = new LoggingMisuse
  val misuseReports: ApiMisuseResult = checker.check(apk, None)


  /*

  //Test: generate intaprocedural CFG for a method, need to pass class name and method name. Does not work if method name is ambigious

  val clazz: JawaClass = apk.getClassOrResolve(new JawaType("com.ccpp.pgw.sdk.android.securepay.SecurePaySDK"))
  val method_opt: Option[JawaMethod] = clazz.getDeclaredMethodByName("encrypt")
  val field_opt: Option[JawaField] = clazz.getDeclaredField("n")

  val method: JawaMethod = clazz.getDeclaredMethodByName("encrypt").get
  val cfg = JawaAlirInfoProvider.getCfg(method)
  val rda = JawaAlirInfoProvider.getRda(method, cfg)

  //print graphs
  val p = new PrintWriter("dfg.dot")
  cfg.toDot(p)

  //Test: Taint analysis. Does not work. Need to pass valid component method in android reaching fact analysis
  //AndroidReachingFactsAnalysisConfig.resolve_icc = false
  AndroidReachingFactsAnalysisConfig.resolve_static_init = false
  AndroidReachingFactsAnalysisConfig.parallel = false

  //implicit val factory = new RFAFactFactory
  // ep is the entry point method for the analsis. Most of the time it is the environment method we generated for each component.
  val icfg = new InterProceduralControlFlowGraph[ICFGNode]
  val ptaresult = new PTAResult
  val sp = new AndroidSummaryProvider(apk)
  val initialfacts = AndroidReachingFactsAnalysisConfig.getInitialFactsForMainEnvironment(method)
  val analysis = new AndroidReachingFactsAnalysis(
    apk, icfg, ptaresult, new AndroidModelCallHandler, sp.getSummaryManager, new ClassLoadManager,
    AndroidReachingFactsAnalysisConfig.resolve_static_init,
    timeout = None)
  val idfg = analysis.build(method, initialfacts, new Context(apk.nameUri))
  val iddResult = InterProceduralDataDependenceAnalysis(apk, idfg)
  val ssm = new DataLeakageAndroidSourceAndSinkManager(AndroidGlobalConfig.settings.sas_file)
  val taint_analysis_result = AndroidDataDependentTaintAnalysis(yard, iddResult, idfg.ptaresult, ssm)

  */
  * */
}
