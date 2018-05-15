import java.text.SimpleDateFormat

import scala.util.{Try, Success, Failure}
import Metadata.Column

/**
  * Serialiser serialises a value into a String
  */
trait Serialiser {
  /**
    * serialise a value into a String
    * @param value the value to serialise
    * @return the serialised value
    */
  def write(value: Try[Any]): String
}

/**
  * base class for serialisers that use a Column as specification for the serialisation
  * @param definition the column definition used by this serialiser
  * @param failOnError whether serialisation should fail if there is no value
  * @param trim whether the string result of serialisation should be trimmed
  */
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

/**
  * Serialise values from string-typed columns
  * @param definition the column definition used by this serialiser
  * @param failOnError whether serialisation should fail if there is no value
  * @param trim whether the string result of serialisation should be trimmed
  */
class StringSerialiser(definition: Column, failOnError: Boolean = false, trim: Boolean = false) extends ColumnSerialiser(definition, failOnError, trim) with Serialiser {
  override def write(value: Try[Any]): String = writeString(value.map(_.toString))
}

/**
  * Serialise values from numeric-typed columns
  * @param definition the column definition used by this serialiser
  * @param failOnError whether serialisation should fail if there is no value
  */

class NumericSerialiser(definition: Column, failOnError: Boolean = false) extends ColumnSerialiser(definition, failOnError) with Serialiser {
  override def write(value: Try[Any]): String = writeString(value.map(_.toString))
}

/**
  * Serialise values from date-typed columns
  * @param definition the column definition used by this serialiser
  * @param failOnError whether serialisation should fail if there is no value
  */
class DateSerialiser(definition: Column, dateFormat: String, failOnError: Boolean = false) extends ColumnSerialiser(definition, failOnError) with Serialiser {
  override def write(value: Try[Any]): String = {
    val format = new SimpleDateFormat(dateFormat)
    writeString(value.map(date => format.format(date)))
  }
}