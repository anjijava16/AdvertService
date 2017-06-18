package controllers

import models.Advert.{Fuel, IsNew, Price, Title}
import play.api.libs.json.Json
import play.api.test.{FakeRequest, PlaySpecification, WithServer}

class AdvertsControllerIntegrationTest extends PlaySpecification {
  private val newCarTitle = "New car"
  private val newCarPrice = 1234
  val newCarAdJson = Json.obj(
    Title -> newCarTitle,
    Fuel -> "Diesel",
    Price -> newCarPrice,
    IsNew -> true
  )
  "AdvertsControllerIntegrationTest" should {
    "have api to list adverts" in new WithServer {
      val Some(result) = route(app, FakeRequest(GET, "/api/adverts"))

      status(result) must equalTo(OK)
    }

    "have api to create ad" in new WithServer {
      route(app, FakeRequest(GET, "/api/adverts")) //Check why first insert fails
      val Some(result) = route(app, FakeRequest(POST, "/api/advert").withBody(newCarAdJson))

      status(result) must equalTo(CREATED)
    }
  }
}
