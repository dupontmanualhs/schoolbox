package controllers.fieldtrips

import com.google.inject.{ Inject, Singleton }
import config.users.{ Config, UsesDataStore }
import scalatags._
import play.api.mvc.Controller
import models.courses._
import models.users._
import scala.xml.NodeSeq
import play.api.mvc.Flash._
import org.dupontmanual.forms.Form
import org.dupontmanual.forms.fields._
import controllers.users.{Authenticated, VisitAction, VisitRequest}
import org.dupontmanual.forms.{ Binding, InvalidBinding, ValidBinding }


@Singleton
class App @Inject()(implicit config: Config) extends Controller with UsesDataStore {
	object FieldTrips extends Form{
		val teacher = new TextField("Teacher")
		val destination = new TextField("Destination")
		val dates = new TextField("Date")
		val transportation = new TextField("transportation")
		val housing = new TextField("Housing")
		
		val fields = List(teacher, destination, dates, transportation, housing)
	}

	def createFieldTrip = Authenticated { implicit request => 
	  Ok(templates.fieldtrips.CreateFieldTrip(Binding(FieldTrips)))
	}
	
	def createFieldTripP = Authenticated { implicit req => 
	  Binding(FieldTrips, req) match {
			case ib: InvalidBinding => Ok(templates.fieldtrips.CreateFieldTrip(ib)) // there were errors
			case vb: ValidBinding => {
				val teacher: String = vb.valueOf(FieldTrips.teacher)
						val destination: String = vb.valueOf(FieldTrips.destination)
						val dates: String = vb.valueOf(FieldTrips.dates)
						val transportation: String = vb.valueOf(FieldTrips.transportation)
						val housing: String = vb.valueOf(FieldTrips.housing)
						// do whatever you want with the values now (notice they're typesafe!)
						Redirect(routes.App.fieldTripCreated())
			}
			}


	}
	
	def fieldTripCreated = Authenticated { implicit req =>
	  Ok(templates.fieldtrips.FieldTripCreated())  
	}
}