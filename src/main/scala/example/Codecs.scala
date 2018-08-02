package example

import example.Parser.{clientP, orderP}
import example.model._
import atto._
import Atto._

trait Codecs {
  implicit val clientEncoder: Encoder[Client] = (c: Client) =>
    Seq(c.name, c.balance, c.get(A).count, c.get(B).count, c.get(C).count, c.get(D).count).mkString("\t")

  implicit val clientDecoder: Decoder[Client] = (v: String) => {
    (clientP parseOnly v).option
  }

  implicit val orderDecoder: Decoder[Order] = (v: String) => {
    (orderP parseOnly v).option
  }
}

trait Encoder[T] {
  def encode(v: T): String
}

trait Decoder[T] {
  def decode(v: String): Option[T]
}
