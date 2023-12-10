package aoc

import scala.annotation.tailrec

def solution10(data: Vector[String]): String = {
  val graph = data.map(_.toVector)


  val pretty = graph.map {
    case c =>
      val m = Map(
        "-" -> "─",
        "L" -> "└",
        "J" -> "┘",
        "7" -> "┐",
        "F" -> "┌",
        "|" -> "│",
        "." -> " "
      )

      val nice = m.foldLeft(c.mkString) {
        case (acc, (from, to)) =>
          val f = from.charAt(0)
          acc.map {
            case `f` => to.charAt(0)
            case x => x
          }
      }
      nice
  }
  //println(pretty.mkString("\n"))
  val (xStart, yStart) = pretty.zipWithIndex.flatMap {
    case (str, y) =>
      str.zipWithIndex.map {
        case ('S', x) => Some(x, y)
        case _ => None
      }
  }.flatten.head

  println(s"start location is $xStart $yStart")
  val route = cycleLength(pretty, xStart, yStart + 1, xStart, yStart, (xStart, yStart) :: Nil)
  println(s"part 1: ${(route.length)/2}")
  val rset = route.toSet


  val onlyLoop = pretty.zipWithIndex.map {
    case (s, y) =>
      s.zipWithIndex.map {
        case (c, x) =>
          if (rset.contains((x, y))) {
            c.toString
          } else {
            " "
          }
      }.mkString
  }
  onlyLoop.foreach(println)

  val c = onlyLoop.map {
    line =>
      val updated = line.zipWithIndex.map {
        case (c, index) =>
          val inLoop = wallsInSegment(line.slice(0, index)) % 2 == 1
          if (c == ' ' && inLoop) {
            print('#')
            '#'
          } else {
            print(c)
            c
          }
      }
      println()
      updated
  }
  println(f"par2: ${c.flatten.count(_ == '#')}")
  ""
}

def wallsInSegment(line: String): Int = {
  val cleaned = line.replace("─", "")
  val walls = Seq("└┐", "│", "┌┘", "S")
  walls.map {
    wall => cleaned.sliding(wall.length).count(_ == wall)
  }.sum
}

@tailrec
def cycleLength(graph: Vector[String], x: Int, y: Int, previousX: Int, previousY: Int, route: List[(Int, Int)] = List.empty): List[(Int, Int)] = {
  val current = graph(y).charAt(x)
  //println(f"$x $y: $current")
  if(current == 'S') {
    route
  } else {
    current match {
      case '─' => if (previousX < x) {
        cycleLength(graph, x + 1, y, x, y, route :+ (x, y))
      } else {
        cycleLength(graph, x - 1, y, x, y, route :+ (x, y))
      }
      case '└' => if (previousY < y) {
        cycleLength(graph, x + 1, y, x, y, route :+ (x, y))
      } else {
        cycleLength(graph, x, y - 1, x, y, route :+ (x, y))
      }
      case '┘' => if (previousY < y) {
        cycleLength(graph, x - 1, y, x, y, route :+ (x, y))
      } else {
        cycleLength(graph, x, y - 1, x, y, route :+ (x, y))
      }
      case '┐' => if (previousY > y) {
        cycleLength(graph, x - 1, y, x, y, route :+ (x, y))
      } else {
        cycleLength(graph, x, y + 1, x, y, route :+ (x, y))
      }
      case '┌' => if (previousY > y) {
        cycleLength(graph, x + 1, y, x, y, route :+ (x, y))
      } else {
        cycleLength(graph, x, y + 1, x, y, route :+ (x, y))
      }
      case '│' => if (previousY > y) {
        cycleLength(graph, x, y - 1, x, y, route :+ (x, y))
      } else {
        cycleLength(graph, x, y + 1, x, y, route :+ (x, y))
      }
      case x =>
        println(s"unmatched val ${x}")
        ???
    }
  }
}




object _10_Test extends Problem(10, InputMode.Test(1), solution10)
object _10_2_Test extends Problem(10, InputMode.Test(2), solution10)
object _10_3_Test extends Problem(10, InputMode.Test(3), solution10)
object _10_4_Test extends Problem(10, InputMode.Test(4), solution10)
object _10_5_Test extends Problem(10, InputMode.Test(5), solution10)
object _10_Normal extends Problem(10, InputMode.Normal, solution10)