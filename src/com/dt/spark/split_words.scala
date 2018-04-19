package com.dt.spark

import org.ansj.splitWord.analysis.{BaseAnalysis, ToAnalysis}
import org.apache.spark.{SparkConf, SparkContext}

object split_words {
  def main(args: Array[String]): Unit = {
    val conf = new SparkConf().setAppName("split_words").setMaster("local")
    val sc = new SparkContext(conf)
    val text = sc.textFile("test2.txt").map(x => ToAnalysis.parse(x))
    text.foreach(word => println(word))
//    val parse = ToAnalysis.parse("我爱北京天安门，天安门上太阳升")
//    println(parse)

  }
}
