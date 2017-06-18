package repositories
import models.Advert
import play.api.libs.json.JsObject

import scala.concurrent.{ExecutionContext, Future}

trait AdvertMongoRepository {
  type Status = Boolean
  def delete(id: String)(implicit ec: ExecutionContext): Future[Status]

  def update(id: String, advert: Advert)(implicit ec: ExecutionContext): Future[Status]

  def save(advert: Advert)(implicit ec: ExecutionContext): Future[Status]

  def findSortedBy(sortBy: String)(implicit ec: ExecutionContext): Future[List[JsObject]]

  def select(id: String)(implicit ec: ExecutionContext): Future[Option[JsObject]]
}
