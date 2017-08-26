package org.gafis.utils

import java.util

import com.google.gson.Gson
import org.apache.tapestry5.json.JSONObject

import scala.collection.mutable

/**
  * Created by yuchen on 2017/8/20.
  */
object JsonUtil {

  def listToJSONStr(list:util.ArrayList[util.HashMap[String,Any]]): String ={
    val gSon = new Gson()
    gSon.toJson(list)
  }

  def mapToJSONStr(hashMap:mutable.HashMap[String,Any]): String ={
    val jSONObject = new JSONObject()
    hashMap.foreach{ t => jSONObject.put(t._1,t._2)}
    jSONObject.toString
  }
}
