package advent.solutions

import advent.Solution

class Day8 extends Solution(8):

  def math(points: Array[(Int, Int)]) =
    (for
      x <- points
      y <- points if x != y
    yield (x, y)).map:
      case ((x1, y1), (x2, y2)) =>
        val dx = x2 - x1
        val dy = y2 - y1
        isMathing(dx, dy, (y1 * dx) - (x1 * dy))

  def isMathing(dx: Int, dy: Int, magic: Int)(x: Int, y: Int): Boolean = (dx * y - dy * x) == magic

  override def solve(input: String) =
    val antennas = input.split("\n")
      .map(_.trim.zipWithIndex).zipWithIndex
      .flatMap: (c, y) =>
        c.map((l, x) => (l, (x, y)))
      .filter(_._1 != '.')

    val maxX = input.split("\n").head.length
    val maxY =  input.split("\n").length

    val equations = antennas.groupBy(_._1).map(_._2.map(_._2)).flatMap(math).toSeq

    val field = (0 until maxY).map: y =>
      (0 until maxX).count: x =>
        equations.exists(_.apply(x, y))

    field.sum

object Day8:
  def main(args: Array[String]): Unit =
    Day8().run()