package example

import atto.Atto.{int, string, takeWhile}
import example.model._
import atto._
import Atto._
import cats.implicits._

object Parser {

  private val nameP = takeWhile(c => c != '\t') <~ skipWhitespace
  private def balanceP(sec: Securities) =
    int.map(v => Balance(sec, v)) <~ skipWhitespace

  private val secP: Parser[Securities] = {
    string(A.letter).map(_ => A: Securities) |
      string(B.letter).map(_ => B: Securities) |
      string(C.letter).map(_ => C: Securities) |
      string(D.letter).map(_ => D: Securities)
  }

  private val opP: Parser[Operation] = {
    string(Sell.letter).map(_ => Sell) |
      string(Buy.letter).map(_ => Buy)
  }

  private val intP = int <~ skipWhitespace

  val clientP = (nameP, intP, balanceP(A), balanceP(B), balanceP(C), balanceP(D)).mapN {
    case (name, dol, a, b, c, d) => Client(name, dol, Seq(a, b, c, d))
  }

  val orderP = (nameP, opP <~ skipWhitespace, secP <~ skipWhitespace, intP, intP).mapN {
    case (name, op, sec, value, count) => Order(name, op, sec, value, count)
  }
}
