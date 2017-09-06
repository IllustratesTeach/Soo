package org.gafis.utils

import java.io.File

import org.gafis.config.WebConfig


/**
  * Created by yuchen on 2017/8/17.
  */
object Constant {


  var webConfig:Option[WebConfig] = None


  final val LOGO = """ #
   _____
  / ____|
 | (___   ___   ___
  \___ \ / _ \ / _ \
  ____) | (_) | (_) | module : |@@|red %s|@#
 |_____/ \___/ \___/  version: |@@|green %s|@
                      """.replaceAll("#", "@|green ")

  final val EMPTY = ""

  def getWebConfig(): Unit ={
    val file = new File(getClass.getClassLoader.getResource("webconfig.xml").getPath)
    val obj = XMLParser.xmlToBean(file,classOf[WebConfig])
    webConfig = Some(obj.asInstanceOf[WebConfig])
  }


  final val JSON_PATTER_RESPONSE = "?pretty"

  final val JSON_PATTER_SEARCH_INDEX_RESPONSE = "_search" + JSON_PATTER_RESPONSE


  /**
    * 访问类型
    */
  final val PUT_DATA_TO_INDEX = "1"
  final val CREATE_INDEX = "2"
  final val DELETE_INDEX = "3"
  final val SEARCH_INDEX = "4"
  final val UPDATE_DATA_TO_INDEX = "5"
  final val QUERY = "6"


  //分隔符
  final val URL_SEPARATOR = "/"



}
