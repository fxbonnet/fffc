import java.text.SimpleDateFormat
import java.util.Date

import scala.util.{Try, Success, Failure}
import Metadata.Column

trait Serialiser[T] {
  def write(value: Try[Any]): String
}

abstract class ColumnSerialiser(definition: Column, failOnError: Boolean = false, trim: Boolean = false) {
  def writeString(value: Try[String]): String = value match {
    case Success(v) => trimIfRequired(v)
    case Failure(t) if failOnError => throw t
    case Failure(t) => t.getMessage
  }

  private def trimIfRequired(str: String): String =
    if(trim)
      str.trim
    else
      str
}

class StringSerialiser(definition: Column, failOnError: Boolean = false, trim: Boolean = false) extends ColumnSerialiser(definition, failOnError, trim) with Serialiser[String] {
  override def write(value: Try[Any]): String = writeString(value.map(_.toString))
}

class NumericSerialiser(definition: Column, failOnError: Boolean = false) extends ColumnSerialiser(definition, failOnError) with Serialiser[BigDecimal] {
  override def write(value: Try[Any]): String = writeString(value.map(_.toString))
}

class DateSerialiser(definition: Column, dateFormat: String, failOnError: Boolean = false) extends ColumnSerialiser(definition, failOnError) with Serialiser[Date] {
  override def write(value: Try[Any]): String = {
    val format = new SimpleDateFormat(dateFormat)
    writeString(value.map(date => format.format(date)))
  }
}