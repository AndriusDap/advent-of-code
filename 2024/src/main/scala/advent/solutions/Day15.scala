package advent.solutions

import advent.Solution

import scala.annotation.tailrec
import scala.collection.immutable.Queue

class Day15 extends Solution(15):
  def left(m: Seq[String]) = m.map("_(O*)@".r.replaceAllIn(_, "$1@_"))
  def right(m: Seq[String]) = m.map("@(O*)_".r.replaceAllIn(_, "_@$1"))

  @tailrec
  private def move(warehouse: Seq[String], commands: List[Char]): Seq[String] =
    commands match
      case '<' :: next => move(left(warehouse), next)
      case '>' :: next => move(right(warehouse), next)
      case '^' :: next => move(left(warehouse.transpose.map(_.mkString)).transpose.map(_.mkString), next)
      case 'v' :: next => move(right(warehouse.transpose.map(_.mkString)).transpose.map(_.mkString), next)
      case _ => warehouse

  def score(warehouse: Map2d[Char]): Long =
    warehouse.underlying.flatMap:
      case (p, 'O') => Some(p)
      case _ => None
    .map(p => p.x + p.y * 100)
    .map(_.toLong)
    .sum


  override def solve(input: String) =
    val lines = input.split("\n")

    val warehouse = lines.filter(_.contains("#")).map(_.replaceAll("\\.", "_")).toList
    val commands = lines.filterNot(_.contains("#")).mkString("").trim

    val after = move(warehouse, commands.toCharArray.toList)

    score(Map2d.fromLines(after.toList))

object Day15:
  def main(args: Array[String]): Unit =
    Day15().run()