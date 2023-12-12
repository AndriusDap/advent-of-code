package aoc

import scala.util.Using
import scala.concurrent.duration.*

enum InputMode {
  case Test(subProblemNo: Int)
  case Normal
}

case class RunFn(name: String, fn: Vector[String] => String)

case class ToRun(functions: Vector[RunFn])
object ToRun {
  def apply(functions: RunFn*): ToRun = ToRun(functions.toVector)

  given Conversion[Vector[String] => String, ToRun] with {
    def apply(f: Vector[String] => String): ToRun = ToRun.apply(RunFn("default", f))
  }
}

trait Problem(problemNo: Int, inputMode: InputMode, run: ToRun) {
  def name: String = {
    val fileName = inputMode match {
      case InputMode.Test(subProblemNo) => s"${problemNo}_${subProblemNo}_test"
      case InputMode.Normal => s"${problemNo}_normal"
    }
    
    s"data/$fileName.txt"
  }
  
  def main(args: Array[String]): Unit = {
    val data = Using(io.Source.fromFile(name))(_.getLines().toVector).get

    val outputs = run.functions.map { runFn =>
      println(s"Running ${runFn.name}...")
      val start = System.currentTimeMillis()
      val result = runFn.fn(data)
      val end = System.currentTimeMillis()
      val time = (end - start).millis
      val output = s"$name (${runFn.name}) done in $time: \n$result"
      //println(output)
      //println()
      output
    }

    println()
    println(outputs.mkString("\n"))
  }
}
