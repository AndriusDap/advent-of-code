package advent.solutions

import advent.Solution

class Day5 extends Solution(5):
  def passesRule(rule: (Int, Int), page: Seq[Int]) =
    (page.indexOf(rule._1), page.indexOf(rule._2)) match
      case (_, -1) => true
      case (x, y) if x < y => true
      case _ => false

  def middle(page: Seq[Int]): Int = page(page.size / 2)

  override def solve(input: String) =
    val rules = input.split("\n").filter(_.contains("|")).map(_.split('|').map(_.toInt)).map(a => a(0) -> a(1))
    val pages = input.split("\n").filterNot(_.contains("|")).filter(_.nonEmpty).map(_.split(",").map(_.toInt))

    def isValid(page: Seq[Int]) = rules.forall(passesRule(_, page))

    def fix(page: Seq[Int]): Seq[Int] =
      if isValid(page) then page
      else
        val (first, _) = rules.find(!passesRule(_, page)).get
        fix(Seq(first) ++ page.filterNot(_ == first))

    val part1 = pages.filter(isValid).map(_.toSeq).map(middle).sum
    val part2 = pages.filterNot(isValid).map(_.toSeq).map(fix).map(middle).sum
    (part1, part2)

object Day5:
  def main(args: Array[String]): Unit =
    Day5().run()