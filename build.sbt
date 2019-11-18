organization := """com.hunorkovacs"""

name := """grpc-try"""

version := "1.0.0-SNAPSHOT"

scalaVersion := "2.13.1"

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
}

fork in run := true

scalacOptions in ThisBuild ++= Seq(
  "-language:postfixOps",
  "-deprecation"
)

enablePlugins(AkkaGrpcPlugin)
