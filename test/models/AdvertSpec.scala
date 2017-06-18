package models

import models.Advert._
import org.joda.time.DateTime
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

  private val oldCarTitle = "Old car"
  private val oldCarPrice = 2345
  private val dateOfRegistration = DateTime.now().minusYears(3)
  private val oldCarMileage = 12
  val oldCarAdJson = Json.obj(
    Title -> oldCarTitle,
    Fuel -> "Petrol",
    Price -> oldCarPrice,
    IsNew -> false,
    Mileage -> oldCarMileage,
    FirstRegistration -> dateOfRegistration
  )

  val newCarAd = Advert(None, newCarTitle, Diesel(), newCarPrice, true)
  val oldCarAd = Advert(None, oldCarTitle, Petrol(), oldCarPrice, false, Some(oldCarMileage), Some(dateOfRegistration))

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

    "validate and create old car advert from json" in {
      val advertResult = oldCarAdJson.validate[Advert]

      (advertResult match {
        case JsSuccess(advert, _) => {
          advert mustEqual oldCarAd
          true
        }
        case error: JsError => {
          println(error)
          false
        }
      }) mustEqual true
    }
  }
}