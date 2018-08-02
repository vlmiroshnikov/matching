package example.model

case class Client(name: String, balance: Int, shares: Seq[Balance]) {
  def get(sec: Securities): Balance = {
    shares.find(_.sec == sec).get
  }
}

object Client {
  implicit class Ops(val cl: Client) extends AnyVal {
    def sell(sec: Securities, count: Int, value: Int): Client = {
      val updated = cl.shares.collect {
        case b @ Balance(s, cnt) if s == sec =>
          b.copy(count = cnt - count)
        case other => other
      }
      cl.copy(balance = cl.balance + value * count, shares = updated)
    }

    def buy(sec: Securities, count: Int, value: Int): Client = {
      val updated = cl.shares.collect {
        case b @ Balance(s, cnt) if s == sec =>
          b.copy(count = cnt + count)
        case other => other
      }
      cl.copy(balance = cl.balance - value * count, shares = updated)
    }
  }
}