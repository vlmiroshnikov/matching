package example

import example.model._
import org.scalatest._

class CodecsSpec extends FlatSpec with Matchers with Codecs {
  it should "decode client" in {
    val row = "C1\t1000\t130\t240\t760\t320"
    implicitly[Decoder[Client]].decode(row) shouldBe Some(
      Client("C1", 1000, Seq(Balance(A, 130), Balance(B, 240), Balance(C, 760), Balance(D, 320)))
    )
  }

  it should "be encode" in {
    val data = Client("C1", 1000, Seq(Balance(A, 130), Balance(B, 240), Balance(C, 760), Balance(D, 320)))
    val row = "C1\t1000\t130\t240\t760\t320"
    implicitly[Encoder[Client]].encode(data) shouldBe row
  }

  it should "parse order" in {
    val row = "C8\tb\tC\t15\t4"
    implicitly[Decoder[Order]].decode(row) shouldBe Some(Order("C8", Buy, C, 15, 4))
  }
}

