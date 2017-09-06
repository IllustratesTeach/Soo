package gafis.service.elasticsearch.sync

/**
  * Created by yuchen on 2017/9/4.
  */
trait SyncCronService {

  /**
    * 定时任务调用方法
    */
  def doWork()
}
