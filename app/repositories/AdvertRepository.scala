package repositories

import play.api.libs.json.JsObject

import scala.concurrent.Future

class AdvertRepository {
  def findSortedBy(sortBy: String):Future[List[JsObject]] = ???

}
