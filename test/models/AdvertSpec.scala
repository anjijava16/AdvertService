package models

import models.Advert._
import org.specs2.mutable.Specification
import play.api.libs.json.{JsError, JsSuccess, Json}

class AdvertSpec extends Specification {
  private val newCarTitle = "New car"
  private val newCarPrice = 1234
  val newCarAdJson = Json.obj(
    Title -> newCarTitle,
    Fuel -> "Diesel",
    Price -> newCarPrice,
    IsNew -> true
  )

  val newCarAd = Advert(None, newCarTitle, Diesel(), newCarPrice, true)

  "Advert" should {
    "validate and create new car advert from json" in {
      val advertResult = newCarAdJson.validate[Advert]

      (advertResult match {
        case JsSuccess(advert, _) =>
          advert mustEqual newCarAd
          true
        case error: JsError =>
          println(error)
          false
      }) mustEqual true
    }
  }
}