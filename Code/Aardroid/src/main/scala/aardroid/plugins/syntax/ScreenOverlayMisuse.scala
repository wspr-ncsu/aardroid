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
class ScreenOverlayMisuse extends MASVSChecker{
  override def name: String = "Screen Overlay Misuse"


  override def check(global: ApkGlobal, idfgOpt: Option[InterProceduralDataFlowGraph]): AnalysisResult = {

    val result: MMap[String, String] = mmapEmpty

    val code = global.getApplicationClassCodes

    code foreach { case (typ, file) =>

      var pkg = typ.getPackageName

      val clazz = global.getClassOrResolve(typ)
      if (!clazz.isSystemLibraryClass && clazz.isConcrete) {
        clazz.getDeclaredMethods foreach { method =>
          val code = method.getBody.toCode
          if (code.contains("Landroid/view/View;.onFilterTouchEventForSecurity:(Landroid/view/MotionEvent;)Z")||
            code.contains("Landroid/view/View;.setFilterTouchesWhenObscured:(Z)V") ){
            var results = "Class: "+ method.getDeclaringClass +"\n"
            result((results)) = "S7 maintained, checked if UI is protected from screen overlay"}

        }
      }
    }
    if (result.size == 0){
      result("") = "S7 violated, Screen overlay protection not provided"
    }
    AnalysisResult(name, result.toMap)
  }




}
