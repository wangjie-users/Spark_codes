package com.dt.spark

import org.apache.spark.{SparkConf, SparkContext}

/**
  * Created by WangJie on 2018/1/16
  * 使用Scala开发本地spark程序
  */

object WordCount {
  def main(args: Array[String]): Unit = {
    //第1步：
    // 创建spark的配置对象sparkconf，设置spark程序运行时的配置信息
    //例如通过setMaster来设置程序要链接的spark集群的Master的URL,如果设置为local，则代表spark程序在本地运行
    val conf = new SparkConf() //创建SparkConf对象
    conf.setAppName("first spark demo") //设置应用程序的名称，在程序运行的监控界面可以看到名称
    conf.setMaster("local") //local表示程序在本地运行，不需要spark集群;如果在spark集群上运行，则不需要写这一行

    //第2步：
    //创建SparkContext对象
    //SparkContext是spark程序所有功能的唯一入口，无论是采用Scala、Java、Python、R都需要有一个SparkContext实例
    //SparkContext的核心作用：初始化spark应用程序运行所需要的核心组件；同时负责Spark程序往Master注册程序等；
    //SparkContext是整个Spark应用程序中至关重要的一个对象
    val sc = new SparkContext(conf) //创建SparkContext对象，通过传入SparkConf实例来定制Spark运行的具体参数和配置信息

    //第3步：
    //根据具体的数据来源（HDFS、HBase、DB等）通过SparkContext来创建RDD
    //RDD的创建有三种方式：根据外部的数据来源（HDFS）、根据Scala集合、由其他的RDD操作而来
    //数据会被RDD划分成一系列的Partitions，分配到每个Partition的数据属于一个task的处理范畴
    //val lines = sc.textFile("E://spark-2.0.2-bin-hadoop2.6.tgz//spark-2.0.2-bin-hadoop2.6//spark-2.0.2-bin-hadoop2.6//README.md",1) //读取本地文件，并设置为一个Partition
    val lines = sc.textFile("test.txt")
    //此时的lines即原始文件中的每一行的内容

    //第4步：
    //对初始的RDD进行Transformation级别的处理，例如map、filter等高阶函数的编程，来进行具体的数据计算
    //4.1 将每一行的字符串拆分成单个的单词
    //4.2 在单词拆分的基础上，对每个单词实例记数为1，也就是word => (word,1)
    //4.3 在上一步的基础上，统计每个单词在文件中出现的总次数
    val words = lines.flatMap(line => line.split(" ")) //进行单词拆分，并把所有行的拆分结果通过flat合并成为一个大集合
    val pairs = words.map(word => (word,1))
    val wordCounts = pairs.reduceByKey(_ + _) //对相同的key进行value的相加
//    lines.flatMap(_.split(" ")).map((_,1)).reduceByKey(_ + _).foreach(wordNumberPair => println(wordNumberPair._1 + " : " +wordNumberPair._2))
    wordCounts.foreach(wordNumberPair => println(wordNumberPair._1 + " : " +wordNumberPair._2))
    sc.stop()
  }
}
