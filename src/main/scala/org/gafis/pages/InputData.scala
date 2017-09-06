package gafis.pages


import javax.inject.Inject

import gafis.service.elasticsearch.InputDataService
import gafis.utils.CommonUtils
import org.apache.tapestry5.{RenderSupport, StreamResponse}
import org.apache.tapestry5.annotations.Environmental
import org.apache.tapestry5.services.Request
import org.apache.tapestry5.util.TextStreamResponse
import org.gafis.utils.Constant

/**
  * Created by yuchen on 2017/9/6.
  */
class InputData {

  @Environmental
  var renderSupport:RenderSupport = _

  @Inject
  private var request:Request= _

  @Inject
  private var inputDataService:InputDataService = _

  def setupRender(): Unit = {
    renderSupport.addScript("inputdata = function(source,result){"
      + "new Ajax.Request('InputData.inputdata/' + $F(source),{"
      + "method:'POST'," + "onSuccess: function(transport){"
      //+ "$(result).update(transport.responseText);"
      +"alert(transport.responseText)"
      + "}" + "});"
      + "}")
  }


  def  onActionFromInputData():StreamResponse={
    var result = Constant.EMPTY
    if(!CommonUtils.isNullOrEmpty(request.getParameter("sql"))){

      val sql_arr = request.getParameter("sql").split(";")




    }else{
      result = "<font size=\"3\" color=\"red\">输入参数为空</font>"
    }
    new TextStreamResponse("text/html",result)
  }
}
