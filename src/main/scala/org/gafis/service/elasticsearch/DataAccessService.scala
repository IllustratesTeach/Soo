package org.gafis.service.elasticsearch


import scala.collection.mutable
/**
  * Created by yuchen on 2017/8/26.
  */
trait DataAccessService {


    def queryMaxSeq(sql: String): Long

    /**
      * 获得当前已经抓取的SEQ值
      *
      * @param sql
      * @return
      */
    def queryCurrentSeq(sql: String): Long

    def querySourcesList():mutable.ListBuffer[mutable.HashMap[String,Any]]

    def updateSooResourceFlag(uuid:String):Unit




    def queryGafisPerson(sql:String,seq:Long,size:Int):mutable.ListBuffer[mutable.HashMap[String,Any]]

    def queryGafisPersonForInputDataToIndex(sql:String):mutable.ListBuffer[mutable.HashMap[String,Any]]

    def updateSooResourceSeq(seq:Long,uuid:String):Unit

    def initSooResource(indexName:String,sql:String):String
}
