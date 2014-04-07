package templates

import org.dupontmanual.forms.Binding
import config.users.Config
import controllers.users.VisitRequest

package object fieldtrips {
  object CreateFieldTrip {
    def apply(form: Binding)(implicit req: VisitRequest[_], config: Config) = {
      config.main("Create a New Field Trip")(form.render())
    }
  }
  
  object FieldTripCreated {
    def apply()(implicit req: VisitRequest[_], config: Config) = {
      config.main("Your field trip was created")()
    }
  }

}