package com.hunorkovacs.grpctry.client

import akka.{Done, NotUsed}
import akka.actor.ActorSystem
import akka.grpc.GrpcClientSettings
import akka.stream.ActorMaterializer
import akka.stream.scaladsl.Source
import com.hunorkovacs.grpctry.grpc.{GreeterService, GreeterServiceClient, HelloRequest, HelloResponse}
import org.slf4j.LoggerFactory

import scala.concurrent.{ExecutionContextExecutor, Future}
import scala.concurrent.duration._
import scala.util.{Failure, Success}

object ClientMain extends App {

  private val logger = LoggerFactory.getLogger(getClass)

  implicit val sys: ActorSystem = ActorSystem("grpc-try-client")
  implicit val mat: ActorMaterializer = ActorMaterializer()
  implicit val ec: ExecutionContextExecutor = sys.dispatcher

  val clientSettings: GrpcClientSettings = GrpcClientSettings.fromConfig(GreeterService.name)
  val client: GreeterService = GreeterServiceClient(clientSettings)
  runStreamingRequestReplyExample()

  def runStreamingRequestReplyExample(): Unit = {
    val requestStream: Source[HelloRequest, NotUsed] = Source
      .tick(100 millis, 1 second, "tick")
      .zipWithIndex
      .map { case (_, i) => i }
      .map(i => HelloRequest(name = s"Alice-$i"))
      .take(10)
      .mapMaterializedValue(_ => NotUsed)

    val responseStream: Source[HelloResponse, NotUsed] = client.streamHellos(requestStream)
    val done: Future[Done] = responseStream.runForeach(reply => logger.info(s"got streaming reply: ${reply.message}"))

    done.onComplete {
      case Success(_) => logger.info("streamingRequestReply done")
      case Failure(e) => logger.error(s"Error streamingRequestReply: $e")
    }
  }
}
