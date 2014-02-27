package models.fieldtrips

import org.datanucleus.store.types.converters.TypeConverter

object Transport extends Enumeration {
  type Transport = Value
  val JcpsSchoolBus = Value(0, "JCPS School Bus")
  val CommonCarrier = Value(1, "Common Carrier")
  val PrivateAuto = Value(2, "Private Auto(s)")
  val Walking = Value(3, "Walking")
}