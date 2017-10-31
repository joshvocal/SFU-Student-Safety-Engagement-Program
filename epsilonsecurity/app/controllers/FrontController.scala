package controllers

import java.io.File

import com.google.inject.Inject
import com.typesafe.config.{Config, ConfigFactory}
import lib.WebpackBuildFile
import play.Environment
import play.api.mvc._
import models.DummyDatabase
import models.DummyDatabase.DummyDataBase

class FrontController @Inject()(cc: ControllerComponents, env: Environment) extends AbstractController(cc) {
  val config: Config = ConfigFactory.parseFile(new File("conf/frontend.conf")).resolve()

  def index(path: String) = Action {
    new DummyDataBase
    Ok(views.html.index.render(env, config.getInt("webpack.port"), WebpackBuildFile.jsBundle, WebpackBuildFile.cssBundle))
  }
}
