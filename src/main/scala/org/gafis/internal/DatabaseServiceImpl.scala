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


  override def save(): Int = {
    val conn = dataSource.getConnection
    var flag = -1
    val sql1 = "insert into TEST_PERSON (ID,NAME,MEMO) values (2,'郑智','广州恒大')"
    val ps:PreparedStatement = conn.prepareStatement(sql1)
    try{
      flag = ps.executeUpdate
      if(flag <=0){
        throw new Exception("insert fail")
      }
    }finally {
      ps.close
      conn.close
    }
     flag
  }

  override def update(): Int = {
    val conn = dataSource.getConnection
    var flag = -1

    val sql2 = "update TEST_PERSON set NAME='郑智' where id=2"
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
      //save(conn)
      update()
      conn.commit
    }catch{
      case e:Exception =>
        conn.rollback
        println(e.getMessage)
    }finally {
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

  override def updateCurrentSeq(seq: Long,uuid:String): Unit = {
      val conn = dataSource.getConnection
      var flag = -1

      val sql = "update soo_resource set SEQ=? where UUID=?"
      var ps:PreparedStatement = null
      try{
        ps = conn.prepareStatement(sql)
        ps.setLong(1,seq)
        ps.setString(2,uuid)
        flag = ps.executeUpdate
        if(flag <=0){
          throw new Exception("更新失败")
        }
      }finally {
        ps.close
      }
  }
}
