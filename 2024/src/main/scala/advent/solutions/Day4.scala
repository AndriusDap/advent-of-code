package advent.solutions

import advent.Solution

class Day4 extends Solution(4):
  override def solve(input: String) =
    def part1 =
      val awesome =
        "XMAS".r :: "SAMX".r :: Nil

      val permutations = rotations(input) ++ skew(input).map(rotate)

      permutations.map: permutation =>
        awesome.map(_.findAllIn(permutation).size).sum
      .sum

    "(?P<prefix>.*?)M.M.*?\n(?P=<prefix"

    def part2 =
      def loop(input: String, count: Int): Int =
        val lines = input.split("\n")
        if input.contains("M") && lines(0).length > 2 then
          def innerLoop(input: String, count: Int = 0): Int =
            val lines = input.split("\n")
            if lines.length > 2 then
              val delta = (lines(0)(0), lines(0)(2), lines(1)(1), lines(2)(0), lines(2)(2)) match
                case ('M', 'S', 'A', 'M', 'S') => 1
                case ('M', 'M', 'A', 'S', 'S') => 1
                case ('S', 'S', 'A', 'M', 'M') => 1
                case ('S', 'M', 'A', 'S', 'M') => 1
                case _ => 0
              innerLoop(dropTop(input), count + delta)
            else count
          loop(trim(input), count + innerLoop(input))
        else count

      loop(input, 0)

    (part1, part2)

  def trim(input: String): String =
    input.split("\n").map(_.drop(1)).mkString("\n")

  def dropTop(input: String): String =
    input.split("\n").tail.mkString("\n")

  def rotate(input: String): String =
    input.split("\n").map(_.toCharArray).transpose.map(new String(_)).mkString("\n")

  def rotations(input: String): Seq[String] =
    rotate(input) :: rotate(rotate(input)) :: Nil

  def skew(input: String): Seq[String] =
    val lines = input.split("\n")
    lines.zipWithIndex.map: (line, index) =>
      "." * index + line + "." * (lines.length - index)
    .mkString("\n") :: lines.zipWithIndex.map: (line, index) =>
      "." * (lines.length - index) + line + "." * index
    .mkString("\n") :: Nil

object Day4:
  def main(args: Array[String]): Unit =
    Day4().run()

