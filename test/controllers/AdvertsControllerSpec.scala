package controllers


import models.Advert._
import models.{Advert, Diesel}
import org.mockito.Matchers.{eq => eqTo}
import org.specs2.mock.Mockito
import org.specs2.mutable.Specification
import play.api.libs.json.{JsArray, Json}
import play.api.mvc.{Result, Results}
import play.api.test.FakeRequest
import play.api.test.Helpers.{GET, contentAsJson, status, _}
import service.AdvertService

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

class AdvertsControllerSpec extends Specification with Results with Mockito {
  val mockAdvertService = mock[AdvertService]

  class TestController extends AdvertsController() {
    override def advertService: AdvertService = mockAdvertService
  }

  val controller = new TestController()

  private val newCarTitle = "New car"
  private val newCarPrice = 1234
  val newCarAdJson = Json.obj(
    Title -> newCarTitle,
    Fuel -> "Diesel",
    Price -> newCarPrice,
    IsNew -> true
  )
  val newCarAd = Advert(None, newCarTitle, Diesel(), newCarPrice, true)
  val ads = List(newCarAdJson)

  val guid = "59438ac04a12f658004a435b"

  "AdvertsController" should {
    "list adverts sorted by id by default" in {
      mockAdvertService.findSortedBy(eqTo(Id)) returns Future(ads)

      val result = controller.list().apply(FakeRequest())

      contentAsJson(result) must be equalTo JsArray(ads)
      there was mockAdvertService.findSortedBy(eqTo(Id))
    }

    "list adverts sorted by query param" in {
      mockAdvertService.findSortedBy(eqTo(Price)) returns Future(ads)

      val result = controller.list().apply(FakeRequest(GET, "/api/adverts?sortBy=price"))

      contentAsJson(result) must be equalTo JsArray(ads)
      there was mockAdvertService.findSortedBy(eqTo(Price))
    }
    "create new car advert" in {
      mockAdvertService.save(eqTo(newCarAd)) returns Future(true)

      val request = FakeRequest().withBody(newCarAdJson)
      val result: Future[Result] = controller.create()(request)


      status(result) must be equalTo CREATED
      there was mockAdvertService.save(eqTo(newCarAd))
    }

    "get advert by id" in {
      mockAdvertService.select(eqTo(guid)) returns Future(Some(newCarAd))

      val result: Future[Result] = controller.read(guid).apply(FakeRequest())

      contentAsJson(result) must be equalTo newCarAdJson
      there was mockAdvertService.select(eqTo(guid))
    }

    "update advert by id" in {
      mockAdvertService.update(eqTo(guid)) returns Future(true)

      val request = FakeRequest().withBody(newCarAdJson)
      val result: Future[Result] = controller.update(guid)(request)

      status(result) must be equalTo ACCEPTED
      there was mockAdvertService.update(eqTo(guid))
    }

    "delete advert by id" in {
      mockAdvertService.delete(eqTo(guid)) returns Future(true)

      val result: Future[Result] = controller.delete(guid)(FakeRequest())

      status(result) must be equalTo ACCEPTED
      there was mockAdvertService.update(eqTo(guid))
    }

    "reject incorrect car advert with Bad Request" in {
      val badCarAdJson = Json.obj(
        Title -> "Incomplete car ad",
        Price -> 1234
      )
      val request = FakeRequest().withBody(badCarAdJson)
      val result: Future[Result] = controller.create()(request)

      status(result) must be equalTo BAD_REQUEST
    }
  }
}
