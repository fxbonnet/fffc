import java.text.SimpleDateFormat
import java.util.Date

import scala.io.Source
import scala.util.{Try,Success,Failure}
import Metadata._

trait Parser[T] {
  def read(source: Source, lineNum: Long): Try[T]
}

abstract class ColumnParser(definition: Column) {
  def readString(source: Source, lineNum: Long): Try[String] =
    Try(source.take(definition.length).mkString).withLineAndColumn(lineNum, definition)

  implicit class TryOps[T](cause: Try[T]) {
    def withLineAndColumn(l: Long, c: Column): Try[T] =
      cause.transform(s => Success(s), f => Failure(new ParserException(l, c, f)))
  }
}

class StringParser(definition: Column) extends ColumnParser(definition) with Parser[String] {
  override def read(source: Source, lineNum: Long): Try[String] = readString(source, lineNum)
}

class NumericParser(definition: Column) extends ColumnParser(definition) with Parser[BigDecimal] {
  override def read(source: Source, lineNum: Long): Try[BigDecimal] =
    readString(source, lineNum).flatMap(s => Try(BigDecimal(s.trim)).withLineAndColumn(lineNum, definition))
}

class DateParser(definition: Column, dateFormat: String) extends ColumnParser(definition) with Parser[Date] {
  override def read(source: Source, lineNum: Long): Try[Date] = {
    val format = new SimpleDateFormat(dateFormat)
    readString(source, lineNum: Long).flatMap(s => Try(format.parse(s.trim)).withLineAndColumn(lineNum, definition))
  }
}

class ParserException(lineNum: Long, column: Column, cause: Throwable)
  extends Exception(s"Parsing error on column '${column.name}' at line $lineNum: ${cause}", cause)