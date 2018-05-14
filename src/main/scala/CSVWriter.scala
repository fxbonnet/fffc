import java.io.StringWriter
import scala.collection.JavaConverters._
import org.apache.commons.csv.{CSVFormat, CSVPrinter}

object CSVWriter {
  val defaultFormat = CSVFormat
    .newFormat(',')
    .withTrim(true)
    .withRecordSeparator("\n")
    .withIgnoreEmptyLines()

  def apply(format: CSVFormat = defaultFormat) = new CSVWriter(format)
}

class CSVWriter(format: CSVFormat) {

  def write(cols: Seq[String]): String = {
    val writer = new StringWriter()
    val csv = new CSVPrinter(writer, format)
    try {
      csv.printRecord(cols.asJavaCollection)
      csv.flush()
    } finally {
      csv.close()
    }
    writer.toString
  }
}