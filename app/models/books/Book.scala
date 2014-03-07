package models.books

import config.users.UsesDataStore
import scala.language.postfixOps
import models.users.Permission
import java.io.File
import java.net.URL
import sys.process._

class Book {

}

object Book extends UsesDataStore {
  object Permissions {
    val Manage = Permission(classOf[Book], 0, "Manage", "can manage textbook system")
    val LookUp = Permission(classOf[Book], 1, "LookUp", "can look up info about books and users")
  }
  def addCoverByIsbn() = {
    val titles = dataStore.pm.query[Title].executeList.sortWith((c1, c2) => c1.name < c2.name)
    val isbns = titles.map(_.isbn)
    for (isbn <- isbns.take(40)) {
      new URL("http://covers.openlbrary.org/b/isbn/" + isbn + "-S.jpg") #> new File("public/images/books/" + isbn + "-thumbnail.jpg") !!;
      new URL("http://covers.openlbrary.org/b/isbn/" + isbn + "-L.jpg") #> new File("public/images/books/" + isbn + ".jpg") !!;
    }
  }
}