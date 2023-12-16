package aoc

import scala.annotation.tailrec
import scala.collection.mutable
import scala.{:+, collection}



val left = 1 << 2
val right = 1 << 3
val up = 1 << 4
val down = 1 << 5

def solution16(data: Vector[String]): String = {
  data.mkString("\n")


  val field =
    traverse(data.map(_.toVector), data.map(_.toVector.map(_ => 0)), Cursor(0, 0, right) :: Nil)

  val leftStarts = data.zipWithIndex.map {
    case (_, i) => Cursor(0, i, right)
  }

  val rightStarts = data.zipWithIndex.map {
    case (_, i) => Cursor(data.head.length - 1, i, left)
  }

  val topStarts = data.head.zipWithIndex.map {
    case (_, i) => Cursor(i, 0, down)
  }
  val bottomStarts = data.head.zipWithIndex.map {
    case (_, i) => Cursor(i, data.length - 1, up)
  }


    println(field.map(_.map {
      case c if c == 0 => "."
      case c => "#"
    }.mkString("")).mkString("\n"))

  println((field.map(_.count(_ != 0)).sum).toString)

  (leftStarts ++ topStarts ++ rightStarts ++ bottomStarts).map {
    start =>
      val field = traverse(data.map(_.toVector), data.map(_.toVector.map(_ => 0)), start :: Nil)
      (field.map(_.count(_ != 0)).sum)
  }.max.toString
}


case class Cursor(x: Int, y: Int, direction: Int)



def traverse(map: Vector[Vector[Char]], energised: Vector[Vector[Int]], cursors: List[Cursor]): Vector[Vector[Int]] = {
  cursors match
    case Cursor(x, y, direction) :: tail =>
      //println(s"Tail is ${tail}")
      if(x < 0 || y < 0 || y >= map.length || x >= map.head.length) {
        traverse(map, energised, tail)
      } else {
        val newEnergy = energised.updated(y, energised(y).updated(x, energised(y)(x) | direction))

        val cUp = Cursor(x, y - 1, up)
        val cDown = Cursor(x, y + 1, down)
        val cLeft = Cursor(x - 1, y, left)
        val cRight = Cursor(x + 1, y, right)

        map(y)(x) match {
          case _ if (energised(y)(x) & direction) == direction =>
            //println("Ignoring since direciton is already energised")
            traverse(map, newEnergy, tail)
          case '|' if direction == left || direction == right =>
            //println("splitting up and down")
            traverse(map, newEnergy, cUp :: cDown :: tail)
          case '-' if direction == up || direction == down =>
            //println("splitting left and right")
            traverse(map, newEnergy,  cLeft :: cRight :: tail)
          case '\\' =>
            direction match {
              case `left` =>
                //println("reflecting up")
                traverse(map, newEnergy, cUp :: tail)
              case `right` =>
                //println("reflecting down")
                traverse(map, newEnergy, cDown :: tail)
              case `up` =>
                //println("reflecting left")
                traverse(map, newEnergy, cLeft :: tail)
              case `down` =>
                //println("reflecting right")
                traverse(map, newEnergy, cRight :: tail)
            }
          case '/' =>
            direction match {
              case `left` =>
                //println("reflecting down")
                traverse(map, newEnergy, cDown :: tail)
              case `right` =>
                //println("reflecting up")
                traverse(map, newEnergy, cUp :: tail)
              case `up` =>
                //println("reflecting right")
                traverse(map, newEnergy, cRight :: tail)
              case `down` =>
                //println("reflecting left")
                traverse(map, newEnergy, cLeft :: tail)
            }
          case any =>
            direction match {
              case `left` =>
                //println("proceeding left")
                traverse(map, newEnergy,  cLeft :: tail)
              case `right` =>
                //println("proceeding right")
                traverse(map, newEnergy,  cRight :: tail)
              case `up` =>
                //println("proceeding up")
                traverse(map, newEnergy,  cUp :: tail)
              case `down` =>
                //println("proceeding down")
                traverse(map, newEnergy,  cDown :: tail)
            }
        }
      }
    case Nil => energised
}



object _16_Test extends Problem(16, InputMode.Test(1), solution16)
object _16_Normal extends Problem(16, InputMode.Normal, solution16)