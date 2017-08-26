package org.gafis.elasticsearch

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
    service.deleteIndex("testindex","test")
  }

  @Test
  def test_createIndex(): Unit ={
    val service = getService[ManageIndexService]
    service.createIndex("testindex")
  }


  @Test
  def test_searchIndex(): Unit ={
    val service = getService[ManageIndexService]
    service.searchIndex("testindex")
  }

  @Test
  def test_queryIndex(): Unit ={
    val service = getService[ManageIndexService]
    val query = QueryBuilders.boolQuery()
      .must(QueryBuilders.matchQuery("ID", 1))
      .must(QueryBuilders.matchQuery("NAME", "wangwu7"))
    service.query("testindex",Utils.buildESQueryParamByAPI(query))
  }
}
