organization := """com.hunorkovacs"""

name := """grpc-try"""

version := "1.0.0-SNAPSHOT"

scalaVersion := "2.13.1"

libraryDependencies ++= {
  val akkaVersion = "2.5.25"
  val akkaHttpVersion = "10.1.10"
  val circeVersion = "0.12.2"
  val specs2Version = "4.8.0"

  Seq(
    "com.typesafe.akka" %% "akka-actor" % akkaVersion,
    "com.typesafe.akka" %% "akka-stream" % akkaVersion,

    "com.typesafe.akka" %% "akka-http-core" % akkaHttpVersion,
    "com.typesafe.akka" %% "akka-http-spray-json" % akkaHttpVersion,

    "org.typelevel" %% "cats-core" % "2.0.0",

    "io.circe" %% "circe-core" % circeVersion,
    "io.circe" %% "circe-generic" % circeVersion,
    "io.circe" %% "circe-parser" % circeVersion,
    "de.heikoseeberger" %% "akka-http-circe" % "1.29.1",

    "io.grpc" % "grpc-netty" % scalapb.compiler.Version.grpcJavaVersion,
    "com.thesamet.scalapb" %% "scalapb-runtime-grpc" % scalapb.compiler.Version.scalapbVersion,
    // (optional) If you need scalapb/scalapb.proto or anything from
    // google/protobuf/*.proto
    // "com.thesamet.scalapb" %% "scalapb-runtime" % scalapb.compiler.Version.scalapbVersion % "protobuf",

    "org.slf4j" % "slf4j-api" % "1.7.26",
    "ch.qos.logback" % "logback-classic" % "1.2.3",

    "com.typesafe.akka" %% "akka-stream-testkit" % akkaVersion % Test,
    "com.typesafe.akka" %% "akka-http-testkit" % akkaHttpVersion % Test,
    "org.specs2" %% "specs2-core" % specs2Version % Test,
    "org.specs2" %% "specs2-mock" % specs2Version % Test,
    "org.mockito" % "mockito-core" % "3.1.0" % Test,

    "org.mortbay.jetty.alpn" % "alpn-boot" % "8.1.13.v20181017"
  )
}

fork in run := true

scalacOptions in ThisBuild ++= Seq(
  "-language:postfixOps",
  "-deprecation"
)

//PB.targets in Compile := Seq(
//  scalapb.gen(flatPackage = false) -> (sourceManaged in Compile).value
//)

enablePlugins(AkkaGrpcPlugin)
// ALPN agent
enablePlugins(JavaAgent)
javaAgents += "org.mortbay.jetty.alpn" % "jetty-alpn-agent" % "2.0.9" % "runtime;test"
