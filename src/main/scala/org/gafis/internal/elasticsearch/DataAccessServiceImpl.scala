package org.gafis.internal.elasticsearch

import javax.sql.DataSource

import org.gafis.service.elasticsearch.DataAccessService

import scala.collection.mutable
/**
  * Created by yuchen on 2017/8/26.
  */
class DataAccessServiceImpl(implicit val dataSource: DataSource) extends DataAccessService{

  override def getDataFromDataBase(): mutable.ListBuffer[mutable.HashMap[String,Any]] = {
    val sql = "SELECT * FROM TEST_PERSON"
    val resultList = new mutable.ListBuffer[mutable.HashMap[String,Any]]
    val conn = dataSource.getConnection
    val st = conn.prepareStatement(sql)
    val rs = st.executeQuery
    try{
      while(rs.next()){
        var map = new scala.collection.mutable.HashMap[String,Any]
        map += ("ID" -> rs.getString("ID"))
        map += ("NAME" -> rs.getString("NAME"))
        map += ("MEMO" -> rs.getString("MEMO"))
        resultList.append(map)
      }
    }finally {
      st.close
      conn.close
    }

    resultList
  }
}
