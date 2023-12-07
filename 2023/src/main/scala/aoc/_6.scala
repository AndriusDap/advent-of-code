package aoc

def solution6(data: Vector[String]): String = {
  val raceDefinitions = data.map(_.split("\\W+").tail) match
    case Vector(times, durations) => times.zip(durations).map((a, b) => a.toInt -> b.toInt)
    case _ => ???


  val part1 = raceDefinitions.map {
    case (timeLimit, maximumDistance) =>
      (0 until timeLimit).map(distance(_, timeLimit)).count(_ > maximumDistance)
  }.product.toString

  val Vector(part2Time, part2Distance) = data.map(_.split(":").last.replace(" ", "")).map(_.toLong)

  val part2 = (0l until part2Time).map(distance(_, part2Time)).count(_ > part2Distance)
  s"Part1: ${part1}, part2: Time: ${part2Time}, distance ${part2Distance}, answer: ${part2}"
}

def distance(timeHeld: Long, totalTime: Long): Long = {
  Math.max(totalTime - timeHeld, 0) * timeHeld
}

object _6_Test extends Problem(6, InputMode.Test(1), solution6)
object _6_Normal extends Problem(6, InputMode.Normal, solution6)