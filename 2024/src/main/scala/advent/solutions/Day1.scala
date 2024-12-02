package advent.solutions

import advent.Solution

class Day1 extends Solution(1):
  override def solve(input: String) =
    val pairs = input.split("\n").map: s =>
      val pair = s.split("   ")
      (pair(0).toInt, pair(1).toInt)

    val left = pairs.map(_._1).sorted
    val right = pairs.map(_._2).sorted

    val firstPart = left.zip(right).map(_ - _).map(Math.abs).sum

    val multipliers = right.groupBy(c => c).map: (number, instances) =>
      number -> number * instances.length

    val secondPart = left.flatMap(multipliers.get).sum

    (firstPart, secondPart)


object Day1:
  def main(args: Array[String]): Unit =
    Day1().run()

