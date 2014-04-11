package models.fieldtrips

import org.datanucleus.store.types.converters.TypeConverter

object Housing extends Enumeration {
  type Housing = Value
  val Hotel = Value(0, "Hotel")
  val Motel = Value(1, "Motel")
  val Brothel = Value(2, "Brothel")  
}