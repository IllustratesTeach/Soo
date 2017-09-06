package org.gafis.service.elasticsearch


import scala.collection.mutable
/**
  * Created by yuchen on 2017/8/26.
  */
trait DataAccessService {

    def queryGafisPerson(sql:String,seq:Long,size:Int):mutable.ListBuffer[mutable.HashMap[String,Any]]

    def queryGafisPersonForInputDataToIndex(sql:String):mutable.ListBuffer[mutable.HashMap[String,Any]]

    def updateSooResourceSeq(seq:Long,uuid:String):Unit
}
