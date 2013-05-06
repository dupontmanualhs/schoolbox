package models.blogs

import javax.jdo.annotations._
import org.datanucleus.query.typesafe._
import org.datanucleus.api.jdo.query._
import models.users.Perspective
import util.ScalaPersistenceManager
import util.DataStore

@PersistenceCapable(detachable="true")
@Unique(members=Array("_owner", "_title"))
class Blog {
  @PrimaryKey
  @Persistent(valueStrategy=IdGeneratorStrategy.INCREMENT)
  private[this] var _id: Long = _

  @Column(allowsNull="false")
  private[this] var _title: String = _

  @Column(allowsNull="false")
  @Persistent(defaultFetchGroup="true")
  private[this] var _owner: Perspective = _

  def this(title: String, owner: Perspective) = {
    this()
    _title = title
    _owner = owner
  }

  def id: Long = _id

  def title: String = _title
  def title_=(theTitle: String) { _title = theTitle }

  def owner: Perspective = _owner

  def createPost(title: String, content: String)(implicit pm: ScalaPersistenceManager = null) {
    val p = new Post(title, content, this)
    if(pm != null) pm.makePersistent(p)
    else DataStore.withTransaction( tpm => tpm.makePersistent(p))
  }
}

trait QBlog extends PersistableExpression[Blog] {
  private[this] lazy val _id: NumericExpression[Long] = new NumericExpressionImpl[Long](this, "_id")
  def id: NumericExpression[Long] = _id
  
  private[this] lazy val _title: StringExpression = new StringExpressionImpl(this, "_title")
  def title: StringExpression = _title
  
  private[this] lazy val _owner: ObjectExpression[Perspective] = new ObjectExpressionImpl[Perspective](this, "_owner")
  def owner: ObjectExpression[Perspective] = _owner
}

object QBlog {
  def apply(parent: PersistableExpression[_], name: String, depth: Int): QBlog = {
    new PersistableExpressionImpl[Blog](parent, name) with QBlog
  }
  
  def apply(cls: Class[Blog], name: String, exprType: ExpressionType): QBlog = {
    new PersistableExpressionImpl[Blog](cls, name, exprType) with QBlog
  }
  
  private[this] lazy val jdoCandidate: QBlog = candidate("this")
  
  def candidate(name: String): QBlog = QBlog(null, name, 5)
  
  def candidate: QBlog = jdoCandidate
  
  def parameter(name: String): QBlog = QBlog(classOf[Blog], name, ExpressionType.PARAMETER)
  
  def variable(name: String): QBlog = QBlog(classOf[Blog], name, ExpressionType.VARIABLE)
}

object Blog {
  def listUserBlogs(perspective: Perspective)(implicit pm: ScalaPersistenceManager): List[Blog] = {
    val realPer = Perspective.getById(perspective.id)
    realPer match {
      case None => Nil
      case Some(realPer) => {
         val cand = QBlog.candidate
         pm.query[Blog].filter(cand.owner.eq(realPer)).executeList
      }
    }
  }

  def getById(id: Long)(implicit pm: ScalaPersistenceManager = null): Option[Blog] = {
    def query(epm: ScalaPersistenceManager) = {
      val cand = QBlog.candidate
      epm.query[Blog].filter(cand.id.eq(id)).executeOption
    }
    if (pm != null) query(pm)
    DataStore.withTransaction( tpm => query(tpm))
  }

  def getPosts(blog: Blog)(implicit pm: ScalaPersistenceManager): List[Post] = {
    val cand = QPost.candidate
    pm.query[Post].filter(cand.blog.eq(blog)).executeList
  }
}
