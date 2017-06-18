package models

import org.joda.time.DateTime
import play.api.libs.functional.syntax._
import play.api.libs.json._

case class Advert(id: Option[String],
                  title: String,
                  fuel: FuelType,
                  price: Int,
                  isNew: Boolean,
                  mileage: Option[Int] = None,
                  firstRegistration: Option[DateTime] = None)

sealed trait FuelType {
  def fuelType: String
}

case class Petrol() extends FuelType {
  override def fuelType = "Petrol"
}

case class Diesel() extends FuelType {
  override def fuelType = "Diesel"
}

object Advert {
  val Id = "_id"
  val Title = "title"
  val Fuel = "fuel"
  val Price = "price"
  val IsNew = "isNew"
  val Mileage = "mileage"
  val FirstRegistration = "firstRegistration"

  def getFuelType(fuelType: String): FuelType = fuelType.toLowerCase match {
    case "diesel" => Diesel()
    case "petrol" => Petrol()
  }

  val dateTimeFormat = "dd-MM-yyyy"

  implicit val dateTimeReads = Reads.jodaDateReads(dateTimeFormat)
  implicit val dateTimeWrites = Writes.jodaDateWrites(dateTimeFormat)
  implicit val fuelTypeWrites:Writes[FuelType] = new Writes[FuelType] {
    override def writes(o: FuelType): JsValue = Json.toJson(o.fuelType)
  }

  implicit val advertReads: Reads[Advert] = (
    (JsPath \ Id).readNullable[String] and
      (JsPath \ Title).read[String] and
      (JsPath \ Fuel).read[String].map(getFuelType) and
      (JsPath \ Price).read[Int] and
      (JsPath \ IsNew).read[Boolean] and
      (JsPath \ Mileage).readNullable[Int] and
      (JsPath \ FirstRegistration).readNullable[DateTime]
    ) (Advert.apply _)

  implicit val advertWrites: Writes[Advert] = (
    (JsPath \ Id).writeNullable[String] and
      (JsPath \ Title).write[String] and
      (JsPath \ Fuel).write[FuelType] and
      (JsPath \ Price).write[Int] and
      (JsPath \ IsNew).write[Boolean] and
      (JsPath \ Mileage).writeNullable[Int] and
      (JsPath \ FirstRegistration).writeNullable[DateTime]
    ) (unlift(Advert.unapply))
}
