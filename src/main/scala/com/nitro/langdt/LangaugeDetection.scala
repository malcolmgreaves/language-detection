package com.nitro.langdt

import com.cybozu.labs.langdetect.DetectorFactory
import com.nitro.IoUtil.slurpSafe

import scala.io.Source
import scala.util.{ Failure, Success, Try }

object LangaugeDetection extends Serializable {

  /** A piece of text. */
  type Text = String

  /** Classifies text to one langauge. */
  type Detector = Text => LangCode

  /** Outputs the probability that a piece of text belongs to a language, for all languages. */
  type Estimator = Text => Seq[(LangCode, Double)]

  /** A language detector that can say some text has no detectable language. */
  type SafeDetector = Text => Option[LangCode]

  /**
   * Useful when attempting to detect the language of text that we're not quite sure about.
   * For instance, what's the correct response for empty Text? A `SafeDetector` will always
   * result in either a valid language code or None, signifying that there was no
   * detected language.
   */
  def makeSafe(d: Detector): SafeDetector =
    (s: Text) => Try(d(s)).toOption
}

object BundledLangDetectCybozu extends Serializable {

  import LangaugeDetection._

  import scala.collection.JavaConverters._

  def loadJsonProfileFromJar(largeProfile: Boolean, language: LangCode): Try[String] =
    slurpSafe(
      Source.fromInputStream(
        classOf[DetectorFactory].getResourceAsStream(s"""/profiles.${if (largeProfile) "lg" else "sm"}/${language.code}""")
      )
    )

  lazy val (detect, estimate): (Detector, Estimator) = {

    val doLargeProf = true

    val detectorFactory = new DetectorFactory()

    detectorFactory.loadProfile(
      LangCode.allCodes
      .map(lc => {
        loadJsonProfileFromJar(doLargeProf, lc) match {

          case s @ Success(_) =>
            s

          case f @ Failure(e) =>
            System.err.println(s"""could not load bundled language $lc (${lc.fullName}) for (large bundle? $doLargeProf)\n$e\n${e.getStackTrace.map(_.toString).mkString("\t\t\n")}\n""")
            f
        }
      })
      .flatMap(_.toOption)
      .toList
      .asJava
    )

    if (!detectorFactory.getLangList.asScala.forall(code => LangCode.str2code.contains(code)))
      throw new IllegalStateException(s"""All loaded langauges are not in known langauge codes: ${detectorFactory.getLangList.asScala.mkString(",")}""")

    (
      (text: String) => {
        val d = detectorFactory.create()
        d.append(text)
        val classifiedCode = d.detect()
        LangCode(classifiedCode).get
      },
      (text: String) => {
        val d = detectorFactory.create()
        d.append(text)
        d.getProbabilities
          .asScala
          .map(l => (LangCode(l.lang).get, l.prob))
          .toSeq
      }
    )
  }

  lazy val safeDetector: SafeDetector = makeSafe(detect)
}