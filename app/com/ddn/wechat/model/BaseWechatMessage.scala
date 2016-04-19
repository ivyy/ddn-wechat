package com.ddn.wechat.model

abstract class BaseWechatMessage {
  def toUserName:String
  def fromUserName:String
  def createTime:Long
  def messageType:String
}
