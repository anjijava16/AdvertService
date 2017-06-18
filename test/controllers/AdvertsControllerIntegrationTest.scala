package controllers

import play.api.test.{FakeRequest, PlaySpecification, WithServer}

class AdvertsControllerIntegrationTest extends PlaySpecification {
  "AdvertsService" should {
    "be reachable" in new WithServer {
      val Some(result) = route(app, FakeRequest(GET, "/api/adverts"))

      status(result) must equalTo(OK)
    }
  }
}
