package com.ddn.wechat.service

import javax.inject.{Inject, Singleton}

import com.ddn.wechat.model.{BaseWechatResponse, BaseWechatMessage}
import org.apache.commons.codec.digest.DigestUtils

import scala.concurrent.Future


@Singleton
class WechatServer @Inject()(wechatClient: WechatClient, wechatMessageHandler: WechatMessageHandler) {

  def validate(signature: String, timestamp: String, nonce: String): Boolean = {
    val token = wechatClient.getAccessToken()
    val str = Array(token, timestamp, nonce).sorted.mkString
    DigestUtils.sha1Hex(str) == signature
  }

  def handleMessage(message: BaseWechatMessage): Future[BaseWechatResponse] = {
    wechatMessageHandler.handle(message)
  }

}
