package service

import models.Advert
import play.api.libs.json.JsObject

import scala.concurrent.Future

class AdvertService {
  def update(id: String): Future[Any] = ???

  def select(id: String): Future[Option[Advert]] = ???

  def save(advert: Advert): Future[Any] = ???

  def findSortedBy(sortBy: String): Future[List[JsObject]] = ???

}
