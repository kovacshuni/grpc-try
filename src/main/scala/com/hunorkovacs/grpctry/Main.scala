package com.hunorkovacs.grpctry

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import org.slf4j.LoggerFactory

import scala.concurrent.Await
import scala.concurrent.duration._

object Main extends App {

  private val logger = LoggerFactory.getLogger(getClass)
  implicit private val sys: ActorSystem = ActorSystem("grpc-try")
  import sys.dispatcher

  private val greeterServer = new GreeterServer(sys)
  private val bindingF = greeterServer.run()

  logger.info("Hello gRPC!")

  scala.sys addShutdownHook shutdown

  private def shutdown() = {
    logger.info("Exiting...")
    val sysTerminated = for {
      binding <- bindingF
      _       <- binding.unbind()
      _       <- Http().shutdownAllConnectionPools()
      sysTerm <- sys.terminate()
    } yield sysTerm
    Await.ready(sysTerminated, 5 seconds)
  }
}
