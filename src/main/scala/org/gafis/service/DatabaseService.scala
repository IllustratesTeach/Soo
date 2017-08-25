package org.gafis.service


import java.sql.Connection
import scala.collection.mutable
/**
  * Created by yuchen on 2017/8/18.
  */
trait DatabaseService {

    def create():Unit

    def save(conn:Connection):Int

    def update(conn:Connection):Int

    def query():mutable.ListBuffer[mutable.HashMap[String,Any]]

    def bussize():Unit
}
