package org.gafis.internal.elasticsearch

import java.sql.{ PreparedStatement}
import java.util.UUID
import javax.sql.DataSource

import gafis.utils.CommonUtils
import org.gafis.service.elasticsearch.DataAccessService

import scala.collection.mutable
import scala.collection.mutable.ListBuffer
/**
  * Created by yuchen on 2017/8/26.
  */
class DataAccessServiceImpl(implicit val dataSource: DataSource) extends DataAccessService{

  override def queryGafisPerson(sql:String,seq:Long,size:Int): ListBuffer[mutable.HashMap[String, Any]] = {
    val resultList = new mutable.ListBuffer[mutable.HashMap[String,Any]]
    val conn = dataSource.getConnection
    val sql_final = sql + " WHERE  p.seq >=? AND p.seq <=? "
    val st = conn.prepareStatement(sql_final)
    st.setLong(1,seq)
    st.setLong(2,seq + size.toLong)
    val rs = st.executeQuery
    try{
      while(rs.next()){
        resultList.append(CommonUtils.getResultMap(rs))
      }
    }finally {
      st.close
      conn.close
    }
    resultList
  }

  override def queryGafisPersonForInputDataToIndex(sql: String): ListBuffer[mutable.HashMap[String, Any]] = {
    val resultList = new mutable.ListBuffer[mutable.HashMap[String,Any]]
    val conn = dataSource.getConnection
    val st = conn.prepareStatement(sql + "  ORDER BY p.seq ASC")
    val rs = st.executeQuery
    try{
      while(rs.next()){
        resultList.append(CommonUtils.getResultMap(rs))
      }
    }finally {
      st.close
      conn.close
    }
    resultList
  }

  override def updateSooResourceSeq(seq: Long,uuid:String): Unit = {
    val conn = dataSource.getConnection
    var flag = -1
    val sql = "update SOO_RESOURCE set SEQ=? where UUID=?"
    var ps:PreparedStatement = null
    try{
      ps = conn.prepareStatement(sql)
      ps.setLong(1,seq)
      ps.setString(2,uuid)
      flag = ps.executeUpdate
      if(flag <=0){
        throw new Exception("update data failed,uuid:" + uuid)
      }
      conn.commit
    }finally {
      ps.close
      conn.close
    }
  }

  override def initSooResource(indexName: String, sql: String): String = {
    val conn = dataSource.getConnection
    var flag = -1
    val uuid = UUID.randomUUID.toString.replace("-","")
    val sql_final = "INSERT INTO SOO_RESOURCE (UUID,NAME,SQL,CREATETIME,FLAG,VERSION,SEQ) VALUES (?,?,?,sysdate,?,?,?)"
    var ps:PreparedStatement = null
    try{
      ps = conn.prepareStatement(sql_final)
      ps.setString(1,uuid)
      ps.setString(2,indexName)
      ps.setString(3,sql)
      ps.setInt(4,0)
      ps.setInt(5,1)
      ps.setLong(6,0)
      flag = ps.executeUpdate
      if(flag <=0){
        throw new Exception("initSooResource failed,indexName:" + indexName)
      }
      conn.commit
      uuid
    }finally {
      ps.close
      conn.close
    }
  }


  override def queryMaxSeq(sql: String): Long = {
    val conn = dataSource.getConnection
    val st = conn.prepareStatement(sql)
    val rs = st.executeQuery
    var maxSeq = 0L
    try{
      while(rs.next()){
        maxSeq = rs.getLong("NUM")
      }
    }finally {
      st.close
      conn.close
    }
    maxSeq
  }

  /**
    * 获得当前已经抓取的SEQ值
    *
    * @param sql
    * @return
    */
  override def queryCurrentSeq(sql: String): Long = {
    val conn = dataSource.getConnection
    val st = conn.prepareStatement(sql)
    val rs = st.executeQuery
    var currentSeq = 0L
    try{
      while(rs.next()){
        currentSeq = rs.getLong("NUM")
      }
    }finally {
      st.close
      conn.close
    }
    currentSeq
  }

  override def querySourcesList(): ListBuffer[mutable.HashMap[String, Any]] = {
    val sql = "select UUID,NAME,SQL,SEQ from soo_resource where flag = 1"
    val resultList = new mutable.ListBuffer[mutable.HashMap[String,Any]]
    val conn = dataSource.getConnection
    val st = conn.prepareStatement(sql)
    val rs = st.executeQuery
    try{
      while(rs.next()){
        var map = new scala.collection.mutable.HashMap[String,Any]
        map += ("UUID" -> rs.getString("UUID"))
        map += ("NAME" -> rs.getString("NAME"))
        map += ("SQL" -> rs.getString("SQL"))
        map += ("SEQ" -> rs.getString("SEQ"))
        resultList.append(map)
      }
    }finally {
      st.close
      conn.close
    }

    resultList
  }

  override def updateSooResourceFlag(uuid:String): Unit = {
    val conn = dataSource.getConnection
    var flag = -1
    val sql = "update soo_resource set FLAG=1 where UUID=?"
    var ps:PreparedStatement = null
    try{
      ps = conn.prepareStatement(sql)
      ps.setString(1,uuid)
      flag = ps.executeUpdate
      if(flag <=0){
        throw new Exception("更新失败")
      }
      conn.commit
    }finally {
      ps.close
      conn.close
    }
  }
}
