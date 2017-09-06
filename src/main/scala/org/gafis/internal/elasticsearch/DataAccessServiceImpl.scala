package org.gafis.internal.elasticsearch

import java.sql.PreparedStatement
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
    val st = conn.prepareStatement(sql)
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
    }finally {
      ps.close
    }
  }
}
