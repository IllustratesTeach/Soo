package org.gafis


import org.gafis.service.DatabaseService
import org.junit.Test

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
    service.query.foreach{
      t => println(t.get("NAME") + "ID:" + t.get("ID"))
    }
  }

  @Test
  def test_bussize(): Unit ={
    val service = getService[DatabaseService]
    service.bussize
  }


}
