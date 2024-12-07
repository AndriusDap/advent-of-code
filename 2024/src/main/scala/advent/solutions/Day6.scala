package advent.solutions

import advent.Solution

import scala.annotation.tailrec
import scala.collection.parallel.CollectionConverters.*

class Day6 extends Solution(6):
  override def solve(input: String) =
    val map = input.split("\n").toSeq.map(_.trim.toCharArray.map(_.toString).toSeq)

    def findAll(x: String) = map.zipWithIndex
      .flatMap: (row, rowNo) =>
        row.zipWithIndex.flatMap {
          case (`x`, i) => Some((i, rowNo))
          case _ => None
        }

    val obstacles = findAll("#")
    val guard = findAll("^").head
    val limit = (map.head.length, map.length)

    def outOfBounds(guard: (Int, Int)) = guard._1 < 0 || guard._2 < 0 || guard._1 >= limit._1 || guard._2 >= limit._2

    def up(x: Int, y: Int) = ((x, y - 1), 1)
    def down(x: Int, y: Int) = ((x, y + 1), 2)
    def left(x: Int, y: Int) = ((x - 1, y), 3)
    def right(x: Int, y: Int) = ((x + 1, y), 4)

    val obstacleSet = obstacles.toSet

    @tailrec
    def loop(
              obstacles: Set[(Int, Int)],
              guard: (Int, Int),
              visited: Set[((Int, Int), Int)],
              route: List[(Int, Int) => ((Int, Int), Int)]
            ): Option[Set[((Int, Int), Int)]] =
      val (nextStep, direction) = route.head(guard._1, guard._2)
      if outOfBounds(nextStep) then Some(visited)
      else if visited.contains(nextStep -> direction) then None
      else if obstacles.contains(nextStep) then loop(obstacles, guard, visited, route.tail :+ route.head)
      else loop(obstacles, nextStep, visited + (nextStep -> direction), route)

    val rotations = up :: right :: down :: left :: Nil

    val path = loop(obstacleSet, guard, Set(guard -> 1), rotations).get.map(_._1)
    val loops = path.filterNot(_ == guard).par.count: potentialSpot =>
      loop(obstacleSet + potentialSpot, guard, Set(guard -> 1), rotations).isEmpty

    (path.size, loops)

object Day6:
  def main(args: Array[String]): Unit =
    Day6().run()