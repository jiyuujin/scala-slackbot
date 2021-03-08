name := """scala-slackbot"""
organization := "nekohack"

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayScala)

scalaVersion := "2.13.3"

libraryDependencies ++= Seq(
  "org.scalatestplus.play" %% "scalatestplus-play" % "5.0.0" % Test,
  guice,
  ws
)

// Adds additional packages into Twirl
//TwirlKeys.templateImports += "nekohack.controllers._"

// Adds additional packages into conf/routes
// play.sbt.routes.RoutesKeys.routesImport += "nekohack.binders._"
