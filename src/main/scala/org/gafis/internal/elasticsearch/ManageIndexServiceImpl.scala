package org.gafis.internal.elasticsearch

import org.elasticsearch.index.query.QueryBuilders
import org.gafis.service.elasticsearch.{DataAccessService, ManageIndexService}
import org.gafis.utils.JsonUtil
import org.gafis.utils.elasticsearch.Utils

/**
  * Created by yuchen on 2017/8/25.
  */
class ManageIndexServiceImpl(dataAccessService: DataAccessService) extends ManageIndexService{

  private final val URL = "http://localhost:9200"

  override def putDataToIndex(indexName: String, tableName: String, jsonStr: String): Unit = {
    val httpAddress = URL + "/" + indexName + "/" + tableName +"?pretty"
    dataAccessService.getDataFromDataBase.foreach{
      t =>
        val str = Utils.restClient_putDataToIndex(httpAddress,JsonUtil.mapToJSONStr(t))
        println(str)
    }
  }

  override def deleteIndex(indexName: String, tableName: String): Unit = {
    val httpAddress = URL + "/" + indexName + "?pretty"
    println(Utils.restClient_delIndex(httpAddress))
  }

  override def createIndex(indexName: String): Unit = {
    val httpAddress = URL + "/" + indexName +"?pretty"
    println(Utils.restClient_createIndex(httpAddress,""))
  }

  override def searchIndex(indexName: String): Unit = {
    val httpAddress = URL + "/" + indexName +"/_search?pretty"
    println(Utils.restClient_searchIndex(httpAddress))
  }

  override def query(indexName: String, jsonStr: String): Unit = {
    val httpAddress = URL + "/" + indexName +"/_search?pretty"
    println(Utils.restClient_searchIndex(httpAddress),jsonStr)
  }
}
