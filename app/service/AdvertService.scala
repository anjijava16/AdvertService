package service

import play.api.libs.json.JsObject

import scala.concurrent.Future

class AdvertService {
  def findSortedBy(sortBy: String):Future[List[JsObject]] = ???

}
