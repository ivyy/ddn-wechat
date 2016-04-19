package com.ddn.wechat.service

import javax.inject.{Inject, Singleton}

import play.api.Configuration
import play.api.libs.ws.WSClient

import scala.concurrent.duration.Duration
import scala.concurrent.{Await, ExecutionContext}

/**
  * Created by bigfish on 16-3-13.
  */


@Singleton
class WechatClient @Inject()(ws: WSClient, configuration: Configuration)(implicit executionContext: ExecutionContext) {

  private val wechatConfiguration = configuration.getConfig("wechat").getOrElse(Configuration.empty)

  private val appId: String = wechatConfiguration.getString("app.id").getOrElse("")

  private val appSecret: String = wechatConfiguration.getString("app.secret").getOrElse("")

  private val accessTokenUrl = wechatConfiguration.getString("access.token.url").get.format(appId, appSecret)

  private val ipListUrl = wechatConfiguration.getString("ip.list.url").get

  private var token: String = ""

  private var expiresIn: Int = -1

  private var tokenUpdateTime = 0L

  def getAccessToken(): String = {

    def timeout = tokenUpdateTime + expiresIn > System.currentTimeMillis() / 1000

    if (token.isEmpty || timeout) {

      val future = ws.url(accessTokenUrl).get().map {
        response =>
          (response.body, response.json)
      }

      val tuple = Await.result(future, Duration.Inf)
      if (tuple._1.contains("access_token")) {
        this.token = (tuple._2 \ "access_token").as[String]
        this.expiresIn = (tuple._2 \ "expires_in").as[Int]
        this.tokenUpdateTime = System.currentTimeMillis() / 1000
      } else {
        this.token = ""
        this.expiresIn = -1
      }

    }

    token

  }

  def getIpList: Set[String] = {

    val accessToken = getAccessToken()

    var ipList = Set.empty[String]

    if (!accessToken.isEmpty) {
      val url = ipListUrl.format(accessToken)
      val future = ws.url(url).get().map {
        response =>
          if (response.body.contains("ip_list")) {
            (response.json \ "ip_list").as[Set[String]]
          } else {
            Set.empty[String]
          }
      }
      ipList = Await.result(future, Duration.Inf)
    }

    ipList.filterNot(_.isEmpty)
  }
}
