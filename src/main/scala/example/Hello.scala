package example

import java.nio.file.Paths

import cats.effect.{IO, Sync}
import example.model._
import fs2.{io, text, _}

import scala.language.higherKinds

object Hello extends App with Codecs {
  lazy val clientsFile = "clients.txt"
  lazy val resultFile  = "result.txt"
  lazy val ordersFile  = "orders.txt"

  val res = (for {
    clients <- read[IO](clientsFile)
    results <- readOrders[IO](ordersFile).throughPure(procees(clients)).compile.toList
    _       <- write[IO](resultFile, results.head)
  } yield results.head).unsafeRunSync()

  res.foreach(c => println(implicitly[Encoder[Client]].encode(c)))

  def procees[F[_]](clients: Vector[Client]): Pipe[F, Order, Vector[Client]] = {
    _.fold(clients) {
      case (cs, order) =>
        cs.collect {
          case c: Client if c.name == order.name =>
            order.op match {
              case Sell => c.sell(order.sec, order.count, order.value)
              case Buy  => c.buy(order.sec, order.count, order.value)
            }
          case other => other
        }
    }
  }

  def readOrders[F[_]](file: String)(implicit F: Sync[F], decoder: Decoder[Order]) = {
    io.file
      .readAll(Paths.get(file), 1000)
      .through(text.utf8Decode)
      .through(text.lines)
      .map(decoder.decode)
      .collect { case Some(v) => v }
  }

  def read[F[_]](file: String)(implicit F: Sync[F], decoder: Decoder[Client]) = {
    io.file
      .readAll(Paths.get(file), 1000)
      .through(text.utf8Decode)
      .through(text.lines)
      .map(decoder.decode)
      .collect { case Some(v) => v }
      .compile
      .toVector
  }

  def write[F[_]](file: String, clients: Vector[Client])(implicit F: Sync[F], encoder: Encoder[Client]) = {
    Stream
      .emits(clients)
      .covary[F]
      .map(encoder.encode)
      .intersperse("\n")
      .through(text.utf8Encode)
      .through(io.file.writeAll(Paths.get(file)))
      .compile
      .drain
  }
}
