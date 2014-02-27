package models.fieldtrips

import javax.jdo.annotations._
import org.datanucleus.api.jdo.query._
import org.datanucleus.query.typesafe._
import org.joda.time.DateTime
import play.api.mvc.Request
import config.users.UsesDataStore
import models.users.DbEquality
import models.courses.Teacher
import org.joda.time.LocalDate
import scala.collection.JavaConverters._

@PersistenceCapable(detachable="true")
class FieldTrip extends UsesDataStore with DbEquality[FieldTrip] {
  @PrimaryKey
  @Persistent(valueStrategy=IdGeneratorStrategy.INCREMENT)
  private[this] var _id: Long = _
  def id: Long = _id
  
  private[this] var _destination: String = _
  def destination: String = _destination
  def destination_=(theDestination: String) { _destination = theDestination }
  
  @Persistent
  private[this] var _teacher: Teacher = _
  def teacher: Teacher = _teacher
  def teacher_=(theTeacher: Teacher) { _teacher = theTeacher }
  
  @Persistent
  private[this] var _dates: java.util.List[java.sql.Date] = _
  def dates: List[LocalDate] = _dates.asScala.toList.map(LocalDate.fromDateFields(_))
  def dates_=(theDates: List[LocalDate]) { _dates = theDates.map(d => new java.sql.Date(d.toDateTimeAtStartOfDay.getMillis)).asJava }
  
  @Persistent
  private[this] var _transportation: Int = _
  def transportation: Transport.Value = Transport(_transportation)
  def transportation_=(theTransportation: Transport.Transport) { _transportation = theTransportation.id }

  @Persistent
  @Column(allowsNull="true")
  private[this] var _housing: String = _
  def housing: Option[String] = if (_housing == null) None else Some(_housing)
  def housing_=(theHousing: Option[String]) { _housing = theHousing.getOrElse(null) }
  
}