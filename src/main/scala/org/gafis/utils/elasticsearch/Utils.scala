package org.gafis.utils.elasticsearch

import java.nio.charset.Charset

import org.apache.http.client.config.RequestConfig
import org.apache.http.client.methods._
import org.apache.http.entity.StringEntity
import org.apache.http.impl.client.{CloseableHttpClient, HttpClients}
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


  object CallFactory{

    final val PUT = "_PUT"
    final val GET = "_GET"
    final val DELETE = "_DELETE"
    final val POST = "_POST"

    val defaultRequestConfig = RequestConfig.custom()
      .setSocketTimeout(5000)
      .setConnectTimeout(5000)
      .setConnectionRequestTimeout(5000)
      .setStaleConnectionCheckEnabled(true)
      .build

    val  httpClient :CloseableHttpClient=  HttpClients.custom.setDefaultRequestConfig(defaultRequestConfig)
      .build


    private def getRequestMethod(requestType:String,uri:String): HttpRequestBase ={
      var httpRequestBase:HttpRequestBase = null
      requestType match{
        case PUT => httpRequestBase = new HttpPut(uri)
        case GET => httpRequestBase = new HttpGet(uri)
        case DELETE => httpRequestBase = new HttpDelete(uri)
        case POST => httpRequestBase = new HttpPost(uri)
        case _ => throw new Exception("request method error:" + requestType)
      }

      httpRequestBase.addHeader("Content-type","application/json; charset=utf-8")
      httpRequestBase.setHeader("Accept", "application/json")
      httpRequestBase
    }

    def call(requestType:String,uri:String,jsonParam:String = ""): String ={
      val method = getRequestMethod(requestType,uri)
      if(requestType.equals(POST)){
        method.asInstanceOf[HttpPost].setEntity(new StringEntity(jsonParam, Charset.forName("UTF-8")))
      }else if(requestType.equals(PUT) && !jsonParam.equals("")){
        method.asInstanceOf[HttpPut].setEntity(new StringEntity(jsonParam, Charset.forName("UTF-8")))
      }
      val response = httpClient.execute(method)
      if(response.getStatusLine.getStatusCode != 200 && !requestType.equals(PUT)){
        throw new RuntimeException("net request failed:" +response.getEntity.toString)
      }
      EntityUtils.toString(response.getEntity)
    }
  }

}
