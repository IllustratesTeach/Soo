package org.gafis.internal.elasticsearch

import java.util
import javax.sql.DataSource

import org.gafis.service.elasticsearch.DataAccessService
/**
  * Created by yuchen on 2017/8/26.
  */
class DataAccessServiceImpl(implicit val dataSource: DataSource) extends DataAccessService{

  override def getDataFromDataBase(): util.ArrayList[util.HashMap[String,Any]] = {
    val sql = "SELECT * FROM TEST_PERSON"
    val resultList = new util.ArrayList[util.HashMap[String,Any]]
    val conn = dataSource.getConnection
    val st = conn.prepareStatement(sql)
    val rs = st.executeQuery
    try{
      while(rs.next()){
        var map = new util.HashMap[String,Any]
        map.put ("ID",rs.getString("ID"))
        map.put ("NAME",rs.getString("NAME"))
        map.put ("MEMO",rs.getString("MEMO"))
        resultList.add(map)
      }
    }finally {
      st.close
      conn.close
    }

    resultList
  }
}
