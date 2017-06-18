package repositories

import models.Advert
import play.api.libs.json.JsObject

import scala.concurrent.Future

class AdvertRepository {
  type Status = Boolean

  def delete(id: String):Future[Status] = ???

  def update(id: String, advert: Advert):Future[Status] = ???

  def save(advert: Advert): Future[Status] = ???

  def findSortedBy(sortBy: String):Future[List[JsObject]] = ???

}
