package advent.solutions

import advent.Solution

import scala.annotation.tailrec
import scala.collection.immutable.Queue

class Day13 extends Solution(13):

  def solveSingle(a: (Int, Int), b: (Int, Int), goal: (Long, Long)): Long =
    val (goalX, goalY) = (BigDecimal(goal._1), BigDecimal(goal._2))
    val (x1, y1) = (BigDecimal(a._1), BigDecimal(a._2))
    val (x2, y2) = (BigDecimal(b._1), BigDecimal(b._2))

    val aSolution  = (goalX * y2 - x2 * goalY) / (x1 * y2 - x2 * y1)
    val bSolution  = (x1 * goalY - goalX * y1) / (x1 * y2 - x2 * y1)

    if aSolution.isWhole && aSolution > 0 && bSolution.isWhole && bSolution > 0 then
      (aSolution * 3 + bSolution).toLong
    else 0L



  override def solve(input: String) =
    val ba = """Button A: X.(\d+), Y.(\d+)""".r
    val bb = """Button B: X.(\d+), Y.(\d+)""".r
    val goal = """Prize: X=(\d+), Y=(\d+)""".r
    val buttonsA = ba.findAllMatchIn(input).map(m => (m.group(1).toInt, m.group(2).toInt)).toSeq
    val buttonsB = bb.findAllMatchIn(input).map(m => (m.group(1).toInt, m.group(2).toInt)).toSeq
    val goals = goal.findAllMatchIn(input).map(m => (m.group(1).toInt, m.group(2).toInt)).toSeq

    buttonsA.zip(buttonsB).zip(goals).map:
      case (((ax, ay), (bx, by)), (gx, gy)) =>
        solveSingle((ax, ay), (bx, by), (gx + 10000000000000L, gy + 10000000000000L))
    .sum


object Day13:
  def main(args: Array[String]): Unit =
    Day13().run()