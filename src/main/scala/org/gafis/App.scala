package org.gafis


import org.gafis.jettyserver.ServerSupport
import org.gafis.utils.Constant
import org.slf4j.LoggerFactory

/**
 * start App
 *
 */
object App extends ServerSupport{
  def main(args: Array[String]): Unit = {
    val logger = LoggerFactory getLogger getClass
    Constant.getWebConfig
    logger.info("Server Starting......")

    val classes = List[Class[_]](
      Class.forName("org.gafis.ServiceModule"),
      Class.forName("org.gafis.DataSourceModule"),
      Class.forName("stark.webservice.StarkWebServiceModule")
    )
    val webConfig = Constant.webConfig
    webConfig match{
      case Some(m) =>
        startServer(m,"org.gafis",classes:_*)
        logger.info("Rest Soo server started " + Constant.LOGO)
        join
      case _ =>
        logger.error("GET Config None")
    }
  }
}
