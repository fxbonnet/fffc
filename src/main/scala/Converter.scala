import Metadata.{Column, Type}

import scala.io.Source
import scala.util.Try

/**
  * Converter converts a fixed-width field text input into a csv output
  * @param metadata the definition of the expected input format
  */
class Converter(metadata: Metadata) {

  type InputLine  = Seq[Try[Any]]   // sequence of parsed column values or failure
  type OutputLine = Seq[String]     // sequence of serialised columns values

  val parsers     = metadata.columns.map(configureParsersOrFail)
  val serialisers = metadata.columns.map(configureSerialisersOrFail)

  /**
    * read the source and convert it to csv
    * @param source the text input stream
    * @param csv the csv writer used to produce the output
    * @return the converted csv output
    */
  def convert(source: Source, csv: CSVWriter): Stream[String] = {
    val in = parse(source)
    val out = transform(in)
    out.map(csv.write)
  }

  /**
    * parse a source text input stream into a stream of typed values
    * @param source the source text input stream
    * @return the parsed stream of lines (Seq) of values
    */
  def parse(source: Source): Stream[InputLine] =
    source.getLines.toStream.zipWithIndex.map(parseLine(parsers))

  /**
    * parse a row of values
    * @param parsers the parsers to be used (ordered)
    * @param l the line to parse and its associated line number
    * @return the parsed values (ordered)
    */
  def parseLine(parsers: Seq[Parser[_]])(l: (String, Int)): InputLine = {
    val (line, lineNum) = l
    val source = Source.fromString(line)
    parsers.map(_.read(source, lineNum + 1))
  }

  /**
    * serialise a source stream of typed values into a stream of their text representation
    * @param source the source values
    * @return the stream of lines (Seq) of serialised values
    */
  def transform(source: Stream[InputLine]): Stream[OutputLine] = {
    val headers = metadata.columns.map(_.name)
    headers #:: source.map(transformLine(serialisers))
  }

  /**
    * write a row of values
    * @param serialisers the serialisers to be used (ordered)
    * @param source the source values (ordered)
    * @return the serialised values (ordered)
    */
  def transformLine(serialisers: Seq[Serialiser])(source: InputLine): OutputLine = {
    serialisers.zip(source).map {
      case (serialiser,value) => serialiser.write(value)
    }
  }

  /**
    * configure a parser used  to read values for a given column
    * @param column the column definition
    * @return the configured parser
    */
  private def configureParsersOrFail(column: Column): Parser[_] = column.`type` match {
    case Type.String => new StringParser(column)
    case Type.Numeric => new NumericParser(column)
    case Type.Date => new DateParser(column, dateFormat = "yyyy-MM-dd")
    case t => throw new IllegalArgumentException(s"No known parser for type: $t")
  }

  /**
    * configure a serialiser to write values for a given column
    * @param column the column definition
    * @return the configured serialiser
    */
  private def configureSerialisersOrFail(column: Column): Serialiser = column.`type` match {
    case Type.String => new StringSerialiser(column, failOnError = true, trim = true)
    case Type.Numeric => new NumericSerialiser(column, failOnError = true)
    case Type.Date => new DateSerialiser(column, dateFormat = "dd/MM/yyyy", failOnError = true)
    case t => throw new IllegalArgumentException(s"No known serialiser for type: $t")
  }
}
