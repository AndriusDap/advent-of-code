package aoc

import java.util.concurrent.ConcurrentHashMap
import scala.annotation.tailrec
import scala.collection
import scala.collection.mutable
import scala.collection.parallel.immutable.ParVector

def solution12(data: Vector[String]): String = {
  val springs = data.map {
    s =>
      val pattern = (0 until 5).map(_ => (s.split(" ")(0))).mkString("?")
      val definition = s.split(" ")(1).split(",").map(_.toInt)
      pattern -> (0 until 5).flatMap(_ => definition)
  }


  print(s"Total count is ${springs.length}")

  ParVector(springs:_*).zipWithIndex.flatMap {
    case ((s, c), id) =>
      val possible = solve(s.toVector, c, new mutable.HashMap())
      println(f"$id: For $s with $c we found ${possible} combinations")
      possible
  }.sum[Long].toString
}

def solve(template: Vector[Char], definition: Seq[Int], cache: scala.collection.mutable.Map[(String, Seq[Int]), Option[Long]]): Option[Long] = {
  if(cache.contains((template.mkString, definition))) {
    return cache((template.mkString, definition))
  }
  if(definition.isEmpty) {
    if(template.contains('#')) {
      return None
    } else {
      return Some(1)
    }
  }

  val valueToInject = definition.max

  val (value, index) = definition.zipWithIndex.find {
    case (value, index) => value == valueToInject
  }.get

  val left = definition.take(index)
  val right = definition.drop(index + 1)
  val results = template.zipWithIndex.sliding(valueToInject).flatMap {
    case x =>
      val leftFits = if(x.head._2 - 1 >= 0) {
        template(x.head._2 - 1) != '#'
      } else {
        true
      }

      val rightFits = if (x.last._2 + 1 < template.length) {
        template(x.last._2 + 1) != '#'
      } else {
        true
      }

      val centerIsOk = !x.exists(_._1 == '.') && x.nonEmpty

      if (leftFits && rightFits && centerIsOk) {
        val leftTemplate = template.slice(0, x.head._2 - 1).appended('.')

        for {
          left <- solve(leftTemplate, left, cache)
          rightTemplate = template.slice(x.last._2 + 2, template.length).prepended('.')
          right <- solve(rightTemplate, right, cache)
        } yield left * right
      } else {
        None
      }
  }.sum
  if(results != 0){
    cache.put((template.mkString, definition), Some(results))
    Some(results)
  } else {
    cache.put((template.mkString, definition), None)
    None
  }
}

object _12_Test extends Problem(12, InputMode.Test(1), solution12)
object _12_Normal extends Problem(12, InputMode.Normal, solution12)