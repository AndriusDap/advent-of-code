package aoc

import scala.annotation.tailrec

def solution11(data: Vector[String]): String = {


  def gaps(d: Vector[Vector[Char]]) = {
    d.zipWithIndex.flatMap {
      case (c, i) =>
        if (c.contains('#')) {
          None
        } else {
          Some(i)
        }
    }
  }

  val horizontalGaps = gaps(data.map(_.toVector))
  val verticalGaps = gaps(data.map(_.toVector).transpose)

  val stars2 = data.zipWithIndex.flatMap {
    case (row, i) =>
      row.zipWithIndex.flatMap {
        case ('#', j) => Some((i.toLong, j.toLong))
        case _ => None
      }
  }


  val gap = 1000000
  val distancedStars = stars2.map {
    case (x, y) =>
      ((horizontalGaps.count(_ < x) * (gap - 1) + x), (verticalGaps.count(_ < y) * (gap - 1) + y))
  }

  distances(distancedStars.toList).toString
}
def distances(coords: List[(Long, Long)], acc: Long = 0): Long = {
  coords match
    case head :: next =>
      val sums = next.map(n => manhattanDistance(n, head)).sum
      distances(next, acc + sums)
    case Nil => acc
}

def manhattanDistance(a: (Long, Long), b: (Long, Long)): Long = {
  val (xa, ya) = a
  val (xb, yb) = b
  Math.abs(xa - xb) + Math.abs(ya - yb)
}


object _11_Test extends Problem(11, InputMode.Test(1), solution11)
object _11_Normal extends Problem(11, InputMode.Normal, solution11)