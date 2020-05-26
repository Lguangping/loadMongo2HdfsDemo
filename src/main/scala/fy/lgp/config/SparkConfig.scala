package fy.lgp.config

import fy.lgp.registrator.AvroKryoRegistrator

/**
  * author : li guang ping
  * description : spark配置
  * date : 20-5-19 上午10:11
  **/
object SparkConfig {
    object Key{
        val SPARK_SERIALIZER: String = "spark.serializer"
        val SPARK_KRYO_REGISTRATOR: String = "spark.kryo.registrator"
        val SPARK_LOCALITY_WAIT_PROCESS: String = "spark.locality.wait.process"
        val SPARK_LOCALITY_WAIT_NODE: String = "spark.locality.wait.node"
        val SPARK_MONGODB_INPUT_URI: String = "spark.mongodb.input.uri"
    }




    val LOCAL_2: String = "local[2]"
    val YARN_CLUSTER: String = "yarn-cluster"
    val SECOND_30: String = "30"
    val KRYO_SERIALIZER: String = "org.apache.spark.serializer.KryoSerializer"
    val AVRO_KRYO_REGISTRATOR: String = classOf[AvroKryoRegistrator].getName
}
