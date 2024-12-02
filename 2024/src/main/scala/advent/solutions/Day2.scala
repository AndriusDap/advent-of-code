package advent.solutions

import advent.Solution

class Day2 extends Solution(2):
  override def solve(input: String) =
    val reports = input.split("\n").map(_.split(" ").map(_.toInt).toSeq)
    (reports.count(isSafe), reports.count(isKindaSafe))

  def isSafe(report: Seq[Int]): Boolean =
    val deltas = report.sliding(2).flatMap:
        case (Seq(a, b)) => Some((a - b).abs)
        case _ => None
      .toSeq

    (report.sorted == report || report.sorted.reverse == report)
      && deltas.min >= 1 && deltas.max <= 3

  def isKindaSafe(report: Seq[Int]): Boolean =
    isSafe(report) || report.indices.map(report.patch(_, Nil, 1)).exists(isSafe)

object Day2:
  def main(args: Array[String]): Unit =
    Day2().run()

