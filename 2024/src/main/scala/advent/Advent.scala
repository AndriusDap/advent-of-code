package advent

import scala.util.Try

object Advent:
  def main(args: Array[String]): Unit =
    println("Merry Crisis!")

    (1 to 24).flatMap: day =>
      Try:
        val v = this.getClass.getClassLoader.loadClass(s"advent.solutions.Day$day").asInstanceOf[Class[Solution]]
        v.getConstructor().newInstance()
      .toOption
    .foreach: solution =>
      solution.run()

