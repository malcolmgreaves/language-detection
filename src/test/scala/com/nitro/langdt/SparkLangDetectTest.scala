package com.nitro.langdt

import com.nitro.IoUtil

import com.nitro.test.SparkTesting
import com.nitro.spark.RddSerializedOps

import scala.reflect.ClassTag

class SparkLangDetectTest extends SparkTesting with Serializable {

  import BundledLangDetectCybozu._
  import IoUtil._
  import LangDetectTest._
  import RddSerializedOps._

  sparkTest("[spark] langauge detection on Chinese, English, French, German, and Russian") {
    assert(testingFiles.size > 0)
    val data = sc.parallelize(testingFiles.map(fi => (fi.getName, slurp(fi))))

    val f =
      Map((x: (String, String)) => {
        val name = x._1
        val text = x._2
        (name, detect(text))
      })

    f(data)
      .collect()
      .foreach({
        case (name, lc) =>
          assert(codeFromTestFileName(name) == lc)
      })
  }

}