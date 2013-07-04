package models.users

import javax.jdo.annotations._
import org.datanucleus.api.jdo.query._
import org.datanucleus.query.typesafe._
import scalajdo.DataStore
import play.api.mvc.Request

@PersistenceCapable(detachable="true")
class User extends Ordered[User] {
  @PrimaryKey
  @Persistent(valueStrategy=IdGeneratorStrategy.INCREMENT)
  private[this] var _id: Long = _
  def id: Long = _id

  @Unique
  @Column(allowsNull="false")
  private[this] var _username: String = _
  def username: String = _username
  def username_=(theUsername: String) { _username = theUsername }
  
  @Column(allowsNull="false")
  private[this] var _first: String = _
  def first: String = _first
  def first_=(theFirst: String) { _first = theFirst }
  
  private[this] var _middle: String = _
  def middle: Option[String] = if (_middle == null) None else Some(_middle)
  def middle_=(theMiddle: String) { _middle = theMiddle }
  
  @Column(allowsNull="false")
  private[this] var _last: String = _
  def last: String = _last
  def last_=(theLast: String) { _last = theLast }
  
  private[this] var _preferred: String = _
  def preferred: Option[String] = if (_preferred == null) None else Some(_preferred)
  def preferred_=(thePreferred: Option[String]) { _preferred = thePreferred.getOrElse(null) }
  
  private[this] var _gender: Int = _
  def gender: Gender.Gender = Gender(_gender)
  def gender_=(theGender: Gender.Gender) { _gender = theGender.id }
  
  private[this] var _theme: String = _
  def theme: String = _theme
  def theme_=(theTheme: String) {_theme = theTheme}

  @Persistent(defaultFetchGroup="true")
  @Embedded
  @Unique
  private[this] var _email: Email = _
  def email: Option[String] = if (_email == null) None else Some(_email.value)
  def email_=(theEmail: Email) { _email = theEmail }
  def email_=(theEmail: Option[String]) {
    if (theEmail.isDefined) email = new Email(theEmail.get)
    else _email = null
  }
  def email_=(theEmail: String) { email = Some(theEmail) }

  @Persistent(defaultFetchGroup="true")
  @Embedded
  private[this] var _password: Password = _
  def password: Password = _password
  def password_=(thePassword: Password) { _password = thePassword }
  def password_=(thePassword: String) { _password = new Password(thePassword) }
  
  def this(username: String, first: String, middle: Option[String], last: String,
      preferred: Option[String], gender: Gender.Gender, email: String, password: String) = {
    this()
    username_=(username)
    first_=(first)
    if (middle.isDefined) middle_=(middle.get)
    last_=(last)
    preferred_=(preferred)
    gender_=(gender)
    email_=(new Email(email))
    password_=(new Password(password))
    theme_=("default")
  }
  
  override def toString: String = s"User(ID: ${id}, ${formalName})"
  
  def displayName: String = "%s %s".format(preferred.getOrElse(first), last)
  
  def formalName: String = "%s, %s%s".format(last, first, middle.map(" " + _).getOrElse(""))
  
  def shortName: String = "%s, %s.".format(last, first.substring(0, 1))
  
  def compare(that: User): Int = {
    Ordering.Tuple3(Ordering.String, Ordering.String, Ordering.String).compare(
      (last, first, middle.getOrElse("")),
      (that.last, that.first, that.middle.getOrElse("")))
  }
  
  def roles(): List[Role] = {
    val cand = QRole.candidate
    DataStore.pm.query[Role].filter(cand.user.eq(this)).executeList()
  }
  
  def canEqual(that: Any): Boolean = that.isInstanceOf[User]
  
  override def equals(that: Any): Boolean = that match {
    case that: User => this.canEqual(that) && this.id == that.id
    case _ => false
  }
  
  override def hashCode: Int = this.id.hashCode
}

object User {
  object Permissions {
    val Add = Permission(classOf[User], 0, "Add", "can add new users")
    val Delete = Permission(classOf[User], 1, "Delete", "can delete users")
    val Change = Permission(classOf[User], 2, "Change", "can modify anything about users")
    val ListAll = Permission(classOf[User], 3, "ListAll", "can view the list of all users")
    val ChangePassword = Permission(classOf[User], 4, "ChangePassword", "can change other users' passwords")
  }
  
  def getById(id: Long): Option[User] = {
    val cand = QUser.candidate
    DataStore.pm.query[User].filter(cand.id.eq(id)).executeOption()
  }

  def getByUsername(username: String): Option[User] = {
    val cand = QUser.candidate
    DataStore.pm.query[User].filter(cand.username.eq(username)).executeOption()
  }
  
  def authenticate(username: String, password: String): Option[User] = {
    getByUsername(username) match {
	  case Some(user) => authenticate(user, password)
      case _ => None
	}
  }

  def authenticate(user: User, password: String): Option[User] = {
    if (user.password.matches(password)) {
      Some(user)
    } else {
      None
    }
  }
}

trait QUser extends PersistableExpression[User] {
  private[this] lazy val _id: NumericExpression[Long] = new NumericExpressionImpl[Long](this, "_id")
  def id: NumericExpression[Long] = _id

  private[this] lazy val _username: StringExpression = new StringExpressionImpl(this, "_username")
  def username: StringExpression = _username
  
  private[this] lazy val _first: StringExpression = new StringExpressionImpl(this, "_first")
  def first: StringExpression = _first
  
  private[this] lazy val _middle: StringExpression = new StringExpressionImpl(this, "_middle")
  def middle: StringExpression = _middle
  
  private[this] lazy val _last: StringExpression = new StringExpressionImpl(this, "_last")
  def last: StringExpression = _last
  
  private[this] lazy val _preferred: StringExpression = new StringExpressionImpl(this, "_preferred")
  def preferred: StringExpression = _last
  
  // TODO: need a class for EnumerationExpressions
  //private[this] lazy val _gender: ObjectExpression[Gender] = new ObjectExpressionImpl[Gender](this, "_gender")
  //def gender: ObjectExpression[Gender] = _gender
  
  private[this] lazy val _theme: StringExpression = new StringExpressionImpl(this, "_theme")
  def theme: StringExpression = _theme
  
  private[this] lazy val _email: ObjectExpression[Email] = new ObjectExpressionImpl[Email](this, "_email")
  def email: ObjectExpression[Email] = _email
  
  private[this] lazy val _password: ObjectExpression[Password] = new ObjectExpressionImpl[Password](this, "_password")
  def password: ObjectExpression[Password] = _password
  
}

object QUser {
  def apply(parent: PersistableExpression[User], name: String, depth: Int): QUser = {
    new PersistableExpressionImpl[User](parent, name) with QUser
  }
  
  def apply(cls: Class[User], name: String, exprType: ExpressionType): QUser = {
    new PersistableExpressionImpl[User](cls, name, exprType) with QUser
  }
  
  private[this] lazy val jdoCandidate: QUser = candidate("this")
  
  def candidate(name: String): QUser = QUser(null, name, 5)
  
  def candidate(): QUser = jdoCandidate

  def parameter(name: String): QUser = QUser(classOf[User], name, ExpressionType.PARAMETER)
  
  def variable(name: String): QUser = QUser(classOf[User], name, ExpressionType.VARIABLE)

}
