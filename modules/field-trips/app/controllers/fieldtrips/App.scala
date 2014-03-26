package controllers.fieldtrips

import org.dupontmanual.forms.Binding
import org.dupontmanual.forms.Form
import org.dupontmanual.forms.InvalidBinding
import org.dupontmanual.forms.ValidBinding
import org.dupontmanual.forms.fields._
import models.fieldtrips.Transport
import models.fieldtrips.Housing
import com.google.inject.Inject
import com.google.inject.Singleton

import config.users.Config
import config.users.UsesDataStore
import controllers.users.Authenticated
import models.courses._
import models.courses.Teacher.TeacherField
import models.courses.Teacher.TeacherList
import models.fieldtrips.FieldTrip
import play.api.mvc.Controller
import play.api.mvc.Flash._

@Singleton
class App @Inject()(implicit config: Config) extends Controller with UsesDataStore {
	object FieldTrips extends Form{
		val teacher = new TeacherField("Teacher", TeacherList.teacherIds)
		val id = new TextField("ID")
		val destination = new TextField("Destination")
		val startDate = new DateField("Start Date")
		val endDate = new DateField("End Date")
		val transportation = new ChoiceField("transportation", Transport.values.toList.map(v => (v.toString, v)))
		val housing = new ChoiceField("Housing", Housing.values.toList.map(v => (v.toString, v)))
		
		val fields = List(teacher, destination, startDate, endDate, transportation, housing)
	}

	def createFieldTrip = Authenticated { implicit request => 
	  Ok(templates.fieldtrips.CreateFieldTrip(Binding(FieldTrips)))
	}
	
	def createFieldTripP = Authenticated { implicit req => 
	  Binding(FieldTrips, req) match {
			case ib: InvalidBinding => Ok(templates.fieldtrips.CreateFieldTrip(ib)) // there were errors
			case vb: ValidBinding => dataStore.execute { implicit pm =>
				val teacher: Teacher = vb.valueOf(FieldTrips.teacher)
						val destination: String = vb.valueOf(FieldTrips.destination)
						val startDate = vb.valueOf(FieldTrips.startDate)
						val endDate = vb.valueOf(FieldTrips.endDate)
						val transportation = vb.valueOf(FieldTrips.transportation)
						val housing = vb.valueOf(FieldTrips.housing)
						// do whatever you want with the values now (notice they're typesafe!)
						
						val ft = new FieldTrip(vb.valueOf(FieldTrips.destination), 
						    vb.valueOf(FieldTrips.teacher), vb.valueOf(FieldTrips.startDate),vb.valueOf(FieldTrips.endDate), 
						    vb.valueOf(FieldTrips.transportation), vb.valueOf(FieldTrips.housing))
					    pm.makePersistent(ft)
					    Redirect(routes.App.fieldTripCreated())
			}
			}


	}
	
	def fieldTripCreated = Authenticated { implicit req =>
	  Ok(templates.fieldtrips.FieldTripCreated())  
	}
}