package controllers

import javax.inject._
import play.api._
import play.api.Configuration
import play.api.mvc._
import play.libs.ws._

import java.net.URLEncoder.encode

/**
 * This controller creates an `Action` to handle HTTP requests to the
 * application's home page.
 */
@Singleton
class HomeController @Inject()(
                                val controllerComponents: ControllerComponents,
                                ws: WSClient,
                                config: Configuration
                              ) extends BaseController {

  /**
   * Create an Action to render an HTML page.
   *
   * The configuration in the `routes` file means that this method
   * will be called when the application receives a `GET` request with
   * a path of `/`.
   */
  def index() = Action { implicit request: Request[AnyContent] =>
    Ok(views.html.index())
  }

  def slack() = Action { implicit request: Request[AnyContent] =>
    val secureKey:Option[String] = request.getQueryString("secureKey")
    secureKey match {
      case Some("hogehoge") => {
        val message:String = "Test send."
        sendMessageToSlack(message)
        Ok("ok")
      }
      case _ => {
        Ok("ng")
      }
    }
  }

  def sendMessageToSlack(message:String):Unit = {
    val slackApiToken:String = config.get[String]("slack.api_token")
    val channelId:String = config.get[String]("slack.channel")
    val slackApiUrl:String = "https://slack.com/api/"
    val method:String  = "chat.postMessage"
    val sendParamMap:Map[String,String] = Map(
      "token" ->  slackApiToken,
      "channel" -> channelId,
      "text" -> message
    )
    val sendParams:String = sendParamMap.map(param => encode(param._1,"utf-8") +"="+ encode(param._2,"utf-8")).mkString("&")
    println(sendParams)

    val slackRequestUrl:String = slackApiUrl + method + "?" + sendParams
    ws.url(slackRequestUrl).get()
  }
}
