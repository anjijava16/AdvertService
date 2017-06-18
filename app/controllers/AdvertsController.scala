package controllers

import models.Advert
import models.Advert._
import play.api.libs.json.{JsError, JsSuccess, Json}
import play.api.mvc.Results._
import play.api.mvc._
import service.AdvertService

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

class AdvertsController {
  def advertRepo = new AdvertService()

  def list = Action.async { implicit request =>
    val sortBy = request.getQueryString("sortBy").getOrElse(Id)
    advertRepo.findSortedBy(sortBy).map(adverts => Ok(Json.toJson(adverts)))
  }

  def create = Action.async(BodyParsers.parse.json) { implicit request =>
    val advertResult = request.body.validate[Advert]

    advertResult match {
      case JsSuccess(advert, _) =>
        try {
          advertRepo.save(advert).map(_ => Created)
        }
        catch {
          case _: Exception =>
            Future(InternalServerError)
        }
      case error: JsError =>
        println(error)
        Future(BadRequest)
    }
  }
}
