package aoc

import scala.annotation.tailrec
import scala.collection

def solution14(data: Vector[String]): String = {

  val field = data.map(_.toVector)

  val cycleDetection = (1 to 200).foldLeft(Vector(field -> 0)) {
    case (fields, i) =>
      val (lastField, lastIteration) = fields.last
      val next = cycle(lastField)
      fields :+ (next, i)
  }

  val (cycleStart, cycleEnd, cycledField) = cycleDetection.flatMap {
    case (field, iteration) =>
      cycleDetection.find {
        case (f, i) => f == field && i != iteration
      }.map {
        case (_, i) => (iteration, i, field)
      }
  }.head

  val count = (1000000000 - cycleStart) % (cycleEnd - cycleStart)
  println(s"Iterations remaining: $count")


  val result = (1 to count).foldLeft(cycledField) {
    case (field, _) => cycle(field)
  }

  result.transpose.flatMap {
    line =>
      line.reverse.zipWithIndex.flatMap {
        case ('O', x) => Some(x + 1)
        case _ => None
      }
  }.sum.toString
}

def show(field: Vector[Vector[Char]]): Unit =  {
  println(field.map(_.mkString("")).mkString("\n"))
  println()
}

def cycle(field: Vector[Vector[Char]]) = {
   Seq("north", "west", "south", "east").foldLeft(field) {
      case (field, direction) =>
        roll(field, direction)
   }
}

def roll(field: Vector[Vector[Char]], direction: String): Vector[Vector[Char]] = {
   direction match {
      case "north" =>
        field.transpose.map {
          line =>
            //Move everything to the left
            roll(line.mkString("")).toVector
        }.transpose
      case "west" =>
        field.map {
          line =>
            roll(line.mkString("")).toVector
        }
      case "east" =>
        field.map {
          line =>
            roll(line.reverse.mkString("")).reverse.toVector
        }
      case "south" =>
        field.transpose.map {
          line =>
            //Move everything to the left
            roll(line.reverse.mkString("")).reverse.toVector
        }.transpose
      case _ => ???
  }
}

@tailrec
def roll(line: String): String = {
  if(line.contains(".O")) {
    roll(line.replaceAll("\\.O", "O."))
  } else {
    line
  }
}


object _14_Test extends Problem(14, InputMode.Test(1), solution14)
object _14_Normal extends Problem(14, InputMode.Normal, solution14)