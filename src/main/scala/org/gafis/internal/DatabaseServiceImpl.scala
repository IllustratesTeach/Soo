package org.gafis.internal

import java.sql.{Connection, PreparedStatement}
import java.util
import javax.sql.DataSource

import org.gafis.service.DatabaseService

import scala.collection.immutable.List
import scala.collection.mutable
import scala.collection.mutable.ListBuffer

/**
  * Created by yuchen on 2017/8/18.
  */
class DatabaseServiceImpl(implicit val dataSource: DataSource) extends DatabaseService{

  override def create(): Unit = {
    val sql: String = "CREATE TABLE TEST_PERSON(ID INT PRIMARY KEY" + ",NAME VARCHAR(255)" + ",MEMO VARCHAR(500))"
    val conn = dataSource.getConnection
    var st:PreparedStatement = null
    try{
          st = conn.prepareStatement(sql)
          st.executeUpdate
    }finally {
      st.close
      conn.close
    }
  }


  override def save(conn:Connection): Int = {
    var flag = -1
    val sql1 = "insert into TEST_PERSON (ID,NAME,MEMO) values (7,'wangwu7','memo')"
    var ps:PreparedStatement = null
    try{
      ps = conn.prepareStatement(sql1)
      flag = ps.executeUpdate
      if(flag <=0){
        throw new Exception("insert fail")
      }
    }finally {
      ps.close
    }
     flag
  }

  override def update(conn:Connection): Int = {
    var flag = -1

    val sql2 = "update TEST_PERSON set name='lisi1' where id=5"
    var ps:PreparedStatement = null
    try{
      ps = conn.prepareStatement(sql2)
      flag = ps.executeUpdate
      if(flag <=0){
        throw new Exception("更新失败")
      }
    }finally {
      ps.close
    }
    flag
  }

  override def query(): mutable.ListBuffer[mutable.HashMap[String,Any]] = {
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

  override def bussize(): Unit = {
    val conn = dataSource.getConnection
    try{
      save(conn)
      update(conn)
      conn.commit
    }catch{
      case e:Exception =>
        conn.rollback
        println(e.getMessage)
    }finally {
      conn.close
    }
  }
}
