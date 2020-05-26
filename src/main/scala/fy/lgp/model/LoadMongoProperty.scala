package fy.lgp.model

/**
  * author : li guang ping
  * description : 复制mongo的配置类
  * date : 20-5-18 上午10:45
  **/
case class LoadMongoProperty(
                                    mongoUri: String, // mongo地址
                                    hadoopUserName: String, // hadoop用户名
                                    needProperties: String, // 需要的字段, 以,分隔
                                    csvPath: String, // 要写的csv地址
                                    filter: String // 过滤条件

                            )
