package repositories

import javax.inject.Inject

import models.Advert
import models.Advert._
import play.api.libs.json.{JsObject, Json}
import play.modules.reactivemongo.ReactiveMongoApi
import play.modules.reactivemongo.json._
import reactivemongo.api.ReadPreference
import reactivemongo.bson.{BSONDocument, BSONObjectID}
import reactivemongo.play.json.collection.JSONCollection

import scala.concurrent.{ExecutionContext, Future}

class AdvertRepository @Inject()(reactiveMongoApi: ReactiveMongoApi) extends AdvertMongoRepository {
  def collection = reactiveMongoApi.db.collection[JSONCollection]("adverts")
  override def delete(id: String): Future[Status] = ???

  override def update(id: String, advert: Advert): Future[Status] = ???

  override def save(advert: Advert)(implicit ec: ExecutionContext): Future[Status] = {
    val document = getBsonDocument(advert)
    val result = collection.update(BSONDocument(Id -> document.get(Id).getOrElse(BSONObjectID.generate)), document, upsert = true)
    result.map(_.ok)
  }

  override def findSortedBy(sortBy: String)(implicit ec: ExecutionContext): Future[List[JsObject]] = {
    val cursor = collection.find(Json.obj())
      .sort(Json.obj(sortBy -> 1))
      .cursor[JsObject](ReadPreference.primary)
    cursor.collect[List]()
  }

  override def select(id: String): Future[Option[JsObject]] = ???

  private def getBsonDocument(advert: Advert) = {
    BSONDocument(
      Id -> advert.id
      , Title -> advert.title
      , Fuel -> advert.fuel.fuelType
      , Price -> advert.price
      , IsNew -> advert.isNew
      , Mileage -> advert.mileage
      , FirstRegistration -> advert.firstRegistration.map(_.toString(dateTimeFormat))
    )
  }
}
