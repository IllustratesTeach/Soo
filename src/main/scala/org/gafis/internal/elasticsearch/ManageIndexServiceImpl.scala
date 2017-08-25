package org.gafis.internal.elasticsearch

import org.gafis.service.elasticsearch.{DataAccessService, ManageIndexService}
import org.gafis.utils.JsonUtil
import org.gafis.utils.elasticsearch.Utils

/**
  * Created by yuchen on 2017/8/25.
  */
class ManageIndexServiceImpl(dataAccessService: DataAccessService) extends ManageIndexService{

  private final val URL = "http://192.168.0.1:9002"

  override def createOrUpdateIndex(indexName: String, tableName: String, jsonStr: String): Unit = {
    val httpAddress = URL + "/" + indexName + "/" + tableName
    Utils.restClient(httpAddress,JsonUtil.ListToJSONStr(dataAccessService.getDataFromDataBase()))
  }

  override def deleteIndex(indexName: String, tableName: String, jsonStr: String): Unit = ???
}
