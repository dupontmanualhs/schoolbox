package models.grades

import java.sql.Date
import javax.jdo.annotations._
import org.datanucleus.api.jdo.query._
import org.datanucleus.query.typesafe._
import util.ScalaPersistenceManager
import util.PersistableFile
import util.DataStore
import models.courses.Section

@PersistenceCapable(detachable="true")
class Assignment {
  @PrimaryKey
  @Persistent(valueStrategy=IdGeneratorStrategy.INCREMENT)
  private[this] var _id: Long = _
  private[this] var _name: String = _
  private[this] var _section: Section = _
  private[this] var _points: Int = _
  private[this] var _post: java.sql.Date = _
  private[this] var _due: java.sql.Date = _
  private[this] var _category: Category = _
  
  def this(name: String, points: Int, post: java.sql.Date, due: java.sql.Date, category: Category) {
    this()
    _name = name
    _points = points
    _post = post
    _due = due
    _category = category
  }
  
  def id: Long = _id
  
  def name: String = _name
  def name_=(theName: String) { _name = theName }
  
  def points: Int = _points
  def points_=(thePoints: Int) { _points = thePoints }
  
  def post: java.sql.Date = _post
  def post_=(thePost: java.sql.Date) { _post = thePost }
  
  def due: java.sql.Date = _due
  def due_=(theDue: java.sql.Date) { _due = theDue }
  
  def category: Category = _category
  def category_=(theCategory: Category) { _category = theCategory }
}

object Assignment {
  
}

trait QAssignment extends PersistableExpression[Assignment] {
  private[this] lazy val _id: NumericExpression[Long] = new NumericExpressionImpl[Long](this, "_id")
  def id: NumericExpression[Long] = _id
  
  private[this] lazy val _name: StringExpression = new StringExpressionImpl(this, "_name")
  def name: StringExpression = _name

  private[this] lazy val _points: NumericExpression[Int] = new NumericExpressionImpl[Int](this, "_points")
  def points: NumericExpression[Int] = _points
  
  private[this] lazy val _post: ObjectExpression[java.sql.Date] = new ObjectExpressionImpl[java.sql.Date](this, "_post")
  def post: ObjectExpression[java.sql.Date] = _post
  
  private[this] lazy val _due: ObjectExpression[java.sql.Date] = new ObjectExpressionImpl[java.sql.Date](this, "_due")
  def due: ObjectExpression[java.sql.Date] = _due
  
  private[this] lazy val _category: ObjectExpression[Category] = new ObjectExpressionImpl[Category](this, "_category")
  def category: ObjectExpression[Category] = _category
}

object QAssignment {
  def apply(parent: PersistableExpression[Assignment], name: String, depth: Int): QAssignment = {
    new PersistableExpressionImpl[Assignment](parent, name) with QAssignment
  }
  
  def apply(cls: Class[Assignment], name: String, exprType: ExpressionType): QAssignment = {
    new PersistableExpressionImpl[Assignment](cls, name, exprType) with QAssignment
  }
  
  private[this] lazy val jdoCandidate: QAssignment = candidate("this")
  
  def candidate(name: String): QAssignment = QAssignment(null, name, 5)
  
  def candidate(): QAssignment = jdoCandidate
  
  def parameter(name: String): QAssignment = QAssignment(classOf[Assignment], name, ExpressionType.PARAMETER)
  
  def variable(name: String): QAssignment = QAssignment(classOf[Assignment], name, ExpressionType.VARIABLE)
}
