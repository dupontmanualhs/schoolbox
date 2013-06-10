package app

import org.junit.Test
import org.openqa.selenium.chrome._
import play.api.test._
import play.api.test.Helpers._
import org.openqa.selenium.{ WebDriver, By }
import org.specs2.mutable.Specification
import org.fluentlenium.core.filter.FilterConstructor._
import org.scalatest.FunSuite
import org.openqa.selenium.firefox.FirefoxDriver
import org.openqa.selenium.interactions.Actions

// This is a conflict

class TestLogins extends FunSuite {
  def driver = classOf[ChromeDriver]
/*
  test("have a log in menu available when you hover over the Accounts link") {
    running(TestServer(3333), driver) { browser =>
      browser.goTo("http://localhost:3333")
      assert(browser.title === "JCPS eSchool")
      val acct = browser.$("#menu_account").first
      assert(acct.getTagName === "a")
      assert(acct.getAttribute("href") === browser.url + "#")
      acct.click()
      browser.$("#menu_login").first.click()
      assert(browser.title === "Login")
      DataStore.close()
    }
  }

  test("allow a student to log in with the correct username and password") {
    running(TestServer(3333), driver) { browser =>
      browser.goTo("http://localhost:3333")
      assert(browser.title === "JCPS eSchool")
      browser.$("a", withText("Log in")).get(1).click
      assert(browser.title === "Login")
      browser.$("#id_username").first.text("john")
      browser.$("#id_password").first.text("kin123")
      browser.$("form").first.submit
      assert(browser.title === "JCPS eSchool")
      assert(browser.$("p").first.getText.startsWith("You are logged in as John King (Student)."))
      DataStore.close()
    }
  }

  test("not allow a user to log in with an incorrect password") {
    running(TestServer(3333), driver) { browser =>
      browser.goTo("http://localhost:3333/login")
      browser.$("#id_username").text("john")
      browser.$("#id_password").text("notkin123")
      browser.$("#id_password").submit
      // should stay on same page, display error, username is kept, password is cleared
      assert(browser.title === "Login")
      assert(browser.$(".errorlist").first.getText.contains("Incorrect username or password."))
      assert(browser.$("#id_username").first.getValue === "john")
      assert(browser.$("#id_password").first.getValue === "")
      DataStore.close()
    }
  }
  
  test("make a user with multiple perspectives choose one") {
    running(TestServer(3333), driver) { browser =>
      browser.goTo("http://localhost:3333/login")
      browser.$("#id_username").text("todd")
      browser.$("#id_password").text("obr123")
      browser.$("#id_password").submit
      assert(browser.title === "Choose Perspective")
      browser.$("#id_perspective").click
      browser.$("#id_perspective").submit
      // return to same page with error
      assert(browser.title === "Choose Perspective")
      assert(browser.$(".errorlist").first.getText.contains("This field is required. Please choose a value."))
      DataStore.close()
    }
  }
  
  test("user can change his/her password") {
    running(TestServer(3333), driver) { browser =>
      browser.goTo("http://localhost:3333/login")
      browser.$("#id_username").text("todd")
      browser.$("#id_password").text("obr123")
      browser.$("#id_password").submit
      assert(browser.title === "Choose Perspective")
      browser.$("#id_perspective").click
      browser.$("#id_perspective").submit
      browser.$("#menu_account").click
      browser.$("#menu_changePassword").click
      assert(browser.title === "Change Your Password")
      browser.$("#id_currentPassword").text("obr123")
      browser.$("#id_newPassword").text("obr456")
      browser.$("#id_verifyNewPassword").text("obr456")
      browser.$("#id_verifyNewPassword").submit
    }
  }
  
  test("user with permission can change others' passwords") {
    
  }
*/  
}
