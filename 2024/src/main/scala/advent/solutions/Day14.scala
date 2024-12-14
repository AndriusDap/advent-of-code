package advent.solutions

import advent.Solution

import scala.annotation.tailrec
import scala.collection.immutable.Queue

class Day14 extends Solution(14):


  def move(robots: Seq[Seq[Long]], seconds: Int) =

    val xlim = 101
    val ylim = 103

    robots.map:
      case Seq(px, py, vx, vy) =>
        val x = (px + seconds * vx) % xlim
        val y = (py + seconds * vy) % ylim

        val adjustedX = if x < 0 then xlim + x else x
        val adjustedY = if y < 0 then ylim + y else y
        Point(adjustedX.toInt, adjustedY.toInt)

  override def solve(input: String) =
    val robots = input.trim.split("\n").map:
      case s"p=$px,$py v=$vx,$vy" =>
        Seq(px, py, vx, vy).map(_.toLong)

    val seconds = 100
    val positions = move(robots, seconds)

    val q1 = positions.count: point =>
      point.x < 50 && point.y < 51
    val q2 = positions.count: point =>
      point.x > 50 && point.y < 51
    val q3 = positions.count: point =>
      point.x < 50 && point.y > 51
    val q4 = positions.count: point =>
      point.x > 50 && point.y > 51

    val part1 = q1 * q2 * q3 * q4



    println(s"Part 1 is ${part1}")
    (0 to 10000).foreach: i =>
      val moved = move(robots, i)
      val xMean = moved.map(_.x).sum/robots.size
      val yMean = moved.map(_.y).sum/robots.size
      if Math.abs(xMean - 50) > 10 && Math.abs(yMean - 50) > 5 then
        println(s"${i}: $xMean $yMean")
        println(Map2d.apply(move(robots, i).groupBy(identity).map((p, v) => p -> v.length)))


object Day14:
  def main(args: Array[String]): Unit =
    Day14().run(true)