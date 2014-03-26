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
import models.fieldtrips.Housing

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
  private[this] var _startDate: java.sql.Date = _
  def startDate: LocalDate = LocalDate.fromDateFields(_startDate)
  def startDate_=(theStartDate: LocalDate) { _startDate = new java.sql.Date(theStartDate.toDateTimeAtStartOfDay.getMillis)}
  
   @Persistent
  private[this] var _endDate: java.sql.Date = _
  def endDate: LocalDate = LocalDate.fromDateFields(_endDate)
  def endDate_=(theEndDate: LocalDate) { _endDate =  new java.sql.Date(theEndDate.toDateTimeAtStartOfDay.getMillis)}
  
  @Persistent
  private[this] var _transportation: Int = _
  def transportation: Transport.Value = Transport(_transportation)
  def transportation_=(theTransportation: Transport.Transport) { _transportation = theTransportation.id }

  @Persistent
  @Column(allowsNull="true")
  private[this] var _housing: Int = _
  def housing: Housing.Value = Housing(_housing)
  def housing_=(theHousing: Housing.Housing) { _housing = theHousing.id }
  
  def this(destination: String, teacher: Teacher, startdate:LocalDate,endDate: LocalDate, 
      transportation: Transport.Transport, housing: Housing.Housing) {
    this()
    destination_=(destination)
    teacher_=(teacher)
    startDate_=(startDate)
    endDate_= (endDate)
	transportation_=(transportation)
    housing_=(housing)
  }
  
}