package service

import javax.inject.Inject

import models.Advert
import play.api.libs.json.JsObject
import repositories.AdvertRepository

import scala.concurrent.Future

class AdvertService @Inject()(advertRepository: AdvertRepository) {
  def delete(id: String):Future[Boolean] = advertRepository.delete(id)

  def update(id: String, advert: Advert): Future[Boolean] = advertRepository.update(id, advert)

  def select(id: String): Future[Option[Advert]] = ???

  def save(advert: Advert): Future[Boolean] = advertRepository.save(advert)

  def findSortedBy(sortBy: String): Future[List[JsObject]] = advertRepository.findSortedBy(sortBy)

}
