package com.dt.spark

import org.apache.spark.ml.feature.LabeledPoint
import org.apache.spark.ml.linalg.Vectors
import org.apache.spark.{SparkConf, SparkContext}

object LinearRegression{
  def main(args: Array[String]): Unit = {
    val conf = new SparkConf().setAppName("LinearRregression algorithm").setMaster("local")
    val sc = new SparkContext(conf)
    val data = sc.textFile("data.csv")
//    data.foreach(i => println(i))
    val parseData = data.map{line => val parts = line.split(',')


    }

  }
}
