package example.model

sealed trait Securities {
  def letter: String
}

case object A extends Securities { val letter = "A" }
case object B extends Securities { val letter = "B" }
case object C extends Securities { val letter = "C" }
case object D extends Securities { val letter = "D" }
