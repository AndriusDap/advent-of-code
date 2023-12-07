package aoc

def solution7(data: Vector[String]): String = {
  val hands = data.map {
    case s"$hand $bid" => (hand, bid.toInt)
    case _ => ???
  }
  hands.sortWith {
    case ((leftH, leftBid), (rightH, rightBid)) =>
      lessThan(leftH, rightH)
  }.zipWithIndex.map {
    case ((hand, bid), index) =>

      val winnings = bid * (index + 1)
      println(f"for $hand #$index bid $bid -> $winnings")
      winnings
  }.sum.toString


}

val FiveOfAKind = 6
val FourOfAKind = 5
val FullHouse = 4
val ThreeOfAKind = 3
val TwoPair = 2
val OnePair = 1
val HighCard = 0

def getCombo(hand: String): Int = {
  cards.keys.map { j =>
    val counts = hand.replaceAll("J", j).toCharArray.groupBy(c => c).view.mapValues(_.length).values.toList.sorted.reverse
    counts match {
      case 5 :: Nil => FiveOfAKind
      case 4 :: 1 :: Nil => FourOfAKind
      case 3 :: 2 :: _ => FullHouse
      case 3 :: _ => ThreeOfAKind
      case 2 :: 2 :: _ => TwoPair
      case 2 :: _ => OnePair
      case _ => HighCard
    }
  }.max
}
val cards = Seq("A", "K", "Q", "T", "9", "8", "7", "6", "5", "4", "3", "2", "J").reverse.zipWithIndex.reverse.toMap



def compareHands(left: String, right: String): Boolean = {

  left.zip(right).flatMap {
    case (l, r) if l == r => None
    case (l, r) if cards(l.toString) < cards(r.toString) => Some(true)
    case (l, r) if cards(l.toString) > cards(r.toString) => Some(false)
  }.head
}

def lessThan(left: String, right: String): Boolean = {
  val leftCombo = getCombo(left)
  val rightCombo = getCombo(right)
  if (leftCombo == rightCombo) {
    compareHands(left, right)
  } else {
    leftCombo < rightCombo
  }
}

object _7_Test extends Problem(7, InputMode.Test(1), solution7)
object _7_Normal extends Problem(7, InputMode.Normal, solution7)