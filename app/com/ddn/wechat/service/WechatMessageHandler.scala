package com.ddn.wechat.service

import com.ddn.wechat.model.{BaseWechatResponse, BaseWechatMessage}

import scala.concurrent.Future


trait WechatMessageHandler {
  def handle(message: BaseWechatMessage): Future[BaseWechatResponse]
}
