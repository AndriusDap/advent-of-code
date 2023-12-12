package aoc

import scala.annotation.tailrec

def solution12(data: Vector[String]): String = {
  val springs = data.map {
    s =>
      val pattern = s.split(" ")(0) * 5
      val definition = s.split(" ")(1).split(",").map(_.toInt)
      pattern -> (0 to 5).flatMap(_ => definition)
  }

  
  springs.map {
    case (s, c) =>
      val possible = combinations(s).count(matches(_, c))
      //println(f"For $s we found ${possible} combinations")
      possible
  }.sum.toString
}

def matches(pattern: String, fields: Seq[Int]): Boolean = {
  val parts = pattern.split("\\.").map(_.length).filter(_ != 0)
  //println(s"For $pattern parts are ${parts.mkString(",")}, looking for $fields")
  if(parts.length != fields.length) {
    false
  } else {
    parts.zip(fields).forall(_ == _)
  }
}

@inline
def getNthBit(from: Int, bit: Int): Boolean = {
  (from & 1 << bit) != 0
}

def combinations(definition: String): Seq[String] = {
  val unknowns = definition.count(_ == '?')
  val placeIndices = definition.zipWithIndex.flatMap {
    case ('?', i) => Some(i)
    case _ => None
  }.zipWithIndex.map(_.swap).toMap

  (0 until scala.math.pow(2d, unknowns).intValue()).map { iteration =>
    val s = definition.toCharArray
    (0 until unknowns).foreach {
      case field =>
        s(placeIndices(field)) = if(getNthBit(iteration, field)) {
          '.'
        } else {
          '#'
        }
    }
    new String(s)
  }
}

object _12_Test extends Problem(12, InputMode.Test(1), solution12)
object _12_Normal extends Problem(12, InputMode.Normal, solution12)