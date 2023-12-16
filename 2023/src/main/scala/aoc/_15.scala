package aoc

import scala.annotation.tailrec
import scala.{:+, collection}

sealed trait Command
case class Addition(label: String, value: Int) extends Command
case class Subtraction(label: String) extends Command

def solution15(data: Vector[String]): String = {
  val line = data.head


  println(s"Part1: ${line.split(",").map(hash).sum.toString}")

  val commands: Seq[Command] = line.split(",").map {
    case s"${label}=${number}" => Addition(label, number.toInt)
    case s"$label-" => Subtraction(label)
  }

  val map = commands.foldLeft(Map.empty[Int, Seq[(String, Int)]]) {
    case (map, Addition(label, value)) =>
      val boxIndex = hash(label)

      val boxContents: Seq[(String, Int)] = map.getOrElse(boxIndex, Seq.empty)
      val updatedContent = if(boxContents.map(_._1).contains(label)) {
        boxContents.map {
          case (l, v) if l == label => (l, value)
          case other => other
        }
      } else {
        boxContents :+ (label, value)
      }
      map + (boxIndex -> updatedContent)
    case (map, Subtraction(label)) =>
      val boxIndex = hash(label)
      val boxContents = map.getOrElse(boxIndex, Seq.empty).filter(_._1 != label)

      map + (boxIndex -> boxContents)
  }
  println(map)


  map.toSeq.map {
    case (boxNumber, lenses) =>
      lenses.zipWithIndex.map {
        case ((label, focalLength), slot) =>
          (boxNumber + 1) * (slot + 1) * focalLength
      }.sum
  }.sum.toString
}


def hash(string: String): Int= {
  string.toVector.foldLeft(0) {
    case (acc, char) =>
      ((acc + char.toInt) * 17) % 256
  }
}


object _15_Test extends Problem(15, InputMode.Test(1), solution15)
object _15_Normal extends Problem(15, InputMode.Normal, solution15)