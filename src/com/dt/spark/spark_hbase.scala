package com.dt.spark

import org.apache.hadoop.hbase.HBaseConfiguration
import org.apache.hadoop.hbase.io.ImmutableBytesWritable
import org.apache.hadoop.hbase.mapred.TableOutputFormat
import org.apache.spark.{SparkConf, SparkContext, rdd}
import org.apache.hadoop.hbase.client.{Put, Result}
import org.apache.hadoop.hbase.util.Bytes
import org.apache.hadoop.mapred.JobConf
object spark_hbase {
  def main(args: Array[String]): Unit = {
    val sparkconf = new SparkConf().setAppName("write some data to hbase").setMaster("local")
    val sc = new SparkContext(sparkconf)

    val tableName = "student"
    val conf = HBaseConfiguration.create()
    val jobConf = new JobConf()
    jobConf.setOutputFormat(classOf[TableOutputFormat])
    jobConf.set(TableOutputFormat.OUTPUT_TABLE,tableName)

    val dataRDD = sc.makeRDD(Array("5,hadoop,B,29","6,spark,G,56"))
    val rdd = dataRDD.map(_.split(",")).map { x =>{
      val put = new Put(Bytes.toBytes(x(0))) //行健的值   Put.add方法接收三个参数：列族,列名,数据
      put.addColumn(Bytes.toBytes("info"), Bytes.toBytes("name"), Bytes.toBytes(x(1))) //info:name列的值
      put.addColumn(Bytes.toBytes("info"), Bytes.toBytes("gender"), Bytes.toBytes(x(2))) //info:gender列的值
      put.addColumn(Bytes.toBytes("info"), Bytes.toBytes("age"), Bytes.toBytes(x(3))) //info:age列的值
      (new ImmutableBytesWritable, put) //转化成RDD[(ImmutableBytesWritable,Put)]类型才能调用saveAsHadoopDataset
    }}
    rdd.saveAsHadoopDataset(jobConf)
  }
}
