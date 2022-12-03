
ThisBuild / version := "0.1.0-SNAPSHOT"
ThisBuild / scalaVersion := "2.13.0"

//// 依赖
//libraryDependencies ++= Seq(
//  "com.typesafe.akka" %% "akka-actor" % "2.7.0",
//  "com.typesafe.akka" %% "akka-testkit" % "2.7.0" % "test",
//  "org.scalatest" %% "scalatest" % "3.2.14" % "test",
//)
lazy val root = (project in file("."))
  .settings(
    name := "ActorDemo03"
  )
