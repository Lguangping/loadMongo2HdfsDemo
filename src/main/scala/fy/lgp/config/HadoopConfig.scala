package fy.lgp.config

import org.slf4j.{Logger, LoggerFactory}

/**
  * author : li guang ping
  * description : hadoop配置
  * date : 20-5-19 上午10:11
  **/
object HadoopConfig {
    protected lazy val logger: Logger = LoggerFactory.getLogger(getClass.getName)

    val HADOOP_USER_NAME: String = "HADOOP_USER_NAME"

    object Key {
        val DEFAULT_FS: String = "fs.defaultFS"
        val NAMESERVICES: String = "dfs.nameservices"
        val HA_NAMENODES_PROD: String = "dfs.ha.namenodes.prod"
        val NAMENODE_RPC_1: String = "dfs.namenode.rpc-address.prod.nn2"
        val NAMENODE_RPC_2: String = "dfs.namenode.rpc-address.prod.nn1"
        val FAILOVER_PROXY: String = "dfs.client.failover.proxy.provider.prod"
    }

    def initHadoopUserNameEnv(hadoopName: String): Unit = {
        System.setProperty(HADOOP_USER_NAME, hadoopName)
    }
}
