package aoc

import java.util.concurrent.ConcurrentHashMap
import scala.annotation.tailrec
import scala.collection
import scala.collection.mutable
import scala.collection.parallel.immutable.ParVector

def solution13(data: Vector[String]): String = {
  val fields = data.foldLeft(List.empty[String]) {
    case (Nil, line) => line :: Nil
    case (list, line) =>
      if (line.strip().isEmpty) {
        list :+ ""
      } else {
        list.dropRight(1) :+ list.last + "\n" + line
      }
  }

  fields.map {
    field =>
      val simplified = field.split("\n").filter(_.nonEmpty).toVector.map(_.toVector)
      println("Looking for the clean line")
      val cleanLine = findMirrorLine(simplified, None)
      println("Looking for vertical smudged line")
      val verticalLine = smudges(simplified, cleanLine)

      verticalLine match
        case Some(value) => value
        case None =>
          println("Looking for clean horizontal line")
          val cleanLine2 = findMirrorLine(simplified.transpose, None)
          println("Looking for smudged horizontal line")
          smudges(simplified.transpose, cleanLine2) match {
            case Some(v) => v * 100
            case None => ???
          }
  }.sum.toString()
}


def findMirrorLine(field: Vector[Vector[Char]], lineToIgnore: Option[Int]): Option[Int] = {
  val columnCount = field.head.length

  val r = (1 until columnCount).filterNot(lineToIgnore.contains).find(i => mirrors(field, i))
  r.map(r => println(s"Found line on ${r}"))
  r
}

def smudges(field: Vector[Vector[Char]], lineToIgnore: Option[Int]): Option[Int] = {
  val smudges = field.zipWithIndex.flatMap {
    case (line, outer) =>
      line.zipWithIndex.map(_._2).map(inner => (outer, inner))
  }

  smudges.flatMap {
    case (outer, inner) =>
      val smudgedField = field.updated(outer, field(outer).updated(inner, invert(field(outer)(inner))))

      println(s"Field before is $field")
      println(s"Field after  is $smudgedField")
      findMirrorLine(smudgedField, lineToIgnore)
  }.headOption
}

def invert: Char => Char = {
  case '#' => '.'
  case '.' => '#'
}


def mirrors(field: Vector[Vector[Char]], afterColumn: Int): Boolean = {
  field.forall {
    line =>
      val (left, right) = line.splitAt(afterColumn)
      if (left.length < right.length) {
        right.startsWith(left.reverse)
      } else {
        left.reverse.startsWith(right)
      }
  }
}


object _13_Test extends Problem(13, InputMode.Test(1), solution13)
object _13_Normal extends Problem(13, InputMode.Normal, solution13)