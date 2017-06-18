
import java.util.concurrent.TimeUnit

import akka.util.Timeout
import controllers.AdvertsController
import models.Advert._
import org.mockito.Matchers.{eq => eqTo}
import org.specs2.mock.Mockito
import org.specs2.mutable.Specification
import play.api.libs.json.{JsArray, Json}
import play.api.mvc.Results
import play.api.test.FakeRequest
import play.api.test.Helpers.{GET, contentAsJson}
import service.AdvertService

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

class AdvertsControllerSpec extends Specification with Results with Mockito {
  val mockAdvertService = mock[AdvertService]

  class TestController extends AdvertsController() {
    override def advertRepo: AdvertService = mockAdvertService
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

  val ads = List(newCarAdJson)

  implicit val timeout = Timeout(5, TimeUnit.SECONDS)

  "AdvertsController" should {
    "list adverts sorted by id by default" in {
      mockAdvertService.findSortedBy(eqTo(Id)) returns Future(ads)

      val result = controller.list().apply(FakeRequest())

      contentAsJson(result) must be equalTo JsArray(ads)
      there was (mockAdvertService).findSortedBy(eqTo(Id))
    }

    "list adverts sorted by query param" in {
      mockAdvertService.findSortedBy(eqTo(Price)) returns Future(ads)

      val result = controller.list().apply(FakeRequest(GET, "/api/adverts?sortBy=price"))

      contentAsJson(result) must be equalTo JsArray(ads)
      there was (mockAdvertService).findSortedBy(eqTo(Price))
    }

  }

}