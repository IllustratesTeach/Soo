package org.gafis

import java.sql.Connection
import javax.sql.DataSource

import com.zaxxer.hikari.{HikariConfig, HikariDataSource}
import net.sf.log4jdbc.ConnectionSpy
import org.apache.tapestry5.ioc.annotations.EagerLoad
import org.apache.tapestry5.ioc.services.RegistryShutdownHub
import org.gafis.utils.Constant
import org.slf4j.Logger

/**
  * Created by yuchen on 2017/8/18.
  */
object DataSourceModule {
  @EagerLoad
  def buildDataSource(hub: RegistryShutdownHub, logger: Logger): DataSource = {
    val hikariConfig = new HikariConfig()
    //针对oracle特别处理
    hikariConfig.setConnectionTestQuery("select 1 from dual")
    hikariConfig.setDriverClassName(Constant.webConfig.get.database.driver)
    hikariConfig.setJdbcUrl(Constant.webConfig.get.database.url)
    hikariConfig.setUsername(Constant.webConfig.get.database.user)
    hikariConfig.setPassword(Constant.webConfig.get.database.password)
    //设置自动提交事务
    hikariConfig.setAutoCommit(false)

    hikariConfig.addDataSourceProperty("cachePrepStmts", "true")
    hikariConfig.addDataSourceProperty("prepStmtCacheSize", "250")
    hikariConfig.addDataSourceProperty("prepStmtCacheSqlLimit", "2048")
    hikariConfig.setMaximumPoolSize(5)
    //hikariConfig.addDataSourceProperty("maximumPoolSize", "5")

    //    new HikariDataSource(hikariConfig)
    val dataSource = new HikariDataSource(hikariConfig){
      override def getConnection: Connection = {
        new ConnectionSpy(super.getConnection)
      }
    }
    hub.addRegistryShutdownListener(new Runnable {
      override def run(): Unit = {
        dataSource.close()
      }
    })

    dataSource
  }
}
