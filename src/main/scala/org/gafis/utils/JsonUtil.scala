package org.gafis.utils

import java.util

import com.google.gson.Gson

/**
  * Created by yuchen on 2017/8/20.
  */
object JsonUtil {

  def ListToJSONStr(list:util.ArrayList[util.HashMap[String,Any]]): String ={
    val gSon = new Gson()
    gSon.toJson(list)
  }
}
