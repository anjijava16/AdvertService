package controllers

import models.Advert._
import play.api.libs.json.Json
import play.api.mvc._
import service.AdvertService

import scala.concurrent.ExecutionContext.Implicits.global

class AdvertsController {
  def advertRepo = new AdvertService()

  def list = Action.async { implicit request =>
    val sortBy = request.getQueryString("sortBy").getOrElse(Id)
    advertRepo.findSortedBy(sortBy).map(adverts => Results.Ok(Json.toJson(adverts)))
  }
}
