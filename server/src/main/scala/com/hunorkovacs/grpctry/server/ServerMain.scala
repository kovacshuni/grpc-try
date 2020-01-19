package com.hunorkovacs.grpctry.server

import akka.actor.ActorSystem
import akka.http.scaladsl.model.{HttpRequest, HttpResponse}
import akka.http.scaladsl.{Http, HttpConnectionContext}
import akka.stream.{ActorMaterializer, Materializer}
import com.hunorkovacs.grpctry.grpc.GreeterServiceHandler
import org.slf4j.LoggerFactory

import scala.concurrent.{Await, Future}
import scala.concurrent.duration._

object ServerMain extends App {

  private val logger = LoggerFactory.getLogger(getClass)
  implicit private val sys: ActorSystem = ActorSystem("grpc-try")
  implicit private val mat: Materializer = ActorMaterializer()
  import sys.dispatcher

  val service: HttpRequest => Future[HttpResponse] = GreeterServiceHandler(new GreeterServiceImpl())
  val binding: Future[Http.ServerBinding] = Http().bindAndHandleAsync(
    service,
    interface = "127.0.0.1",
    port = 8080,
    connectionContext = HttpConnectionContext())

  binding.foreach { binding =>
    logger.info(s"gRPC server bound to: ${binding.localAddress}")
  }

  logger.info("Hello gRPC!")

  scala.sys addShutdownHook shutdown

  private def shutdown() = {
    logger.info("Exiting...")
    val sysTerminated = for {
      binding <- binding
      _       <- binding.unbind()
      _       <- Http().shutdownAllConnectionPools()
      sysTerm <- sys.terminate()
    } yield sysTerm
    Await.ready(sysTerminated, 5 seconds)
  }
}
