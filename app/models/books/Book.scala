package models.books

import config.users.UsesDataStore
import models.users.Permission
import java.io.File
import java.net.URL
import sys.process._
import javax.imageio.ImageIO

class Book {

}

object Book extends UsesDataStore {
  object Permissions {
    val Manage = Permission(classOf[Book], 0, "Manage", "can manage textbook system")
    val LookUp = Permission(classOf[Book], 1, "LookUp", "can look up info about books and users")
  }
  def addCovers() {
    val titles = dataStore.pm.query[Title].executeList.sortWith((c1, c2) => c1.name < c2.name)
    val isbns = titles.map(_.isbn)
    for (isbn <- isbns.take(40)) {
      addCoverByIsbn(isbn)
    }
  }
  
  def addCoverByIsbn(isbn: String) {
    val thumbnailUrl = new URL(s"http://covers.openlibrary.org/b/isbn/${isbn}-S.jpg")
    val bigUrl = new URL(s"http://covers.openlibrary.org/b/isbn/${isbn}-L.jpg")
    val thumbnail = ImageIO.read(thumbnailUrl)
    ImageIO.write(thumbnail, "jpg", new File(s"public/images/books/${isbn}-thumbnail.jpg"))
    val bigimg = ImageIO.read(bigUrl)
    ImageIO.write(bigimg, "jpg", new File(s"public/images/books/${isbn}.jpg"))
  }
}