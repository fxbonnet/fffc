import org.specs2.mutable.Specification

import scala.io.Source

object ConverterSpec {
  val inputTest =
    "1970-01-01John           Smith           81.5\n" +
    "1975-01-31Jane           Doe             61.1\n" +
    "1988-11-28Bob            Big            102.4"
  val expectedCSV =
    "Birth date,First name,Last name,Weight\r\n" +
    "01/01/1970,John,Smith,81.5\r\n" +
    "31/01/1975,Jane,Doe,61.1\r\n" +
    "28/11/1988,Bob,Big,102.4\r\n"
}

class ConverterSpec extends Specification {
  import ConverterSpec._

  "Converter should" >> {
    val metadata = Metadata(Source.fromString(MetadataSpec.metadataTest))

    "convert the test input" >> {
      val converter = new Converter(metadata)
      val writer = CSVWriter()

      val csv = converter.convert(Source.fromString(inputTest), writer).mkString
      println(csv)
      csv mustEqual expectedCSV
    }
  }

}
