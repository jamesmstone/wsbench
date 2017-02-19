lazy val root = (project in file("."))
  .settings(
    name         := "wsbench",
    organization := "com.github.wsbench",
    version      := "0.0.1-SNAPSHOT",
    scalaVersion := "2.11.8",
    libraryDependencies += "com.tumblr" % "colossus_2.11" % "0.8.2"
  ).enablePlugins(JavaAppPackaging, UniversalPlugin)

