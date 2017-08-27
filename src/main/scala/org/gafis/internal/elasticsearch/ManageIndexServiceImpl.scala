package org.gafis.internal.elasticsearch

import org.apache.tapestry5.json.JSONObject
import org.gafis.service.elasticsearch.{DataAccessService, ManageIndexService}
import org.gafis.utils.JsonUtil
import org.gafis.utils.elasticsearch.Utils.CallFactory

/**
  * Created by yuchen on 2017/8/25.
  */
class ManageIndexServiceImpl(dataAccessService: DataAccessService) extends ManageIndexService{

  private final val URL = "http://localhost:9200"

  override def putDataToIndex(indexName: String, tableName: String, jsonStr: String): Unit = {

    var httpAddress = ""
    dataAccessService.getDataFromDataBase.foreach{
      t =>
        httpAddress = URL + "/" + indexName + "/" + tableName + "/" + t.get("ID").get.toString +"?pretty"
        val str = CallFactory.call(CallFactory.PUT,httpAddress,JsonUtil.mapToJSONStr(t))
        println(str)
    }
  }

  override def deleteIndex(indexName: String): Unit = {
    val httpAddress = URL + "/" + indexName + "?pretty"
    val resultStr = CallFactory.call(CallFactory.DELETE,httpAddress)
    val jsonObject = new JSONObject(resultStr)
    if(jsonObject.has("acknowledged")){
      if(jsonObject.getBoolean("acknowledged")){
        println("index delete success")
      }else{
        println("index delete failed")
      }
    }
  }

  override def createIndex(indexName: String): Unit = {
    val httpAddress = URL + "/" + indexName +"?pretty"
    val resultStr = CallFactory.call(CallFactory.PUT,httpAddress)
    val jsonObject = new JSONObject(resultStr)
    if(jsonObject.has("acknowledged") && jsonObject.has("shards_acknowledged")){
      if(jsonObject.getBoolean("acknowledged") && jsonObject.getBoolean("shards_acknowledged")){
        println("index create success")
      }else{
        println("index create failed")
      }
    }
  }

  override def searchIndex(indexName: String): String = {
    val httpAddress = URL + "/" + indexName +"/_search?pretty"
    CallFactory.call(CallFactory.GET,httpAddress)
  }

  override def query(indexName: String, jsonStr: String): Unit = {
    val httpAddress = URL + "/" + indexName +"/_search?pretty"
    //println(Utils.restClient_searchIndex(httpAddress),jsonStr)
  }

  override def updateDataToIndex(indexName: String, tableName: String): Unit = {
    val httpAddress = URL + "/" + indexName + "/" + tableName +"/4"
    CallFactory.call(CallFactory.PUT,httpAddress,JsonUtil.mapToJSONStr(dataAccessService.getOneRecord))
  }
}
