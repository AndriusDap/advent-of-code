package advent

import advent.Configuration.*

import java.io.{BufferedWriter, File, FileWriter}
import scala.util.Try

abstract class Solution(day: Int, refetchSamples: Boolean = false):
  def solve(input: String): Any

  def run(onlyFinal: Boolean = false): Unit =
    println(s"Running day $day")

    val finalInput = fetchInput()
    val samples = fetchSamples()

    def doIt(input: String, label: String) =
      val start = System.currentTimeMillis()
      val answer = Try:
        solve(input)
      .toEither
      val end = System.currentTimeMillis()

      println(f"[$label\t]\tDay #$day runtime   ${end - start}ms, answer: ${answer}")
      answer.left.foreach(_.printStackTrace())


    if !onlyFinal then
      samples.zipWithIndex.foreach: (sample, index) =>
        println(sample)
        doIt(sample, s"Ex. #$index")

    doIt(finalInput, "Final")

  val filePrefix = s"$cacheDir/$year/$day"

  def fetchSamples(): List[String] =
    val samplePath = s"$cacheDir/$year/$day/samples/"
    val sampleFolder = new File(samplePath)
    sampleFolder.mkdirs()

    if refetchSamples || sampleFolder.listFiles().isEmpty then
      val response = requests.get(s"https://adventofcode.com/$year/day/$day", headers =
        "cookie" -> s"session=$session" :: Nil).text()

      def extractSamples(start: Int = 0): List[String] =
        val startToken = "<pre><code>"
        val endToken = "</code></pre>"

        val sampleStart = response.indexOf(startToken, start)
        if sampleStart == -1 then Nil
        else
          val sampleEnd = response.indexOf(endToken, sampleStart)
          response.slice(sampleStart + startToken.length, sampleEnd) :: extractSamples(sampleEnd)

      val samples = extractSamples()

      samples.zipWithIndex.foreach: (sample, index) =>
          val writer = new BufferedWriter(new FileWriter(sampleFolder.getAbsolutePath + "/" + index))
          writer.write(sample)
          writer.close()

      samples
    else
      sampleFolder
        .listFiles()
        .sortBy(_.getAbsolutePath)
        .map: file =>
          val source = io.Source.fromFile(file)
          val sample = source.getLines().mkString("\n")
          source.close()
          sample
        .toList


  def fetchInput() =
    val filePath = s"$filePrefix.input"
    Try:
      val source = scala.io.Source.fromFile(s"$filePrefix.input")
      val content = source.mkString
      source.close()
      content
    .getOrElse:
      val response = requests.get(s"https://adventofcode.com/$year/day/$day/input", headers =
        "cookie" -> s"session=$session" :: Nil).text()
      new File(s"$cacheDir/$year/").mkdirs()
      val writer = new BufferedWriter(new FileWriter(filePath))
      writer.write(response)
      writer.close()
      response
