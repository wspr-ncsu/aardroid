/*
 * Copyright (c) 2021. Fengguo Wei and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Apache License v2.0
 * which accompanies this distribution, and is available at
 * https://www.apache.org/licenses/LICENSE-2.0
 *
 * Detailed contributors are listed in the CONTRIBUTOR.md
 */

package aardroid.plugins.layout

import java.io.{FileInputStream, IOException, InputStream}

import aardroid.plugins.utils.NamespaceFilter.ignorednamespaces
import javax.xml.parsers.{DocumentBuilderFactory, ParserConfigurationException}
import org.argus.amandroid.core.parser.LayoutControl
import org.argus.jawa.core.JawaClass
import org.argus.jawa.core.util._
import org.w3c.dom.{Node, NodeList}
import org.xml.sax.SAXException

/**
  * @project argus-saf
  * @author samin on 10/14/21
  */
class LayoutAnalysis (fileName: String){

  val file = FileUtil.toFile(fileName)
  val layout_in = new FileInputStream(file)

  private final val creditCardDataSynonym: ISet[String] = Set(
    "card_input","ccnumber","cc_number","cc number","cardnumber","card number","card_number","credit_card_number","creditcard", "credit card ","credit_card", "debit_card", "debit card", "debitcard", "cvc", "ccv", "card_verification_code" , "cvv code", "card ccv", "securecode", "cvv number", "ccv2", "cvv", "security code", "card security code"
  )


  private final val DEBUG = false

  def loadLayoutFromTextXml(): String = {

    var result = ""
    try {

      val db = DocumentBuilderFactory.newInstance().newDocumentBuilder()
      val doc = db.parse(layout_in)
      val rootElement = doc.getDocumentElement
      val worklistAlgorithm = new WorklistAlgorithm[NodeList] {
        override def processElement(cns: NodeList): Unit = {
          for(i <- 0 until cns.getLength) {
            val cn = cns.item(i)
            val nname = cn.getNodeName
            result = result + visitLayoutNode(fileName, cn)
            worklist = worklist :+ cn.getChildNodes
          }
        }
      }
      worklistAlgorithm.run(worklistAlgorithm.worklist :+= rootElement.getChildNodes)
    } catch {
      case ex: IOException =>
        System.err.println("Could not parse layout: " + ex.getMessage)
        if(DEBUG)
          ex.printStackTrace()
      case ex: ParserConfigurationException =>
        System.err.println("Could not parse layout: " + ex.getMessage)
        if(DEBUG)
          ex.printStackTrace()
      case ex: SAXException =>
        System.err.println("Could not parse layout: " + ex.getMessage)
        if(DEBUG)
          ex.printStackTrace()
    }

    result
  }

  private def visitLayoutNode(fileName: String, n: Node): String = {

    var result = ""
    var text = ""
    var inputId = ""
    var hint = ""
    var inputType = ""
    var widget = n.getNodeName

    try {

      var isEditText = false

      if( widget=="EditText" || widget.toLowerCase.contains("edittext")) isEditText = true

      val attr = n.getAttributes
      if(attr.getNamedItem("android:inputType")!=null) isEditText = true


      if( isEditText ){

        val attrs = n.getAttributes
        if(attrs.getNamedItem("android:inputType")!=null)inputType = attrs.getNamedItem("android:inputType").getNodeValue
        if(attrs.getNamedItem("android:id")!=null)inputId = attrs.getNamedItem("android:id").getNodeValue
        if(attrs.getNamedItem("android:text")!=null)text = attrs.getNamedItem("android:text").getNodeValue
        if(attrs.getNamedItem("android:hint")!=null)hint = attrs.getNamedItem("android:hint").getNodeValue

        if(contain_cc_text(inputId.toLowerCase()) || contain_cc_text(hint.toLowerCase) || contain_cc_text(text.toLowerCase)){

          //Sensitive data exposed through keyboard cache (MSTG DS5)
          if(!inputType.contains("textNoSuggestions") &&(!(inputType.contains("number"))|| (inputType.contains("phone")))){
            result = result+"L1 violated. "+fileName+" Edittext provides text suggestion\n"
          }

          //Sensitive data exposed through UI (MSTG DS7)
          if(!inputType.contains("textPassword") && !inputType.contains("numberPassword")) {
            result = result+"L2 violated. "+fileName+" Edittext displays sensitive data\n"
          }
        }
      }



    } catch {
      case _: Exception =>
    }

    result


  }

  def contain_cc_text( nm : String ) : Boolean = {

    creditCardDataSynonym.foreach(x => {

      if(nm.toLowerCase.contains(x)) return true

    })

    return false
  }


}
