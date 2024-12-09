package advent.solutions

import advent.Solution

import scala.collection.mutable
import Day9.*

import scala.annotation.tailrec

class Day9 extends Solution(9):

  @tailrec
  private def defragP1(data: Seq[Int], leftCursor: Int, rightCursor: Int, acc: List[Int]): List[Int] =
    if leftCursor == rightCursor then acc ++ Option.when(data(leftCursor) != -1)(data(leftCursor))
    else
      data.lift(leftCursor) match
      case None => acc
      case Some(-1) =>
        data.lift(rightCursor) match
          case Some(-1) => defragP1(data, leftCursor, rightCursor - 1, acc)
          case Some(x) => defragP1(data, leftCursor + 1, rightCursor - 1, acc :+ x)
          case None => acc
      case Some(x) => defragP1(data, leftCursor + 1, rightCursor, acc :+ x)


  @tailrec
  private def defrag(storage: mutable.ArrayBuffer[Storage], current: Int): mutable.ArrayBuffer[Storage] =
    storage.lift(current) match
      case Some(Gap(_)) => defrag(storage, current - 1)
      case Some(File(size, index)) =>
        val into = storage.indexWhere {
          case Gap(x) if x >= size => true
          case _ => false
        }
        if into != -1 && into < current then
          storage.update(current, Gap(size))
          val gap = storage(into).asInstanceOf[Gap]
          storage.update(into, File(size, index))
          if gap.size >= size then
            storage.insert(into+1, Gap(gap.size - size))

        defrag(storage, current - 1)

      case None => storage

  override def solve(t: String) =
    val sizes = t.trim.map(_.toString.toInt)
    val fileMap = sizes.zipWithIndex.map:
      case (x, i) if i % 2 == 0 => File(x, i / 2)
      case (x, _) => Gap(x)

    val p1 = defragP1(fileMap.flatMap(_.expand), 0, fileMap.length - 1, Nil).map(_.toLong).zipWithIndex.map(_ * _).sum


    val p2 = defrag(mutable.ArrayBuffer.from(fileMap), fileMap.length - 1)
    (p1, p2.flatMap(_.expand).map:
      case -1 => 0
      case x => x
    .map(_.toLong).zipWithIndex.map(_ * _).sum)

object Day9:
  sealed trait Storage:
    def expand: Seq[Int]
  case class Gap(size: Int) extends Storage:
    override def expand: Seq[Int] = (0 until size).map(_ => -1)
  case class File(size: Int, fileIndex: Int) extends Storage:
    override def expand: Seq[Int] = (0 until size).map(_ => fileIndex)
  def main(args: Array[String]): Unit =
    Day9().run()