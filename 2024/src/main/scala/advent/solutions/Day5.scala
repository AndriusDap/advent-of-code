package advent.solutions

import advent.Solution

import scala.collection.immutable.{AbstractSeq, LinearSeq}

class Day5 extends Solution(5):
  override def solve(input: String) =
    val rules = input.split("\n").filter(_.contains("|")).map(_.split('|').map(_.toInt)).map(a => a(0) -> a(1))

    val pages = input.split("\n").filterNot(_.contains("|")).filter(_.nonEmpty).map(_.split(",").map(_.toInt))

    def isValid(page: Seq[Int]) =
      rules.forall: (first, second) =>
        (page.indexOf(first), page.indexOf(second)) match
          case (-1, _) => true
          case (_, -1) => true
          case (x, y) if x < y => true
          case _ => false

    def middle(page: Seq[Int]): Int =
      page.drop(1).dropRight(1) match
        case Seq(only) => only
        case other => middle(other)

    val part1 = pages.filter(isValid).map(_.toSeq).map(middle).sum

    def fix(page: Seq[Int]): Seq[Int] =
      if isValid(page) then page
      else
        val (first, second) = rules.find: (first, second) =>
          (page.indexOf(first), page.indexOf(second)) match
            case (-1, _) => !true
            case (_, -1) => !true
            case (x, y) if x < y => !true
            case _ => !false
        .get

        fix(Seq(first) ++ page.filterNot(_ == first))

    pages.filterNot(isValid).map(_.toSeq).map(fix).map(middle).sum
object Day5:
  def main(args: Array[String]): Unit =
    Day5().run()

