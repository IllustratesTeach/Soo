package org.gafis.service.elasticsearch


import scala.collection.mutable
/**
  * Created by yuchen on 2017/8/26.
  */
trait DataAccessService {
    def getDataFromDataBase():mutable.ListBuffer[mutable.HashMap[String,Any]]

    def getOneRecord():mutable.HashMap[String,Any]
}
