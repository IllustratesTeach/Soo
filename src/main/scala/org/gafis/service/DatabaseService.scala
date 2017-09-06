package org.gafis.service


import scala.collection.mutable
/**
  * Created by yuchen on 2017/8/18.
  */
trait DatabaseService {

    def create():Unit

    def save():Int

    def update():Int

    def query():mutable.ListBuffer[mutable.HashMap[String,Any]]

    def bussize():Unit

    def queryMaxSeq(sql: String): Long

    /**
      * 获得当前已经抓取的SEQ值
      *
      * @param sql
      * @return
      */
    def queryCurrentSeq(sql: String): Long

    def querySourcesList():mutable.ListBuffer[mutable.HashMap[String,Any]]

    def updateCurrentSeq(seq: Long,uuid:String):Unit
}
