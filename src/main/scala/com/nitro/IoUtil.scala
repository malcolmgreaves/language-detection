package com.nitro

import java.io.File

import scala.io.{ Source, BufferedSource }
import scala.util.Try

object IoUtil {

  val newline: String = System.getProperty("line.separator")

  @inline def slurp(r: BufferedSource): String =
    slurpSafe(r) getOrElse ""

  @inline def slurp(fi: File): String =
    slurpSafe(Source fromFile fi) getOrElse ""

  @inline def slurpSafe(fi: File): Try[String] =
    slurpSafe(Source fromFile fi)

  @inline def slurpSafe(r: BufferedSource): Try[String] =
    Try {
      val br = r.bufferedReader()
      try {
        val sb = new StringBuilder
        var line: String = null
        while ({ line = br.readLine(); line } != null) {
          sb.append(line).append(newline)
        }
        sb.toString().trim()

      } finally {
        br.close()
      }
    }

  def fileAt(base: String)(parts: String*): File =
    fileAt(new File(base))(parts: _*)

  def fileAt(base: File)(parts: String*): File =
    parts.foldLeft(base)((buildingFrom, part) => new File(buildingFrom, part))

}
