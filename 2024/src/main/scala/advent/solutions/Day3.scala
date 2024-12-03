package advent.solutions

import advent.Solution

class Day3 extends Solution(3):
  override def solve(input: String) =
    val p1 =
      val action = """mul\(\d+,\d+\)""".r
      action.findAllIn(input).map: s =>
        val operators = s.drop(4).dropRight(1).split(",").map(_.toInt)
        operators.head * operators.last
      .sum

    val p2 =
      val action = """(do\(\)|don't\(\)|mul\(\d+,\d+\))""".r
      action.findAllIn(input).foldLeft((0, 1)):
        case ((acc, _), "do()") =>
          (acc, 1)
        case ((acc, _), "don't()") =>
          (acc, 0)
        case ((acc, multiplier), s) =>
          val operators = s.drop(4).dropRight(1).split(",").map(_.toInt)
          (acc + operators.head * operators.last * multiplier, multiplier)
      ._1
    (p1, p2)


object Day3:
  def main(args: Array[String]): Unit =
    Day3().run()

