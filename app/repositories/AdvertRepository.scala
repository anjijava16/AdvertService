package repositories

import javax.inject.Inject

import models.Advert
import play.api.libs.json.{JsObject, Json}
import play.modules.reactivemongo.ReactiveMongoApi
import play.modules.reactivemongo.json._
import reactivemongo.api.ReadPreference
import reactivemongo.play.json.collection.JSONCollection

import scala.concurrent.{ExecutionContext, Future}

class AdvertRepository @Inject()(reactiveMongoApi: ReactiveMongoApi) extends AdvertMongoRepository {
  def collection = reactiveMongoApi.db.collection[JSONCollection]("adverts")

  override def delete(id: String): Future[Status] = ???

  override def update(id: String, advert: Advert): Future[Status] = ???

  override def save(advert: Advert): Future[Status] = ???

  override def findSortedBy(sortBy: String)(implicit ec: ExecutionContext): Future[List[JsObject]] = {
    val cursor = collection.find(Json.obj())
      .sort(Json.obj(sortBy -> 1))
      .cursor[JsObject](ReadPreference.primary)
    cursor.collect[List]()
  }

  override def select(id: String): Future[Option[JsObject]] = ???
}
