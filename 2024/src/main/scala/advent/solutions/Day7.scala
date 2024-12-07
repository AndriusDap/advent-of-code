package advent.solutions

import advent.Solution

class Day7 extends Solution(7):

  def multiply: (Long, Long) => Long = _ * _
  def add: (Long, Long) => Long = _ + _
  def concat: (Long, Long) => Long = (a, b) => (a.toString + b.toString).toLong

  def isValid(target: Long, equation: List[Long], operators: Seq[(Long, Long) => Long]): Boolean =
    equation match
      case Nil => false
      case head :: Nil => head == target
      case head :: neck :: tail => operators.exists: operator =>
          isValid(target, operator.apply(head, neck) :: tail, operators)

  override def solve(input: String) =
    val equations = input.split("\n").map(_.filterNot(_ == ':').split(" ").map(_.toLong).toList).toSeq
    val part1 = equations.filter:
      case goal :: parameters => isValid(goal, parameters, multiply :: add :: Nil)

    val part2 = equations.filter:
      case goal :: parameters => isValid(goal, parameters, multiply :: add :: concat :: Nil)

    (part1.map(_.head).sum, part2.map(_.head).sum)

object Day7:
  def main(args: Array[String]): Unit =
    Day7().run()