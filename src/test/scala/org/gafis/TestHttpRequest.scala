package org.gafis

import java.util

import org.apache.http.NameValuePair
import org.apache.http.message.BasicNameValuePair
import org.gafis.utils.HttpClient
import org.junit.Test

/**
  * Created by yuchen on 2017/8/20.
  */
class TestHttpRequest {

  @Test
  def test_Get(): Unit ={
    val str = HttpClient.get("http://localhost:8009")
    println(str)
  }

  @Test
  def test_Post(): Unit ={
    val params = new util.ArrayList[NameValuePair]
    params.add(new BasicNameValuePair("name","yuchen"))
    val str = HttpClient.post("http://localhost:8090",params)
    println(str)
  }
}
