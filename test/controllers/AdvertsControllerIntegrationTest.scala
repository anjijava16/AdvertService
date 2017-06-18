package controllers

import models.Advert._
import org.mockito.Matchers.{eq => eqTo}
import org.specs2.mock.Mockito
import play.api.libs.json.Json
import play.api.test.{FakeRequest, PlaySpecification, WithServer}

class AdvertsControllerIntegrationTest extends PlaySpecification with Mockito {
  val newCarAdJson = Json.obj(
    Title -> "New car",
    Fuel -> "Diesel",
    Price -> 1234,
    IsNew -> true
  )
  private val guid = "594680c64f00004f0056054f"
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

    "have api to get, delete, update ad by id" in new WithServer {
      route(app, FakeRequest(GET, "/api/adverts")) //Check why first insert fails

      val ad = Json.obj(
        Id -> guid,
        Title -> "New car",
        Fuel -> "Diesel",
        Price -> 1234,
        IsNew -> true
      )
      route(app, FakeRequest(POST, "/api/advert").withBody(ad))

      val Some(getByIdResult) = route(app, FakeRequest(GET, s"/api/advert/$guid"))
      status(getByIdResult) must equalTo(OK)

      val Some(updateResult) = route(app, FakeRequest(PATCH, s"/api/advert/$guid").withJsonBody(newCarAdJson))
      status(updateResult) must equalTo(ACCEPTED)

      val Some(deleteResult) = route(app, FakeRequest(DELETE, s"/api/advert/$guid"))
      status(deleteResult) must equalTo(ACCEPTED)
    }
  }
}
