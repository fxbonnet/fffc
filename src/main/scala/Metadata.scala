import scala.io.Source
import scala.util.Try
import Metadata._

/**
  * the definition of each columns of a fixed-width text input file
  * @param columns the columns definition
  */
case class Metadata(columns: Seq[Column])

object Metadata {

  /**
    * Parse a Metadata definition
    * @param input the text definition of the metadata
    * @return an instance of Metadata
    */
  def apply(input: Source): Metadata = Metadata(
    input.getLines.foldRight(Seq.empty[Column]) {
      case (Column(col),acc) => col +: acc
      case (_, acc) => acc
    }
  )

  /**
    * the definition of a column of a fixed-width text input file
    * @param name the column name
    * @param length the column width in characters
    * @param `type` the column type
    */
  case class Column(name: String, length: Int, `type`: Type)

  // FIXME parse with apache commons csv for a more robust solution
  object Column {
    val ColumnDefinition = "([^,]+),([^,]+),([^,]+).*".r

    def unapply(definition: String): Option[Column] = definition match {
      case ColumnDefinition(n, l, Type(t)) =>
        Try(Column(n, l.toInt, t)).toOption
      case _ => None
    }
  }

  type Type = Type.Value

  /**
    * supported column types
    */
  object Type extends Enumeration {
    val Date = Value("date")
    val String = Value("string")
    val Numeric = Value("numeric")

    def unapply(s: String): Option[Type] = values.find(_.toString == s)
  }
}