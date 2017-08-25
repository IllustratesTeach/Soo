package org.gafis.utils.elasticsearch


import java.io.DataOutputStream
import java.net.{HttpURLConnection, URL}

import org.elasticsearch.index.query.QueryBuilder
import org.elasticsearch.search.builder.SearchSourceBuilder

/**
  * Created by yuchen on 2017/8/25.
  */
object Utils {

    def buildESQueryParamByAPI(queryBuilder:QueryBuilder): Unit ={
      val searchSourceBuilder = new SearchSourceBuilder()
      searchSourceBuilder.query(queryBuilder).toString
    }

    def restClient(url:String,jsonParams:String): Unit ={
      val url = new URL(url)
      val conn = url.openConnection.asInstanceOf[HttpURLConnection]
      var wr:DataOutputStream = null
      try{
        conn.setRequestMethod("PUT")
        conn.setRequestProperty("Content-Type","application/json")
        conn.setRequestProperty("Content-Length",jsonParams.getBytes.length.toString)
        conn.setRequestProperty("Content-Language","en-US")
        conn.setUseCaches(false)
        conn.setDoInput(true)
        wr = new DataOutputStream(conn.getOutputStream)
        wr.writeBytes(jsonParams)
        if(conn.getResponseCode != 200){
          throw new RuntimeException("request Failed,HTTP error code:" + conn.getResponseCode)
        }
      }finally {
        if(null != wr)wr.close
        if(null != conn)conn.disconnect
      }

    }
}
