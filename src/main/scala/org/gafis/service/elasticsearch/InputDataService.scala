package gafis.service.elasticsearch

/**
  * Created by yuchen on 2017/9/6.
  */
trait InputDataService {

  def inputDataToIndex(indexName:String,sql:String,uuid:String):Unit

  def initSooResource(indexName:String)
}
