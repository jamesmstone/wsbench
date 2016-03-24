package wsbench

import sbt._,Keys._

object Build extends Build {

  override lazy val settings = super.settings ++ Seq(
    name := "wsbench-sockoweb",
    organization := "com.github.wsbench",
    version := "0.0.1-SNAPSHOT",
    scalaVersion := "2.11.8",
    scalacOptions ++= Seq(
      "-feature",
      "-unchecked",
      "-deprecation"
    )
  )

  lazy val root = Project(id="root",base=file(".")).settings(
    libraryDependencies ++= Seq(
      "org.mashupbots.socko" %% "socko-webserver" % "0.6.0"
    ),
    mainClass in (Compile, run) := Some("wsbench.Start"),
    javaOptions in run ++= Seq(
      "-server",
      "-Xms512m",
      "-Xmx512m"
    )
  )
}
