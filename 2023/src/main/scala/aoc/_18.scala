package aoc

import scala.annotation.tailrec
import scala.collection.mutable
import scala.{:+, collection}


def solution18(data: Vector[String]): String = {
  val matrix = data.map(_.toVector.map(_.toInt))

  val digs = data.foldLeft((0, 0) :: Nil) {
    case (acc, s"$dir $count ($color)") =>
      val (deltaY, deltaX) = dir match {
        case "L" => (0, -1)
        case "D" => (1, 0)
        case "U" => (-1, 0)
        case "R" => (0, 1)
      }


      val digs = for {
        n <- 1 to count.toInt
        (y, x) = acc.last
      } yield (y + (deltaY * n), x + (deltaX * n))

      acc ++ digs
  }.toSet
  val hole = show(digs)
  val areaOutside = flood(hole, Set.empty, List(0 -> 0), 0)
  val totalArea = hole.length * hole.head.length
  println(s"Par1: area outside is $areaOutside, total area is ${totalArea}, diff ${totalArea - areaOutside}")



  val (_, _, doubleArea, perimeter) = data.foldLeft((0L, 0L, 0L, 0L)) {
    case ((x, y, area, perimeter), s"$a $b (#$c)") =>
      val direction = c.last match {
        case '2' => (0, -1)
        case '1' => (1, 0)
        case '3' => (-1, 0)
        case '0' => (0, 1)
      }

      val length = Integer.parseInt(c.take(5), 16)

      val x2 = x + direction._2 * length
      val y2 = y + direction._1 * length

      (x2, y2, area + (x * y2 - y * x2), perimeter + length)
  }

  //Dunno why the +1 but it works lol
  println(f" area is ${doubleArea} perimeter is ${perimeter}, answ: ${(doubleArea / 2) + (perimeter/2) + 1}")

  ""
}

@tailrec
def flood(s: Vector[Vector[Int]], visited: Set[(Int, Int)], queue: List[(Int, Int)], counter: Int): Int = {
  queue match {
    case cursor :: tail =>
      val (y, x) = cursor
      if(visited.contains(cursor) || x < 0 || x >= s.head.length || y < 0 || y >= s.length) {
        flood(s, visited, tail, counter)
      } else {
        if (s(y)(x) != 1) {
          val neigbours = List((0, -1), (1, 0), (-1, 0), (0, 1)).map {
            case (dx, dy) =>
              (y + dy, x + dx)
          }
          flood(s, visited + cursor, neigbours ++ tail, counter + 1)
        } else {
          flood(s, visited + cursor, tail, counter)
        }
      }
    case Nil => counter
  }
}

def show(digs: Set[(Int, Int)]) = {

  val maxX = digs.map(_._2).max + 3
  val maxY = digs.map(_._1).max + 3

  val minX = digs.map(_._2).min - 3
  val minY = digs.map(_._1).min - 3

  (minY to maxY).map {
    y =>
      (minX to maxX).map {
        x =>
          if (digs.contains(y -> x)) {
            1
          } else {
            0
          }
      }.toVector
  }.toVector
}


object _18_Test extends Problem(18, InputMode.Test(1), solution18)
object _18_Normal extends Problem(18, InputMode.Normal, solution18)