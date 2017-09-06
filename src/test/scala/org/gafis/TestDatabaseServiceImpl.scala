package org.gafis


import org.gafis.service.DatabaseService
import org.gafis.service.elasticsearch.DataAccessService
import org.junit.{Assert, Test}

/**
  * Created by yuchen on 2017/8/20.
  */
class TestDatabaseServiceImpl extends BaseTestCase{

   @Test
   def test_create(): Unit ={
     val service = getService[DatabaseService]
     val matchResult = service.create()
   }



  @Test
  def test_query(): Unit ={
    val service = getService[DatabaseService]
    Assert.assertTrue(service.query.size > 1)
  }

  @Test
  def test_bussize(): Unit ={
    val service = getService[DatabaseService]
    service.bussize
  }

  @Test
  def test_save(): Unit ={
    val service = getService[DatabaseService]
    service.save
  }

  @Test
  def test_spilt(): Unit ={
    val str = "SELECT max(seq) NUM FROM gafis_person"
    println(str.split("FROM")(1).trim)
  }

  @Test
  def test_querySourcesList(): Unit ={
    val service = getService[DatabaseService]
    Assert.assertTrue(service.querySourcesList().size > 0)
  }

  @Test
  def test_queryMaxSeq(): Unit ={
    val service = getService[DatabaseService]

    val list = service.querySourcesList()
    println(list(0).get("SEQ").get.toString)
    val value = service.queryMaxSeq(list(0).get("SQL").get.toString.split(";")(1))
    Assert.assertTrue(value > 0)
    println(value)
  }

  @Test
  def test_queryGafisPerson(): Unit ={
    val service_db = getService[DatabaseService]
    val service_adb = getService[DataAccessService]
    val list = service_db.querySourcesList()
    val list_result = service_adb.queryGafisPerson(list(0).get("SQL").get.toString.split(";")(0),1,10)
    Assert.assertTrue(list_result.size > 0)
    list_result.foreach{
      t => println(t.get("ID").get.toString + ";" + t.get("GATHER_ORG_NAME").get.toString)
    }
  }


}
