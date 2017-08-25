package org.gafis.pages

import javax.inject.Inject

import org.apache.tapestry5.json.{JSONArray, JSONObject}
import org.apache.tapestry5.services.{Request, Response}
import org.apache.tapestry5.util.TextStreamResponse
import org.gafis.service.DatabaseService

/**
  * Created by yuchen on 2017/8/20.
  */
class Start {
  @Inject
  private var request:Request= _

  @Inject
  private var databaseService:DatabaseService = _

  @Inject
  private var response:Response= _

  def onActivate = {
    val jSONArray = new JSONArray()
    if(null == request.getHeader("type") || !request.getHeader("type").equals("soo")){
      val jSONObject = new JSONObject()
      jSONObject.put("message","requestHead Error")
      jSONObject.put("name",request.getParameter("name"))
      jSONArray.put(jSONObject)
    }else{

      val result = databaseService.query
      result.foreach{
        t =>
          val jSONObject = new JSONObject()
          t.foreach{
            m =>
              jSONObject.append(m._1,m._2)
          }
          jSONArray.put(jSONObject)
      }
    }


    response.setHeader("Access-Control-Allow-Origin","*")
    response.setHeader("Access-Control-Allow-Headers","X-Requested-With,Content-Type,X-Hall-Request")
    new TextStreamResponse("text/plain", "Welcome to Rest Soo server! \n " + jSONArray.toString)
  }
}
