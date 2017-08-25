package org.gafis.utils

import java.util
import java.util.concurrent.CountDownLatch

import org.apache.commons.httpclient.methods.RequestEntity
import org.apache.http.{Consts, HttpEntity, HttpResponse, NameValuePair}
import org.apache.http.client.config.RequestConfig
import org.apache.http.client.entity.UrlEncodedFormEntity
import org.apache.http.client.methods.{HttpGet, HttpPost}
import org.apache.http.concurrent.FutureCallback
import org.apache.http.impl.nio.client.HttpAsyncClients
import org.apache.http.util.EntityUtils

/**
  * Created by yuchen on 2017/8/20.
  */
object HttpClient {

    def get(url:String): String ={
      var result = ""
      val requestConfig = RequestConfig.custom.setSocketTimeout(3000).setConnectTimeout(3000).build
      val httpAsyncClients = HttpAsyncClients.custom.setDefaultRequestConfig(requestConfig).build
      httpAsyncClients.start

      val request = new HttpGet(url)

      val latch = new CountDownLatch(1)
      httpAsyncClients.execute(request,new FutureCallback[HttpResponse] {

        override def cancelled() = {
          latch.countDown
          throw new Exception(request.getRequestLine + ":cancelled")
        }

        override def completed(t: HttpResponse) = {
          latch.countDown
          if(t.getStatusLine.getStatusCode == 200){
            val entity = t.getEntity
            result = EntityUtils.toString(entity)
          }
        }

        override def failed(e: Exception) = {
          latch.countDown
          throw new Exception(request.getRequestLine + e.getMessage)
        }
      })
      try{
        latch.await()
      }finally {
        httpAsyncClients.close
      }
      result

    }



  def post(url:String,params:util.ArrayList[NameValuePair]): String ={
    var result = ""
    val requestConfig = RequestConfig.custom.setSocketTimeout(3000).setConnectTimeout(3000).build
    val httpAsyncClients = HttpAsyncClients.custom.setDefaultRequestConfig(requestConfig).build
    httpAsyncClients.start

    val httpPost = new HttpPost(url)
    if(params != null){
      val entity = new UrlEncodedFormEntity(params,Consts.UTF_8)
      httpPost.setEntity(entity)
    }

    val latch = new CountDownLatch(1)
    httpAsyncClients.execute(httpPost,new FutureCallback[HttpResponse] {

      override def cancelled() = {
        latch.countDown
        throw new Exception(httpPost.getRequestLine + ":cancelled")
      }

      override def completed(t: HttpResponse) = {
        latch.countDown
        if(t.getStatusLine.getStatusCode == 200){
          val entity = t.getEntity
          //t.getAllHeaders.foreach(t => println(t.getName + ":" + t.getValue))
          result = EntityUtils.toString(entity)
        }
      }

      override def failed(e: Exception) = {
        latch.countDown
        throw new Exception(httpPost.getRequestLine + e.getMessage)
      }
    })
    try{
      latch.await()
    }finally {
      httpAsyncClients.close
    }
    result

  }
}
