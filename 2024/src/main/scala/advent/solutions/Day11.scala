package advent.solutions

import advent.Solution

import scala.collection.mutable

class Day11 extends Solution(11):
  def blink(x: Long): List[Long] =
    if x == 0 then 1 :: Nil
    else
      val stringy = x.toString
      if stringy.length % 2 == 0 then
        val (left, right) = stringy.splitAt(stringy.length / 2)
        left.toLong :: right.toLong :: Nil
      else x * 2024 :: Nil

  override def solve(input: String) =
    val stones = input.split(" ").map(_.toLong)

    (25 :: 75 :: Nil).map: blinks =>
      (0 until blinks).foldLeft(stones.map(_ -> 1L).toSeq):
        case (stones, _) =>
          stones.flatMap: (stone, count) =>
            blink(stone).map(_ -> count)
          .groupBy(_._1).map((stone, counts) => stone -> counts.map(_._2).sum).toSeq
      .map(_._2).sum

object Day11:
  def main(args: Array[String]): Unit =
    Day11().run()