package wsbench

import sbt._,Keys._

object Build extends Build {

  override lazy val settings = super.settings ++ Seq(
    name := "wsbench-servlet",
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
      "javax.websocket" % "javax.websocket-api" % "1.1" % "provided",
      "javax.servlet" % "javax.servlet-api" % "3.1.0" % "provided"
    ),
    mainClass in (Compile, run) := Some("wsbench.Start"),
    javaOptions in run ++= Seq(
      "-server",
      "-Xms512m",
      "-Xmx512m"
    )
  )
}
