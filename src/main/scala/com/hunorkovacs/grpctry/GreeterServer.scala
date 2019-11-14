package com.hunorkovacs.grpctry

import akka.actor.ActorSystem
import akka.http.scaladsl.model.{HttpRequest, HttpResponse}
import akka.http.scaladsl.{Http, HttpConnectionContext}
import akka.stream.{ActorMaterializer, Materializer}
import com.hunorkovacs.grpctry.grpc.GreeterServiceHandler
import org.slf4j.LoggerFactory

import scala.concurrent.{ExecutionContext, Future}

class GreeterServer(sys: ActorSystem) {

  private val logger = LoggerFactory.getLogger(getClass)

  def run(): Future[Http.ServerBinding] = {

    implicit val implSys: ActorSystem = sys
    implicit val mat: Materializer = ActorMaterializer()
    implicit val ec: ExecutionContext = sys.dispatcher

    val service: HttpRequest => Future[HttpResponse] = GreeterServiceHandler(new GreeterServiceImpl())

    val binding = Http().bindAndHandleAsync(
      service,
      interface = "127.0.0.1",
      port = 8080,
      connectionContext = HttpConnectionContext())

    binding.foreach { binding =>
      logger.info(s"gRPC server bound to: ${binding.localAddress}")
    }

    binding
  }
}
