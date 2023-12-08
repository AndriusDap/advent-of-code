package aoc

import scala.annotation.tailrec

def solution8(data: Vector[String]): String = {
  val path = data.head.toCharArray.map(_.toString).toList
  val graph = data.flatMap {
    case s"$from = ($left, $right)" => Some(from -> (left, right))
    case _ => None
  }.toMap

  graph.keys.filter(_.endsWith("A")).map {
    case startingPoint =>
      traverse(path, graph, startingPoint)
  }.toSet.toString()
  // Plug the answer into google sheets =LCM(...)
}

@tailrec
def traverse(path: List[String], graph: Map[String, (String, String)], current: String, depth: Long = 0): Long = {
  if(current.endsWith("Z")) {
    depth
  } else {
    path match
      case head :: tail =>
        val (left, right) = graph(current)
        val next = if (head == "L") {
          left
        } else {
          right
        }

        traverse(tail :+ head, graph, next, depth + 1)
      case Nil => ???
  }
}


object _8_Test extends Problem(8, InputMode.Test(1), solution8)
object _8_2_Test extends Problem(8, InputMode.Test(2), solution8)
object _8_Normal extends Problem(8, InputMode.Normal, solution8)