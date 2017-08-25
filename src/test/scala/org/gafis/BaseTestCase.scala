package org.gafis

import javax.persistence.EntityManagerFactory

import org.apache.tapestry5.ioc.{Registry, RegistryBuilder, ServiceBinder}
import org.gafis.internal.DatabaseServiceImpl
import org.gafis.service.DatabaseService
import org.gafis.utils.Constant
import org.junit.{Before}


import scala.reflect.{ClassTag, classTag}

/**
  * Created by yuchen on 2017/8/20.
  */
class BaseTestCase {
  private var registry:Registry = _
  protected def getService[T:ClassTag]:T={
    registry.getService(classTag[T].runtimeClass.asInstanceOf[Class[T]])
  }
  @Before
  def setup: Unit ={
    Constant.getWebConfig
    val modules = Seq[String](
      //"stark.activerecord.StarkActiveRecordModule",
      "org.gafis.DataSourceModule",
      //"org.gafis.ServiceModule"
      "org.gafis.TestModule"
      //"stark.webservice.StarkWebServiceModule"
    ).map(Class.forName)
    registry = RegistryBuilder.buildAndStartupRegistry(modules: _*)
  }


}

object TestModule{

  def bind(binder: ServiceBinder): Unit = {
    binder.bind(classOf[DatabaseService],classOf[DatabaseServiceImpl])
  }

}
