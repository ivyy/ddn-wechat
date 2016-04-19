package com.ddn.wechat.service

import com.ddn.wechat.model._
import com.ddn.wechat.util.WechatUtil
import scala.concurrent.Future
import play.api.libs.concurrent.Execution.Implicits.defaultContext

class DummyWechatMessageHandler extends WechatMessageHandler {

  override def handle(message: BaseWechatMessage): Future[BaseWechatResponse] = {
    message match {
      case msg: TextMessage =>
        Future {
          TextResponse(msg.fromUserName, msg.toUserName, WechatUtil.currentTimeInSeconds, msg.content)
        }
      case msg: VoiceMessage =>
        Future {
          TextResponse(msg.fromUserName, msg.toUserName, WechatUtil.currentTimeInSeconds, "VoiceMessage Handled")
        }
      case msg: LinkMessage =>
        Future {
          TextResponse(msg.fromUserName, msg.toUserName, WechatUtil.currentTimeInSeconds, "LinkMessage Handled")
        }
      case msg: LocationMessage =>
        Future {
          TextResponse(msg.fromUserName, msg.toUserName, WechatUtil.currentTimeInSeconds, "LocationMessage Handled:" + msg)
        }
      case msg: ShortVideoMessage =>
        Future {
          TextResponse(msg.fromUserName, msg.toUserName, WechatUtil.currentTimeInSeconds, "ShortVideoMessage Handled")
        }
      case msg: VideoMessage =>
        Future {
          TextResponse(msg.fromUserName, msg.toUserName, WechatUtil.currentTimeInSeconds, "VideoMessage Handled")
        }
      case msg: ImageMessage =>
        Future {
          TextResponse(msg.fromUserName, msg.toUserName, WechatUtil.currentTimeInSeconds, "ImageMessage Handled")
        }
      case msg: ClickEventMessage =>
        Future {
          TextResponse(msg.fromUserName, msg.toUserName, WechatUtil.currentTimeInSeconds, "ClickEventMessage Handled")
        }
      case msg: LocationEventMessage =>
        Future {
          TextResponse(msg.fromUserName, msg.toUserName, WechatUtil.currentTimeInSeconds, "LocationEventMessage Handled")
        }
      case msg: ScanSubscribedEventMessage =>
        Future {
          TextResponse(msg.fromUserName, msg.toUserName, WechatUtil.currentTimeInSeconds, "LocationEventMessage Handled")
        }
      case msg: SubscribeEventMessage =>
        Future {
          TextResponse(msg.fromUserName, msg.toUserName, WechatUtil.currentTimeInSeconds, "LocationEventMessage Handled")
        }
      case msg: UnSubscribeEventMessage =>
        Future {
          TextResponse(msg.fromUserName, msg.toUserName, WechatUtil.currentTimeInSeconds, "LocationEventMessage Handled")
        }
      case msg: ViewEventMessage =>
        Future {
          TextResponse(msg.fromUserName, msg.toUserName, WechatUtil.currentTimeInSeconds, "LocationEventMessage Handled")
        }
      case msg: UnknownWechatMessage =>
        Future {
          TextResponse(msg.fromUserName, msg.toUserName, WechatUtil.currentTimeInSeconds, "UnknownMessage dropped")
        }
    }
  }

}
