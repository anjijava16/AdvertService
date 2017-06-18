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
  def advertService = new AdvertService()

  def list = Action.async { implicit request =>
    val sortBy = request.getQueryString("sortBy").getOrElse(Id)
    advertService.findSortedBy(sortBy).map(adverts => Ok(Json.toJson(adverts)))
  }

  def create = Action.async(BodyParsers.parse.json) { implicit request =>
    val advertResult = request.body.validate[Advert]

    advertResult match {
      case JsSuccess(advert, _) =>
        try {
          advertService.save(advert).map(_ => Created)
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

  def read(id: String): Action[AnyContent] = Action.async { implicit request =>
    advertService.select(id).map {
      case Some(advert) => Ok(Json.toJson(advert))
      case None => Ok
    }
  }

  def update(id: String) = Action.async(BodyParsers.parse.json) { implicit request =>
    val advertResult = request.body.validate[Advert]

    advertResult match {
      case JsSuccess(advert, _) =>
        advertService.update(id).map(_ => Accepted)
      case error: JsError =>
        println(error)
        Future(BadRequest)
    }
  }

  def delete(id: String) = Action.async { implicit request =>
    advertService.delete(id).map(_ => Accepted)
  }
}
