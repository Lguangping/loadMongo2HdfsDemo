package fy.lgp.util

import com.alibaba.fastjson.JSON
import fy.lgp.model.LoadMongoProperty
import org.json4s.native.Serialization

/**
  * author : li guang ping
  * description : 配置工具类
  * date : 20-5-18 上午10:53
  **/
object PropertyUtil {


    implicit val formats = org.json4s.DefaultFormats

    def string2Object[T](jsonString: String, clazz : Class[T]): T = {
        JSON.parseObject(jsonString, clazz)
    }

    def object2String[T <: scala.AnyRef](copyHdfsToKafkaProperty: T): String = {
        Serialization.write(copyHdfsToKafkaProperty)
    }

    def checkArgs(args: Array[String], expectedLength: Int): Unit = {
        if (args.length != expectedLength) {
            throw new IllegalArgumentException("参数读取错误, 请检查运行参数  args:" + args.mkString)
        }
    }


    def main(args: Array[String]): Unit = {
        val property = string2Object(
            "{\"mongoUri\":\"uri\",\"hadoopUserName\":\"hive\",\"needProperties\":\"_id,court,related_id\",\"csvPath\":\"hdfspath\",\"filter\":\"a = b\"}"
            , classOf[LoadMongoProperty]
        )
        println(property)
        val str = object2String(property)
        println(str)
    }
}
