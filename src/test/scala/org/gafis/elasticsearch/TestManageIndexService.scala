package org.gafis.elasticsearch

import org.apache.tapestry5.json.JSONObject
import org.elasticsearch.index.query.QueryBuilders
import org.gafis.BaseTestCase
import org.gafis.service.elasticsearch.ManageIndexService
import org.gafis.utils.elasticsearch.Utils
import org.junit.Test

/**
  * Created by yuchen on 2017/8/26.
  */
class TestManageIndexService extends BaseTestCase{

  /**
    *  q-url -----http://localhost:9200/testindex/_search
    *  create-index ------http://localhost:9200/testindex?pretty
    */
  @Test
    def test_createOrUpdateIndex(): Unit ={
      val service = getService[ManageIndexService]
      service.putDataToIndex("testindex","test","")
    }

  @Test
  def test_deleteIndex(): Unit ={
    val service = getService[ManageIndexService]
    service.deleteIndex("testindex")
  }

  @Test
  def test_createIndex(): Unit ={
    val service = getService[ManageIndexService]
    service.createIndex("testindex")
  }


  @Test
  def test_isExistIndex(): Unit ={
    try{
      val service = getService[ManageIndexService]
      val strJson = service.searchIndex("testindex")
      val jsonObject = new JSONObject(strJson)
      if(jsonObject.has("hits")){
        val _jsonObject = new JSONObject(jsonObject.get("hits").toString)
        if(_jsonObject.has("total")){
          if(_jsonObject.get("total").toString.toInt > 0){
            println("索引已经存在")
          }
        }
      }else{
        println("索引不存在")
      }
    }catch{
      case e:Throwable =>
        println(e.getMessage)
    }
  }




  @Test
  def test_SearchIndex(): Unit ={
    try{
      val service = getService[ManageIndexService]
      val strJson = service.searchIndex("testindex")
      println(strJson)
    }catch{
      case e:Throwable =>
        println(e.getMessage)
    }
  }

  @Test
  def test_queryIndex(): Unit ={
    val service = getService[ManageIndexService]
    /*val query = QueryBuilders.boolQuery()
      .must(QueryBuilders.matchQuery("ID", 1))
      .must(QueryBuilders.matchQuery("NAME", "wangwu7"))
    service.query("testindex",Utils.buildESQueryParamByAPI(query))*/
  }

  @Test
  def test_updateDataToIndex():Unit ={
    val service = getService[ManageIndexService]
    service.updateDataToIndex("testindex","test")
  }
}
