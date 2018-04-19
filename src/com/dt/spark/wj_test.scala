package com.dt.spark

import org.apache.spark.{SparkConf, SparkContext}
//本地测试
object wj_test {
  def main(args: Array[String]): Unit = {
    val conf = new SparkConf().setAppName("this is a test").setMaster("local")
    val sc = new SparkContext(conf)
    val test = sc.parallelize(List(1,2,3,4)).map(_ * 2).map(i =>(i,1))
    test.foreach(i => println(i._1 + ":" + i._2))
  }

//集群测试
//object wj_test {
//  def main(args: Array[String]): Unit = {
//    val conf = new SparkConf().setAppName("this is a test")
//    val sc = new SparkContext(conf)
//    val test = sc.parallelize(List(1,2,3,4)).map(_ * 2)
//    test.collect.foreach(i => println(i))
//  }

}
