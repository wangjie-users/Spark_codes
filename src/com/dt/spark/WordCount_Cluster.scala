package com.dt.spark

import org.apache.spark.{SparkConf, SparkContext}

/**
  * Created by WangJie on 2018/1/16
  *在集群上运行spark程序
  */

object WordCount_Cluster {
  def main(args: Array[String]): Unit = {
    val conf = new SparkConf() //
    conf.setAppName("first spark demo")
//    conf.setMaster("local") //如果在spark集群上运fgf行，则不需要写这一行，在提交的时候手动配置
//    conf.setMaster("spark://Master:7077")
    val sc = new SparkContext(conf)
    val lines = sc.textFile("/user/wangjie/test2.txt")
    val words = lines.flatMap(line => line.split(" ")) //进行单词拆分，并把所有行的拆分结果通过flat合并成为一个大集合
    val pairs = words.map(word => (word,1))
    val wordCounts = pairs.reduceByKey(_ + _) //对相同的key进行value的相加
//    lines.flatMap(_.split(" ")).map((_,1)).reduceByKey(_ + _).foreach(wordNumberPair => println(wordNumberPair._1 + " : " +wordNumberPair._2))
    wordCounts.collect.foreach(wordNumberPair => println(wordNumberPair._1 + " : " +wordNumberPair._2)) //在集群中需要用collect才能展现
    sc.stop()
  }
}
