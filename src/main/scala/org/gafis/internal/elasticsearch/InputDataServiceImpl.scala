package gafis.internal.elasticsearch

import gafis.service.elasticsearch.InputDataService
import org.gafis.service.elasticsearch.{DataAccessService, ManageIndexService}
import org.gafis.utils.JsonUtil

/**
  * Created by yuchen on 2017/9/6.
  */
class InputDataServiceImpl(dataAccessService: DataAccessService,manageIndexService: ManageIndexService) extends InputDataService{
  override def inputDataToIndex(indexName:String,sql: String,uuid:String): Unit = {
    val listResult = dataAccessService.queryGafisPersonForInputDataToIndex(sql)
    listResult.foreach{
      t =>
        manageIndexService.putDataToIndex(indexName,t.get("ID").get.toString,JsonUtil.mapToJSONStr(t))
        dataAccessService.updateSooResourceSeq(t.get("SEQ").get.toString.toLong,uuid)
    }
  }
}
