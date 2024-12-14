package advent.solutions

import advent.Solution

import scala.annotation.tailrec
import scala.collection.immutable.Queue

class Day12 extends Solution(12):

  @tailrec
  private def flood2(map: Map2d[Char], queue: List[Point], visited: Set[Point] = Set.empty): Set[Point] =
    if queue.isEmpty then visited
    else
      val next = queue.head
      val neighbours = next
        .adjacent.filter(_.inBounds(map)).filter(map(_) == map(next)).filterNot(visited.contains)

      flood2(map, neighbours ++ queue.tail , visited + next)

  private def perimeter(points: Set[Point]): Int =
    points.toSeq.map: point =>
     4 - point.adjacent.count(points.contains)
    .sum




  private def sides(points: Set[Point]): Int =
    points.toSeq.map: point =>
      val c = point.adjacent.count(points.contains)
      val up = points.contains(point.up)
      val down = points.contains(point.down)
      val left = points.contains(point.left)
      val right = points.contains(point.right)

      c match
        case 1 => 2
        case 2 =>
          if up && down || left && right then 0
          else 1
        case 3 => 0
        case 4 => 0
        case 0 => 4
    .sum


  def regions(points: Set[Point], map: Map2d[Char], r: Seq[Set[Point]]): Seq[Set[Point]] =
    if points.isEmpty then r
    else
      val region = flood2(map, points.head :: Nil)
      regions(points -- region, map, r :+ region)

  override def solve(input: String) =
    val map = Map2d.fromLines(input.split("\n").filterNot(_.isBlank).toList)

    val r = regions(map.underlying.keys.toSet, map, Seq.empty)

    r.toSeq.map: region =>
      perimeter(region) * region.size
    .sum

    r.toSeq.map: region =>
      val regionMap = Map2d.apply(region.map(_ -> "#").toMap).toString

      println(regionMap)
      println(s"Has ${sides(region)}")
      sides(region) * region.size
    .sum

    System.exit(1)

object Day12:
  def main(args: Array[String]): Unit =
    Day12().run()