import scala.io.Source
import scala.util.Try
import Metadata._

case class Metadata(columns: Seq[Column])

object Metadata {

  def apply(input: Source): Metadata = Metadata(
    input.getLines.foldRight(Seq.empty[Column]) {
      case (Column(col),acc) => col +: acc
      case (_, acc) => acc
    }
  )
  case class Column(name: String, length: Int, `type`: Type)

  object Column {
    val ColumnDefinition = "([^,]+),([^,]+),([^,]+).*".r

    def unapply(definition: String): Option[Column] = definition match {
      case ColumnDefinition(n, l, Type(t)) =>
        Try(Column(n, l.toInt, t)).toOption
      case _ => None
    }
  }

  type Type = Type.Value
  object Type extends Enumeration {
    val Date = Value("date")
    val String = Value("string")
    val Numeric = Value("numeric")

    def unapply(s: String): Option[Type] = values.find(_.toString == s)
  }
}