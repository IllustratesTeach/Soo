package org.gafis.config

import javax.xml.bind.annotation._

/**
  * Created by yuchen on 2017/8/17.
  */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "WebConfig")
@XmlRootElement
class WebConfig {

  @XmlElement(name = "web")
  var web: Web = new Web

  @XmlElement(name = "sync")
  var sync:Sync = new Sync

  @XmlElement(name = "elasticsearch-url")
  var elasticsearchurl:String ="http://localhost:9200"

  @XmlElement(name = "database")
  var database:DataBase = new DataBase

  @XmlElement(name = "sql")
  var sql:String = _

}

class Sync{
  @XmlElement(name = "sync_cron")
  var sync_cron: String = "0/1 * * * * ? "
}

class Web{
  @XmlElement(name = "bind")
  var bind: String = "127.0.0.1:8090"
}

class DataBase{

  @XmlElement(name = "driver")
  var driver:String = "oracle.jdbc.driver.OracleDriver"

  @XmlElement(name = "user")
  var user:String = "afis"

  @XmlElement(name = "password")
  var password:String = "helloafis"

  @XmlElement(name = "url")
  var url:String = "jdbc:oracle:thin:@192.168.1.17:1521:oragafis6"

}
