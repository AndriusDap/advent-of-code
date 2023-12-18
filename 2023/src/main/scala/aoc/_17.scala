package aoc

import scala.annotation.tailrec
import scala.collection.mutable
import scala.{:+, collection}


def solution17(data: Vector[String]): String = {
  val matrix = data.map(_.toVector.map(_.toInt))

  println(matrix.map(_.mkString("")).mkString("\n"))


  ""
}


object _17_Test extends Problem(17, InputMode.Test(1), solution17)
object _17_Normal extends Problem(17, InputMode.Normal, solution17)