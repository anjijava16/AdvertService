package service

import javax.inject.Inject

import models.Advert
import play.api.libs.json.JsObject
import repositories.AdvertRepository

import scala.concurrent.Future

class AdvertService @Inject()(advertRepository: AdvertRepository) {
  def delete(id: String):Future[Any] = ???

  def update(id: String): Future[Any] = ???

  def select(id: String): Future[Option[Advert]] = ???

  def save(advert: Advert): Future[Any] = ???

  def findSortedBy(sortBy: String): Future[List[JsObject]] = advertRepository.findSortedBy(sortBy)

}
