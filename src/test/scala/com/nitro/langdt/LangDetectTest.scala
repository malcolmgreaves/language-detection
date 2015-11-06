package com.nitro.langdt

import com.nitro.IoUtil
import org.scalatest.FunSuite

class LangDetectTest extends FunSuite {

  import LangDetectTest._
  import IoUtil._
  import BundledLangDetectCybozu._

  test("langauge detection on Chinese, English, French, German, and Russian") {
    assert(testingFiles.size > 0)
    testingFiles
      .foreach(fi =>
        assert(codeFromTestFileName(fi.getName) == detect(slurp(fi))))
  }

  test("safe detect on error cases as well as normal ones") {
    assert(safeDetector("") == None)
    assert(safeDetector("Wikipedia is a free-access, free-content Internet encyclopedia, supported and hosted by the non-profit Wikimedia Foundation. Those who can access the site can edit most of its articles, with the expectation that they follow the website's policies.") == Some(En))
  }

}

object LangDetectTest extends Serializable {

  def testingFiles =
    IoUtil.fileAt(".")("src", "test", "resources", "testing_documents")
      .listFiles()
      .toSeq

  def codeFromTestFileName(name: String): LangCode = {
    val lcase = name.toLowerCase
    if (lcase.startsWith("chinese"))
      Zhcn
    else if (lcase.startsWith("english"))
      En
    else if (lcase.startsWith("french"))
      Fr
    else if (lcase.startsWith("german"))
      De
    else if (lcase.startsWith("russian"))
      Ru
    else
      throw new Exception(s"unknown language for name: $name")
  }

}
