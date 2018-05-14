import Metadata.{Column, Type}

import scala.io.Source
import scala.util.Try

class Converter(metadata: Metadata) {

  type InputLine  = Seq[Try[Any]]   // sequence of parsed column values or failure
  type OutputLine = Seq[String]     // sequence of serialised columns values

  val parsers     = metadata.columns.map(configureParsersOrFail)
  val serialisers = metadata.columns.map(configureSerialisersOrFail)

  def convert(source: Source, csv: CSVWriter): Stream[String] = {
    val in = parse(source)
    val out = transform(in)
    out.map(csv.write)
  }

  def parse(source: Source): Stream[InputLine] =
    source.getLines.toStream.zipWithIndex.map(parseLine(parsers))


  def parseLine(parsers: Seq[Parser[_]])(l: (String, Int)): InputLine = {
    val (line, lineNum) = l
    val source = Source.fromString(line)
    parsers.map(_.read(source, lineNum + 1))
  }

  def transform(source: Stream[InputLine]): Stream[OutputLine] = {
    val headers = metadata.columns.map(_.name)
    headers #:: source.map(transformLine(serialisers))
  }

  def transformLine(serialisers: Seq[Serialiser[_]])(source: InputLine): OutputLine = {
    serialisers.zip(source).map {
      case (serialiser,value) => serialiser.write(value)
    }
  }

  private def configureParsersOrFail(column: Column): Parser[_] = column.`type` match {
    case Type.String => new StringParser(column)
    case Type.Numeric => new NumericParser(column)
    case Type.Date => new DateParser(column, dateFormat = "yyyy-MM-dd")
    case t => throw new IllegalArgumentException(s"No known parser for type: $t")
  }

  private def configureSerialisersOrFail(column: Column): Serialiser[_] = column.`type` match {
    case Type.String => new StringSerialiser(column, failOnError = true, trim = true)
    case Type.Numeric => new NumericSerialiser(column, failOnError = true)
    case Type.Date => new DateSerialiser(column, dateFormat = "dd/MM/yyyy", failOnError = true)
    case t => throw new IllegalArgumentException(s"No known serialiser for type: $t")
  }
}
