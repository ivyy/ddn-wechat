package com.ddn.wechat.controller

import javax.inject.{Inject, Singleton}
import com.ddn.wechat.model.BaseWechatResponse
import com.ddn.wechat.service.{WechatMessageParser, WechatClient, WechatServer}
import play.api.mvc.{BodyParsers, Action, Controller}
import play.api.libs.concurrent.Execution.Implicits.defaultContext

@Singleton
class WechatApplication @Inject()(wechatServer: WechatServer, wechatClient: WechatClient) extends Controller {

  def validateWechatApp = Action {
    implicit request =>
      val signature = request.getQueryString("signature").getOrElse("")
      val timestamp = request.getQueryString("timestamp").getOrElse("")
      val nonce = request.getQueryString("nonce").getOrElse("")
      val echoStr = request.getQueryString("echostr").getOrElse("")
      Ok(if (wechatServer.validate(signature, timestamp, nonce)) echoStr else "")
  }

  def accessToken = Action {
    Ok(wechatClient.getAccessToken)
  }

  def ipList = Action {
    val ips = wechatClient.getIpList
    println(ips)
    Ok(ips.toString())
  }

  def handleMessage = Action.async(BodyParsers.parse.xml) {
    implicit request => {
      val message = WechatMessageParser.parse(request.body)
      val future = wechatServer.handleMessage(message)
      future.mapTo[BaseWechatResponse].map(response => Ok(response.toXml))
    }
  }

}
