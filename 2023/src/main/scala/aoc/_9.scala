package aoc

import scala.annotation.tailrec

def solution9(data: Vector[String]): String = {
  val puzzles = data.map {
    case s => s.split(" ").map(_.toInt).toVector
  }

  val answers = (for {
    puzzle <- puzzles
    d = derivatives(puzzle, Vector(puzzle)).reverse
    next = d.foldLeft(0) {
      case (acc, current) =>
        current.last + acc
    }
    first = d.foldLeft(0) {
      case (acc, current) =>
        current.head - acc
    }
  } yield (next, first))

  val p1 = answers.map(_._1).sum
  val p2 = answers.map(_._2).sum
  s"Part 1 $p1 part 2 $p2"
}

@tailrec
def derivatives(source: Vector[Int], acc: Vector[Vector[Int]] = Vector.empty): Vector[Vector[Int]] = {
  if(source.forall(_ == 0)) {
    acc
  } else {
    val f = source.sliding(2).map {
      case Vector(a, b) => b - a
    }.toVector
    derivatives(f, acc :+ f)
  }
}
object _9_Test extends Problem(9, InputMode.Test(1), solution9)
object _9_Normal extends Problem(9, InputMode.Normal, solution9)