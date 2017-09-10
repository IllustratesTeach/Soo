package gafis.utils

import java.sql.ResultSet
import java.util.regex.Pattern

import org.apache.commons.lang.StringUtils
import org.fusesource.jansi.{Ansi, AnsiConsole}
import org.slf4j.Logger

import scala.collection.mutable

/**
  * Created by yuchen on 2017/8/30.
  */
object CommonUtils {

  def isNullOrEmpty(str:String):Boolean = {
    var bStr = false
    if(StringUtils.isEmpty(str) || StringUtils.isBlank(str)){
      bStr = true
    }
    bStr
  }

  def getResultMap(rs:ResultSet):mutable.HashMap[String,Any] = {
    val hashMap = new mutable.HashMap[String, Any]
    val rsmd = rs.getMetaData
    val count = rsmd.getColumnCount

    for (i <- 1 until(count)){

      hashMap.put(rsmd.getColumnLabel(i),rs.getString(i))
    }
    hashMap
  }

  def printTextWithNative(logger: Logger,
                          logo: String) {
    var str = logo
    try {
      AnsiConsole.systemInstall()

      str = Ansi.ansi().render(logo).toString
      /*
      val className = "org.fusesource.jansi.Ansi"
      val clazz = Thread.currentThread().getContextClassLoader.loadClass(className)
      //ansi()
      val obj = clazz.getMethod("ansi").invoke(null)
      //ansi().render
      str = clazz.
        getMethod("render", classOf[String]).
        invoke(obj, "@|green " + str.
        format("|@ @|red " + text + "|@ @|green ", "|@ @|yellow " + version + "|@ @|green ", "|@ @|cyan " + nativeVersion + "|@")).toString
      */
      logger.info(str)
    } finally {
      AnsiConsole.systemUninstall()
    }
  }

  def filterSQL(sql:String): String ={
    val str = "\\*|update|or|delete|insert|trancate|char|into|substr|ascii|declare|exec|count|master|into|drop|execute"
    if(Pattern.compile(str).matcher(sql).find){
      throw new Exception("SQL is Ill")
    }
    sql
  }

  def replaceCharsetInSQL(sql:String):String ={
    sql.toLowerCase.replaceAll("<", "")
      .replaceAll(">", "")
      .replaceAll("'", "")
      .replaceAll("%", "")
      .replaceAll("--","")
      .replaceAll("/","")
  }

  /*def main(args: Array[String]): Unit = {
    println(filterSQL(replaceCharsetInSQL("select name FROm a where 1 = 1' and a.id = 100")))
  }*/
}
