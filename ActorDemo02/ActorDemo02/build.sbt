
ThisBuild / version := "0.1.0-SNAPSHOT"
ThisBuild / scalaVersion := "2.13.0"
// 依赖
//libraryDependencies ++= Seq(
//  "com.typesafe.akka" %% "akka-actor" % "2.3.3",
//  "com.typesafe.akka" %% "akka-testkit" % "2.3.6" % "test",
//  "org.scalatest" %% "scalatest" % "2.1.6" % "test",
//  "com.typesafe.akka" %% "akka-remote" % "2.3.6"

//)
lazy val root = (project in file("."))
  .settings(
    name := "ActorDemo02"
  )
