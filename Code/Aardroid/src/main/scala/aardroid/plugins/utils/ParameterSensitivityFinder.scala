/*
 * Copyright (c) 2022. Fengguo Wei and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Apache License v2.0
 * which accompanies this distribution, and is available at
 * https://www.apache.org/licenses/LICENSE-2.0
 *
 * Detailed contributors are listed in the CONTRIBUTOR.md
 */

package aardroid.plugins.utils


import java.io.File

import org.argus.jawa.core.util.ISet
import org.argus.saf.cli.util.CliLogger

import scala.io.Source


/**
  * @project argus-saf
  * @author samin on 1/18/22
  */
object ParameterSensitivityFinder {

  private final val sdk_sensitivity_file = "temp/Sensitivity.txt"

  private final val sensitivity_high : ISet[String] = Set(

    "bankaccountnumber",
    "bankroutingnumber",
    "biometricinformation",
    "credential",
    "creditcardsecuritycode",
    "driverlicensenumber",
    "financialaccountinformation",
    "government-issueidentificationinformation",
    "password",
    "paymentcardinformation",
    "paymentcardnumber",
    "securitycode",
    "socialmediaaccountcredential",
    "ssn",
    "tin"

  )

  private final val sensitivity_medium : ISet[String] = Set(

    "accountbalanceinformation",
    "accountinformation",
    "androidid",
    "contactinformation",
    "credithistory",
    "creditinformation",
    "dateofbirth",
    "deviceidentifier",
    "emailaddress",
    "expirationdate",
    "financialtransactioninformation",
    "identifier",
    "imei",
    "insurancepolicynumber",
    "ipaddress",
    "key",
    "healthinformation",
    "loginformation",
    "macaddress",
    "medicalinsuranceinformation",
    "paymentcardexpirationdate",
    "paymentinformation",
    "phonenumber",
    "postaladdress",
    "sensitivepersonalinformation",
    "serialnumber",
    "transactioninformation",
    "token",
    "vehicleidentificationnumber",
    "vehiclelicensenumber",
    "zipcode"

  )

  private final val sensitivity_low : ISet[String] = Set(

    "accelerometerinformation",
    "activityinformation",
    "addressbook",
    "advertisingidentifier",
    "advertisingnetwork",
    "analyticprovider",
    "anyone",
    "applicationinformation",
    "artistname",
    "bicyclerentalinformation",
    "biography",
    "bloodglucoselevel",
    "bodymassindex",
    "bodyweight",
    "browserhistory",
    "browserinformation",
    "browsertype",
    "browsingbehavior",
    "businessinformation",
    "businessname",
    "calllog",
    "city",
    "clickstreaminformation",
    "configuration",
    "contactname",
    "content",
    "country",
    "demographicinformation",
    "deviceinformation",
    "devicesetting",
    "devicetype",
    "driverbehavior",
    "driverlicenseinformation",
    "educationalinformation",
    "educationinformation",
    "educationlevel",
    "employername",
    "employmentinformation",
    "firebase",
    "fitnessactivityinformation",
    "gameplayinformation",
    "gender",
    "geneticinformation",
    "geocachinglog",
    "geographicallocation",
    "googleanalytic",
    "governmententity",
    "gps",
    "gsfid",
    "hash",
    "heading",
    "heartrate",
    "information",
    "informationaboutyou",
    "installationinformation",
    "insurancecarrier",
    "insurancecoverageinformation",
    "insurancehistory",
    "interactioninformation",
    "internetserviceprovider",
    "isp",
    "language",
    "lifestyleinformation",
    "linkclick",
    "localizationinformation",
    "membershipinformation",
    "networkinformation",
    "non-pii",
    "operatingsystem",
    "operatingsystemversion",
    "pagevisit",
    "paymentmethod",
    "paymentprocessor",
    "personage",
    "personname",
    "philosophicalbelief",
    "phonetower",
    "photo",
    "photometadata",
    "physicaltraits",
    "pii",
    "politicalview",
    "preference",
    "prescriptioninformation",
    "profileinformation",
    "purchaseamount",
    "purchasedate",
    "purchaseinformation",
    "race",
    "registrationinformation",
    "relationshipstatus",
    "routerinformation",
    "routername",
    "salary",
    "searchengine",
    "servicetype",
    "sexualhistory",
    "sexualorientation",
    "shippinginformation",
    "shoppinghabits",
    "socialmediaaccountinformation",
    "socialmediafriends",
    "socialmediainformation",
    "socialmediaprofileurl",
    "socialmediausername",
    "socialnetwork",
    "software",
    "state",
    "textmessage",
    "thirdparty",
    "thirdpartypaymentprocessor",
    "trafficinformation",
    "translator",
    "travelinformation",
    "unclassifiedinfo",
    "url",
    "usageinformation",
    "userinformation",
    "username",
    "vehicleinformation",
    "vehicleusageinformation",
    "website",
    "websiteaddress",
    "yahooanalytic"

  )

  def detectSensitivity( param : String , pos: Int) : String = {

    val filename = sdk_sensitivity_file
    var result = "LOW"

    try{


      val source = Source.fromFile(filename)
      for (line <- source.getLines()) {

        if(line.startsWith(param)){
          val strs: Array[java.lang.String] = line.split(",")
          for( a <- 1 to strs.length-1){
            val params :Array[java.lang.String] = strs(a).split(":")
            if(params(0).equals(pos.toString)){
              if(sensitivity_high.contains(params(1))){
                result = "HIGH("+params(1)+")"
              }
              else if(sensitivity_medium.contains(params(1))){
                result = "MEDIUM("+params(1)+")"
              }
              else if(sensitivity_low.contains(params(1))){
                result = "LOW("+params(1)+")"
              }
            }
          }
        }

      }
      source.close()

    }
    catch {
      case e: Throwable =>
        print(e)
        result
    }

    result
  }


}
