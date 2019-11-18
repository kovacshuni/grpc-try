package com.hunorkovacs.grpctry.server

import akka.NotUsed
import akka.stream.Materializer
import akka.stream.scaladsl.Source
import com.hunorkovacs.grpctry.grpc.{GreeterService, HelloRequest, HelloResponse}
import org.slf4j.LoggerFactory

class GreeterServiceImpl(implicit mat: Materializer) extends GreeterService {

  private val logger = LoggerFactory.getLogger(getClass)

  override def streamHellos(in: Source[HelloRequest, NotUsed]): Source[HelloResponse, NotUsed] = {
    logger.info(s"sayHello to stream...")
    in.map(request => HelloResponse(message = s"Hello, ${request.name}"))
  }
}
