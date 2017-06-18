package controllers

import play.api.libs.json.Json
import play.api.mvc._
import repositories.AdvertRepository

import scala.concurrent.ExecutionContext.Implicits.global

class AdvertsController {
  def advertRepo = new AdvertRepository()

  def list = Action.async { implicit request =>
    advertRepo.find().map(adverts => Results.Ok(Json.toJson(adverts)))
  }
}
