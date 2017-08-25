package org.gafis

import org.apache.tapestry5.ioc.ServiceBinder
import org.gafis.internal.DatabaseServiceImpl
import org.gafis.service.DatabaseService

/**
  * Created by yuchen on 2017/8/20.
  */
object ServiceModule {
  def bind(binder:ServiceBinder): Unit ={
    binder.bind(classOf[DatabaseService],classOf[DatabaseServiceImpl])
  }
}
