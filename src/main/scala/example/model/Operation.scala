package example.model

sealed trait Operation {
  def letter: String
}

case object Sell extends Operation { val letter = "s" }
case object Buy extends Operation { val letter  = "b" }
