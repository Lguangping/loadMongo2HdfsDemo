package fy.lgp

import fy.lgp.config.SparkConfig
import fy.lgp.model.LoadMongoProperty
import fy.lgp.util.{Constant, PropertyUtil}
import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.types.{StringType, StructField, StructType}

/**
  * author : li guang ping
  * description :  加载mongo数据写入hdfs
  * date : 20-5-25 下午7:17
  **/
object LoadMongo2Csv {
    val PRODUCT_NAME: String = "LoadMongo2Csv"
    val SQL_PREFIX: String = "select * from "
    val WHERE: String = " where "


    def main(args: Array[String]): Unit = {
        val property: LoadMongoProperty = propertyByArgs(args)
        //只加载需要读取的字段，减少内存的消耗，并用sparksql的方式进行数据融合，减少资源消耗并提升开发效率以及代码简洁性
        //对想要获取的字段切割后，创建structFields，然后创建StructType（DF的schema）
        val schema = StructType(
            property.needProperties
                    .split(Constant.COMMA)
                    .map(
                        fieldName => StructField(fieldName, StringType, true)
                    )
        )

        val name = property.hadoopUserName
        // 本地有效, 集群运行此设置无用
        fy.lgp.config.HadoopConfig.initHadoopUserNameEnv(name)

        val mongoUri = property.mongoUri
        val sparkSession = SparkSession
                .builder()
                // 本地运行, 集群注释
                //                 .master(SparkConfig.LOCAL_2)
                .appName(PRODUCT_NAME + Constant.UNDERLINE + getDbTable(mongoUri))
                // 本地运行, 需要配置hadoop的配置文件, 不然无法识别hdfs:///的路径
                .config(SparkConfig.Key.SPARK_MONGODB_INPUT_URI, mongoUri)
                .getOrCreate()

        val sqlContext = sparkSession.sqlContext


        //利用上面自定义的DF的schema 和配置信息读取momngo指定表里的指定列，对读取的列声明成虚拟表，可以进一步操作，如有需要可以转换成RDD进行操作
        val load = sparkSession
                .read
                .schema(schema)
                .format(Constant.COM_MONGODB_SPARK_SQL)
                .load
        load.createOrReplaceTempView(Constant.MONGO_TABLE)

        val result = sqlContext.sql(
            sql(property)
        )
        result.coalesce(1)
                .write
                .mode(Constant.APPEND)
                .option(Constant.HEADER, true)
                .option(Constant.QUOTE, Constant.SINGLE_QUOTATION_MARK)
                .option(Constant.NULL_VALUE, Constant.SPACE)
                .option(Constant.INFER_SCHEMA, true)
                .csv(property.csvPath)

        sparkSession.stop()

    }

    def propertyByArgs(args: Array[String]): LoadMongoProperty = {
        PropertyUtil.checkArgs(args, 1)
        PropertyUtil.string2Object(args(0), classOf[LoadMongoProperty])
    }

    def getDbTable(uri: String): String = {
        uri.substring(
            uri.lastIndexOf(Constant.POLYLINE)
        )
    }

    def sql(loadMongoProperty: LoadMongoProperty): String = {
        var sql: String = SQL_PREFIX + Constant.MONGO_TABLE
        val filter = loadMongoProperty.filter
        if (!filter.isEmpty) {
            val trim = filter.trim
            sql += WHERE + trim
        }
        sql
    }

}
