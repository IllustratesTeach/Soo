package org.gafis

import org.apache.tapestry5.ioc.ServiceBinder
import org.gafis.internal.elasticsearch.DataAccessServiceImpl
import org.gafis.service.elasticsearch.DataAccessService

/**
  * Created by yuchen on 2017/8/26.
  */
object ElasticSearchServiceModule {
  def bind(binder:ServiceBinder): Unit ={
    binder.bind(classOf[DataAccessService],classOf[DataAccessServiceImpl])
  }
}
