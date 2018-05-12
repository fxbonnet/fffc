import java.text.SimpleDateFormat
import java.util.Date

import scala.io.Source
import scala.util.Try
import Metadata._

trait Parser[T] {
  def read(source: Source): Try[T]
}

object Parser {
  def apply(column: Column): Try[Parser[_]] = Try {
    column.`type` match {
      case Type.String => new StringParser(column)
      case Type.Numeric => new NumericParser(column)
      case Type.Date => new DateParser(column, "dd-MM-yyyy")
      case t => throw new IllegalArgumentException(s"No known parser for type: $t")
    }
  }
}

abstract class ColumnParser(definition: Column) {
  def readString(source: Source): Try[String] = Try(source.take(definition.length).mkString)
}

class StringParser(definition: Column) extends ColumnParser(definition) with Parser[String] {
  override def read(source: Source): Try[String] = readString(source)
}

class NumericParser(definition: Column) extends ColumnParser(definition) with Parser[BigDecimal] {
  override def read(source: Source): Try[BigDecimal] = readString(source).flatMap(s => Try(BigDecimal(s.trim)))
}

class DateParser(definition: Column, dateFormat: String) extends ColumnParser(definition) with Parser[Date] {
  override def read(source: Source): Try[Date] = {
    val format = new SimpleDateFormat(dateFormat)
    readString(source).flatMap(s => Try(format.parse(s)))
  }
}