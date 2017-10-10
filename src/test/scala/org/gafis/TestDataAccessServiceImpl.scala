package gafis

import org.gafis.BaseTestCase
import org.gafis.service.elasticsearch.DataAccessService
import org.gafis.utils.Constant
import org.junit.{Assert, Test}

/**
  * Created by yuchen on 2017/10/10.
  */
class TestDataAccessServiceImpl extends BaseTestCase{


  @Test
  def test_initSooResource():Unit ={
    val sql = "sql"
    val service = getService[DataAccessService]
    service.initSooResource("gafis_person",sql)
  }

  @Test
  def test_queryGafisPersonForInputDataToIndex():Unit ={
    val service = getService[DataAccessService]
    val sql = Constant.webConfig.get.sql
    val list = service.queryGafisPersonForInputDataToIndex(sql)
    Assert.assertTrue(list.size > 0)
    list.foreach{
      t => println(t.get("ID").get.toString)
    }
  }


  @Test
  def test_queryGafisPerson(): Unit ={
    val service = getService[DataAccessService]
    val list = service.querySourcesList()
    val list_result = service.queryGafisPerson(list(0).get("SQL").get.toString.split(";")(0),1,10)
    Assert.assertTrue(list_result.size > 0)
    list_result.foreach{
      t => println(t.get("ID").get.toString + ";" + t.get("GATHER_ORG_NAME").get.toString)
    }
  }

  @Test
  def test_spilt(): Unit ={
    val str = "SELECT max(seq) NUM FROM gafis_person"
    println(str.split("FROM")(1).trim)
  }



  @Test
  def test_querySourcesList(): Unit ={
    val service = getService[DataAccessService]
    Assert.assertTrue(service.querySourcesList().size > 0)
  }

  @Test
  def test_queryMaxSeq(): Unit ={
    val service = getService[DataAccessService]

    val list = service.querySourcesList()
    println(list(0).get("SEQ").get.toString)
    val value = service.queryMaxSeq(list(0).get("SQL").get.toString.split(";")(1))
    Assert.assertTrue(value > 0)
    println(value)
  }


}
