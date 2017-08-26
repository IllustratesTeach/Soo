package org.gafis.utils.elasticsearch


import java.nio.charset.Charset
import org.apache.http.client.methods.{HttpDelete, HttpGet, HttpPost, HttpPut}
import org.apache.http.entity.StringEntity
import org.apache.http.impl.client.DefaultHttpClient
import org.apache.http.util.EntityUtils
import org.elasticsearch.index.query.QueryBuilder
import org.elasticsearch.search.builder.SearchSourceBuilder

/**
  * Created by yuchen on 2017/8/25.
  */
object Utils {

    def buildESQueryParamByAPI(queryBuilder:QueryBuilder): String ={
      val searchSourceBuilder = new SearchSourceBuilder()
      searchSourceBuilder.query(queryBuilder).toString
    }

    def restClient_putDataToIndex(uri:String,jsonParams:String): String ={
      val  httpClient =  new DefaultHttpClient()
      val method = new HttpPost(uri)
      method.addHeader("Content-type","application/json; charset=utf-8")
      method.setHeader("Accept", "application/json")
      method.setEntity(new StringEntity(jsonParams, Charset.forName("UTF-8")))
      val response = httpClient.execute(method)
      EntityUtils.toString(response.getEntity())
    }

  def restClient_createIndex(uri:String,jsonParams:String): String ={
    val  httpClient =  new DefaultHttpClient()
    val method = new HttpPut(uri)
    method.addHeader("Content-type","application/json; charset=utf-8")
    method.setHeader("Accept", "application/json")
    method.setEntity(new StringEntity(jsonParams, Charset.forName("UTF-8")))
    val response = httpClient.execute(method)
    EntityUtils.toString(response.getEntity())
  }

  def restClient_delIndex(uri:String): String ={
    val  httpClient =  new DefaultHttpClient()
    val method = new HttpDelete(uri)
    method.addHeader("Content-type","application/json; charset=utf-8")
    method.setHeader("Accept", "application/json")
    val response = httpClient.execute(method)
    EntityUtils.toString(response.getEntity())
  }

  def restClient_searchIndex(uri:String): String ={
    val  httpClient =  new DefaultHttpClient()
    val method = new HttpGet(uri)
    method.addHeader("Content-type","application/json; charset=utf-8")
    method.setHeader("Accept", "application/json")
    val response = httpClient.execute(method)
    EntityUtils.toString(response.getEntity())
  }

  def restClient_query(uri:String,jsonParams:String):String ={
    val  httpClient =  new DefaultHttpClient()
    val method = new HttpPost(uri)
    method.addHeader("Content-type","application/json; charset=utf-8")
    method.setHeader("Accept", "application/json")
    val response = httpClient.execute(method)
    method.setEntity(new StringEntity(jsonParams, Charset.forName("UTF-8")))
    EntityUtils.toString(response.getEntity())
  }

}
