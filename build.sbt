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
    // (optional) If you need scalapb/scalapb.proto or anything from
    // google/protobuf/*.proto
    // "com.thesamet.scalapb" %% "scalapb-runtime" % scalapb.compiler.Version.scalapbVersion % "protobuf",

    "org.slf4j" % "slf4j-api" % "1.7.26",
    "ch.qos.logback" % "logback-classic" % "1.2.3",
  )
}

fork in run := true

scalacOptions in ThisBuild ++= Seq(
  "-language:postfixOps",
  "-deprecation"
)

// not needed with akka grpc
//PB.targets in Compile := Seq(
//  scalapb.gen(flatPackage = false) -> (sourceManaged in Compile).value
//)

enablePlugins(AkkaGrpcPlugin)
// ALPN agent
//enablePlugins(JavaAgent)
//javaAgents += "org.mortbay.jetty.alpn" % "jetty-alpn-agent" % "2.0.9" % "runtime;test"
