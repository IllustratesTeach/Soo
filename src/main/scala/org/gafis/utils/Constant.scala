package org.gafis.utils

import java.io.File

import org.gafis.config.WebConfig


/**
  * Created by yuchen on 2017/8/17.
  */
object Constant {


  var webConfig:Option[WebConfig] = None


  final val LOGO = """ _____
                      / ____|
                     | (___   ___   ___
                      \___ \ / _ \ / _ \
                      ____) | (_) | (_) |
                     |_____/ \___/ \___/ """

  def getWebConfig(): Unit ={
    val file = new File(getClass.getClassLoader.getResource("webconfig.xml").getPath)
    val obj = XMLParser.xmlToBean(file,classOf[WebConfig])
    webConfig = Some(obj.asInstanceOf[WebConfig])
  }


}
