import java.text.SimpleDateFormat
import java.util.Date

import scala.io.Source
import scala.util.{Try,Success,Failure}
import Metadata._

/**
  * Parser parses values from a text source
  * @tparam T the type of value parsed by this parser
  */
trait Parser[T] {
  /**
    * parse a value from a text source
    * @param source the text input stream to read from
    * @param lineNum the line number the source contains
    * @return the value read from the stream
    */
  def read(source: Source, lineNum: Long): Try[T]
}

/**
  * base class for parsers that use a Column as specification for the parsing
  * @param definition the column definition used by this parser
  */
abstract class ColumnParser(definition: Column) {
  def readString(source: Source, lineNum: Long): Try[String] =
    Try(source.take(definition.length).mkString).withLineAndColumn(lineNum, definition)

  implicit class TryOps[T](cause: Try[T]) {
    def withLineAndColumn(l: Long, c: Column): Try[T] =
      cause.transform(s => Success(s), f => Failure(new ParserException(l, c, f)))
  }
}

/**
  * Parse values of string-typed columns
  * @param definition the column definition used by this parser
  */
class StringParser(definition: Column) extends ColumnParser(definition) with Parser[String] {
  override def read(source: Source, lineNum: Long): Try[String] = readString(source, lineNum)
}

/**
  * Parse values of numeric-typed columns
  * @param definition the column definition used by this parser
  */
class NumericParser(definition: Column) extends ColumnParser(definition) with Parser[BigDecimal] {
  override def read(source: Source, lineNum: Long): Try[BigDecimal] =
    readString(source, lineNum).flatMap(s => Try(BigDecimal(s.trim)).withLineAndColumn(lineNum, definition))
}

/**
  * Parse values of date-typed columns
  * @param definition the column definition used by this parser
  */
class DateParser(definition: Column, dateFormat: String) extends ColumnParser(definition) with Parser[Date] {
  override def read(source: Source, lineNum: Long): Try[Date] = {
    val format = new SimpleDateFormat(dateFormat)
    format.setLenient(false)
    readString(source, lineNum: Long).flatMap(s => Try(format.parse(s.trim)).withLineAndColumn(lineNum, definition))
  }
}

/**
  * A parser exception
  * @param lineNum the line number the exception occurred
  * @param column the column for which the exception occurred
  * @param cause the exception
  */
class ParserException(lineNum: Long, column: Column, cause: Throwable)
  extends Exception(s"Parsing error on column '${column.name}' at line $lineNum: ${cause}", cause)