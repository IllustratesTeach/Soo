package org.gafis.service.elasticsearch

import java.util
/**
  * Created by yuchen on 2017/8/26.
  */
trait DataAccessService {
    def getDataFromDataBase():util.ArrayList[util.HashMap[String,Any]]
}
