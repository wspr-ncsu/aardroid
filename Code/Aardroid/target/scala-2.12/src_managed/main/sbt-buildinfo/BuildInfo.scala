package org.argus

import scala.Predef._

/** This object was generated by sbt-buildinfo. */
case object BuildInfo {
  /** The value is "argus-saf". */
  val name: String = "argus-saf"
  /** The value is "3.2.1-SNAPSHOT". */
  val version: String = "3.2.1-SNAPSHOT"
  /** The value is "2.12.7". */
  val scalaVersion: String = "2.12.7"
  /** The value is "1.2.4". */
  val sbtVersion: String = "1.2.4"
  override val toString: String = {
    "name: %s, version: %s, scalaVersion: %s, sbtVersion: %s" format (
      name, version, scalaVersion, sbtVersion
    )
  }
}
