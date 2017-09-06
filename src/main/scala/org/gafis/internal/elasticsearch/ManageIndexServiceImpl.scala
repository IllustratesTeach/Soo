package org.gafis.internal.elasticsearch


import org.apache.tapestry5.json.JSONObject
import org.gafis.service.elasticsearch.{DataAccessService, ManageIndexService}
import org.gafis.utils.{Constant, JsonUtil}
import org.gafis.utils.elasticsearch.Utils.CallFactory
import org.slf4j.LoggerFactory

/**
  * Created by yuchen on 2017/8/25.
  */
class ManageIndexServiceImpl(dataAccessService: DataAccessService) extends ManageIndexService{

  val logger = LoggerFactory getLogger getClass

  override def putDataToIndex(indexName: String,id:String,jsonStr: String): Unit = {
    val str = CallFactory.call(CallFactory.PUT,getRequestURL(Constant.PUT_DATA_TO_INDEX,indexName,indexName,id),jsonStr)
    logger.info("ID:" + id +" Data input success, information:" + str)
  }

  override def deleteIndex(indexName: String): Unit = {

    val resultStr = CallFactory.call(CallFactory.DELETE,getRequestURL(Constant.DELETE_INDEX,indexName))
    val jsonObject = new JSONObject(resultStr)
    if(jsonObject.has("acknowledged") && jsonObject.getBoolean("acknowledged")){
      logger.info("index delete success")
    } else{
        logger.warn("index delete failed,info:{}",resultStr)
        throw new Exception("index delete failed,info:" + resultStr)
    }
  }

  override def createIndex(indexName: String): Unit = {

    val resultStr = CallFactory.call(CallFactory.PUT,getRequestURL(Constant.CREATE_INDEX,indexName))
    val jsonObject = new JSONObject(resultStr)
    if(jsonObject.has("acknowledged") && jsonObject.getBoolean("acknowledged")){
          logger.info("index create success")
    }else{
      logger.warn("index create failed,info:{}",resultStr)
      throw new Exception("index create failed,info:" + resultStr)
    }
  }

  override def searchIndex(indexName: String): String = {
    val resultStr = CallFactory.call(CallFactory.GET,getRequestURL(Constant.SEARCH_INDEX,indexName))
    val jsonObject = new JSONObject(resultStr)
    if(jsonObject.has("hits")){
      val _jsonObject = new JSONObject(jsonObject.get("hits").toString)
      if(_jsonObject.has("total")){
        logger.info("索引已存在")
      }
    }else{
      throw new Exception("search index failed:" + resultStr)
    }
    resultStr
  }

  override def query(indexName: String, jsonStr: String): Unit = {
    //println(Utils.restClient_searchIndex(httpAddress),jsonStr)
  }

  override def updateDataToIndex(indexName: String,id:String,jsonStr: String): Unit = {
    CallFactory.call(CallFactory.PUT
      ,getRequestURL(Constant.UPDATE_DATA_TO_INDEX,indexName,indexName,id)
      ,jsonStr)
  }

  private def getRequestURL(accessType:String,indexName:String,tableName:String = Constant.EMPTY,id:String = Constant.EMPTY): String ={

    var httpAddress = ""
    accessType match{
      case Constant.PUT_DATA_TO_INDEX | Constant.UPDATE_DATA_TO_INDEX=>
        httpAddress = Constant.webConfig.get.elasticsearchurl + Constant.URL_SEPARATOR + indexName + Constant.URL_SEPARATOR + tableName + Constant.URL_SEPARATOR + id + Constant.JSON_PATTER_RESPONSE
      case Constant.CREATE_INDEX | Constant.DELETE_INDEX=>
        httpAddress = Constant.webConfig.get.elasticsearchurl + Constant.URL_SEPARATOR + indexName + Constant.JSON_PATTER_RESPONSE
      case Constant.SEARCH_INDEX =>
        httpAddress = Constant.webConfig.get.elasticsearchurl + Constant.URL_SEPARATOR + indexName + Constant.URL_SEPARATOR + Constant.JSON_PATTER_SEARCH_INDEX_RESPONSE
      case Constant.QUERY =>

    }
    httpAddress
  }
}
