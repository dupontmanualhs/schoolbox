package controllers

import play.api.mvc.Controller
import play.api.mvc.Action
import controllers.users.VisitAction
import com.google.inject.{ Inject, Singleton }
import config.Config
import com.typesafe.scalalogging.slf4j.Logging

@Singleton
class App @Inject()(implicit config: Config) extends Controller with Logging{
  def index() = VisitAction { implicit req =>
    logger.debug("Loading The Index Page.")
      Ok(templates.Index())
  }
  
  def stub() = VisitAction { implicit req =>
    logger.debug("This page does not exist")
      Ok(templates.Stub())
  }
}


