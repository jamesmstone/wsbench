lazy val root = (project in file("."))
  .settings(
    name         := "testkit",
    organization := "com.github.wsbench",
    version      := "0.0.1-SNAPSHOT",
    scalaVersion := "2.11.8",
    libraryDependencies += "io.netty" % "netty-all" % "4.1.8.Final"
  )

