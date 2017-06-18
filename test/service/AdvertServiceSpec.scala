package service

import models.Advert
import models.Advert.{Fuel, IsNew, Price, Title}
import org.mockito.Matchers.{eq => eqTo}
import org.specs2.mock.Mockito
import org.specs2.mutable.Specification
import play.api.libs.json.Json
import repositories.AdvertRepository

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

class AdvertServiceSpec extends Specification with Mockito {

  val mockAdvertRepository = mock[AdvertRepository]

  val advertService = new AdvertService(mockAdvertRepository)

  private val newCarTitle = "New car"
  private val newCarPrice = 1234
  val newCarAdJson = Json.obj(
    Title -> newCarTitle,
    Fuel -> "Diesel",
    Price -> newCarPrice,
    IsNew -> true
  )

  val ads = List(newCarAdJson)

  "AdvertService" should {
    "find sorted by id" in {
      val sortBy = Advert.Id
      mockAdvertRepository.findSortedBy(eqTo(sortBy)) returns Future(ads)

      advertService.findSortedBy(sortBy)

      there was mockAdvertRepository.findSortedBy(eqTo(sortBy))
    }
  }
}
