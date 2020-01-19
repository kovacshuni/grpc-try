ThisBuild / organization := """com.hunorkovacs"""

ThisBuild / version := "1.0.0-SNAPSHOT"

ThisBuild / scalaVersion := "2.13.1"

lazy val root = (project in file("."))
  .aggregate(proto, client, server)

lazy val commonSettings = Seq(

  libraryDependencies ++= {
    val akkaVersion = "2.5.25"
    val akkaHttpVersion = "10.1.10"

    Seq(
      "com.typesafe.akka" %% "akka-actor" % akkaVersion,
      "com.typesafe.akka" %% "akka-stream" % akkaVersion,

      "com.typesafe.akka" %% "akka-http" % akkaHttpVersion,
      "com.typesafe.akka" %% "akka-http2-support" % akkaHttpVersion,

      "io.grpc" % "grpc-netty" % scalapb.compiler.Version.grpcJavaVersion,
      "com.thesamet.scalapb" %% "scalapb-runtime-grpc" % scalapb.compiler.Version.scalapbVersion,

      "org.slf4j" % "slf4j-api" % "1.7.26",
      "ch.qos.logback" % "logback-classic" % "1.2.3",
    )
  },

  fork in run := true,

  scalacOptions in ThisBuild ++= Seq(
    "-language:postfixOps",
    "-deprecation"
  )
)

lazy val proto = (project in file("proto"))
  .enablePlugins(AkkaGrpcPlugin)
  .settings(
    commonSettings,
    name := """grpc-try-proto"""
  )

lazy val client = (project in file("client"))
  .dependsOn(proto)
  .settings(
    commonSettings,
    name := """grpc-try-client"""
  )

lazy val server = (project in file("server"))
  .dependsOn(proto)
  .settings(
    commonSettings,
    name := """grpc-try-server"""
  )
