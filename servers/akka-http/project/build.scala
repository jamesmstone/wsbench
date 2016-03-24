package wsbench

import sbt._,Keys._

object Build extends Build {

	override lazy val settings = super.settings ++ Seq(
    name := "wsbench-akka-http",
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
    	"com.typesafe.akka" %% "akka-http-core" % "2.4.2"
    ),
    mainClass in (Compile, run) := Some(""),
    javaOptions in run ++= Seq(
      "-server",
      "-Xms512m",
      "-Xmx512m"
    )
  )
}
