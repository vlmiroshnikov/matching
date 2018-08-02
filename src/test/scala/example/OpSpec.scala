package example

import example.model._
import org.scalatest.{FlatSpec, Matchers}

class OpSpec extends FlatSpec with Matchers with Codecs {
  it should "correct sell" in {
    val c        = Client("C1", 0, Seq(Balance(A, 130), Balance(B, 240), Balance(C, 760), Balance(D, 320)))
    val expected = Client("C1", 3000, Seq(Balance(A, 100), Balance(B, 240), Balance(C, 760), Balance(D, 320)))

    c.sell(A, 30, 100) shouldBe expected
  }

  it should "correct buy" in {
    val c        = Client("C1", 1000, Seq(Balance(A, 0), Balance(B, 240), Balance(C, 760), Balance(D, 320)))
    val expected = Client("C1", 0, Seq(Balance(A, 100), Balance(B, 240), Balance(C, 760), Balance(D, 320)))
    c.buy(A, 100, 10) shouldBe expected
  }

}
