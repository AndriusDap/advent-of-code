package advent

import java.io.{BufferedWriter, File, FileWriter}
import scala.util.Try

object Advent:
  def main(args: Array[String]): Unit = {
    println("Merry Crisis!")

    (1 to 24).flatMap {
      day =>
        Try {
          val v = this.getClass.getClassLoader.loadClass(s"advent.solutions.Day$day").asInstanceOf[Class[Solution]]
          v.getConstructor().newInstance()
        }.toOption.map(day -> _)
    }.foreach {
      case (day, solution) =>
      solution.run()
    }
  }


