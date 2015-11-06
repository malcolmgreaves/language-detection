package com.nitro.langdt

import java.io._

import com.nitro.IoUtil

import scala.util.{ Failure, Success, Try }

object LangDetectMain extends App {

  import IoUtil._

  import BundledLangDetectCybozu._

  val inputDir = {
    val x = new File(args(0))
    if (!x.isDirectory)
      throw new Exception(s"input is not directory / exists: ${args(0)}")
    x
  }
  val outputDir = {
    val x = new File(args(1))
    if ((!x.exists() && !x.mkdirs()) || (x.exists() && x.isFile))
      throw new Exception(s"couldn't make output dir: ${args(1)}")
    x
  }
  println(s"reading documents from ${inputDir.getAbsolutePath} and writing detected language to ${outputDir.getAbsolutePath}")

  val textForEach =
    inputDir.listFiles()
      .flatMap(fi => {
        val cleaned = slurp(fi)
        if (cleaned.isEmpty)
          None
        else
          Some((fi.getName, cleaned))
      })
      .toSeq
  println(s"Read and cleaned ${textForEach.length} documents")

  val detectedLangs = textForEach map { case (id, text) => (id, Try { detect(text) }) }

  Try {
    val outputPath = new File(outputDir, "out")
    println(s"Writing all to $outputPath")
    val bw = new BufferedWriter(new FileWriter(outputPath))
    var nSuccess = 0

    detectedLangs
      .foreach {
        case (name, Success(detectedLangCode)) =>
          bw.write(s"$name\t$detectedLangCode\n")
          nSuccess += 1

        case (name, Failure(e)) =>
          println(s"Could not detect langauge for $name, error:\n$e")
      }
    bw.close()
    nSuccess

  } match {
    case Success(nSuccess) =>
      println(s"Successfully detected language in $nSuccess documents out of ${detectedLangs.size}")

    case Failure(e) =>
      println(s"Failure: $e")
      e.printStackTrace()
  }
}
