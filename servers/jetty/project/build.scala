package wsbench

import sbt._,Keys._

object Build extends Build {

  override lazy val settings = super.settings ++ Seq(
    name := "wsbench-jetty",
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
      "org.eclipse.jetty" % "jetty-server" % "9.3.8.v20160314",
      "org.eclipse.jetty.websocket" % "websocket-server" % "9.3.8.v20160314"
    ),
    mainClass in (Compile, run) := Some("wsbench.Start"),
    javaOptions in run ++= Seq(
      "-server",
      "-Xms512m",
      "-Xmx512m"
    )
  )
}
