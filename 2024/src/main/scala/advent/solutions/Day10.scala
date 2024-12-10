package advent.solutions

import advent.Solution

class Day10 extends Solution(10):

  override def solve(input: String) =
    val map = input.split("\n")
      .map(_.trim.zipWithIndex).zipWithIndex
      .flatMap: (c, y) =>
        c.map: (l, x) =>
          if l.isDigit then (x, y) -> l.toString.toInt
          else (x, y) -> 5000
      .toMap

    def neighbours(of: (Int, Int)) = {
      val (x, y) = of
      ((x + 1, y) :: (x - 1, y) :: (x, y - 1) :: (x, y + 1) :: Nil)
        .filter(map.contains).filter(map(_) - 1 == map(of))
    }

    def traverse(from: (Int, Int)): Seq[(Int, Int)] = {
      map.get(from) match
        case Some(9) => Seq(from)
        case Some(_) => neighbours(from).flatMap(traverse)
        case None => Seq.empty
    }

    val trails = map.filter(_._2 == 0).keys.toSeq.map(traverse)
    val p1 = trails.map(_.toSet).map(_.size).sum
    val p2 = trails.map(_.size).sum
    (p1, p2)

object Day10:
  def main(args: Array[String]): Unit =
    Day10().run()