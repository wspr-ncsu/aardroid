/*
 * Copyright (c) 2021. Fengguo Wei and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Apache License v2.0
 * which accompanies this distribution, and is available at
 * https://www.apache.org/licenses/LICENSE-2.0
 *
 * Detailed contributors are listed in the CONTRIBUTOR.md
 */

package aardroid.plugins.utils

import org.argus.jawa.core.util.ISet

/**
  * @project argus-saf
  * @author samin on 4/11/21
  */
object NamespaceFilter {



  private final val ignorednamespaces: ISet[String] = Set(

    "Lcom/google/android",
    "Landroid/",
    "Landroidx/",
    "Ljunit/",
    "Ljava/",
    "Ljavax/"

  )

  def filterNamespace( nm : String ) : Boolean = {

    ignorednamespaces.foreach(x => {

      if(nm.startsWith(x)) return true

    })

    return false
  }

}
