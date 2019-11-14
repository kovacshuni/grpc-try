package com.hunorkovacs.grpctry

import akka.actor.ActorSystem
import akka.stream.ActorMaterializer
import org.slf4j.LoggerFactory

import scala.concurrent.Await
import scala.concurrent.duration._

object Main extends App {

  private val logger = LoggerFactory.getLogger(getClass)
  implicit private val sys: ActorSystem = ActorSystem("json-validation")
  implicit private val mat: ActorMaterializer = ActorMaterializer()

  logger.info("Hello gRPC!")

  shutdown()

  private def shutdown() = {
    logger.info("Exiting...")
    Await.ready(sys.terminate(), 5 seconds)
  }
}
