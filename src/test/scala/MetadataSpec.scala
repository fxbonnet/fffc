import org.specs2.mutable.Specification

import scala.io.Source
import Metadata._

object MetadataSpec {

  val metadataTest =
    "Birth date,10,date\n" +
    "First name,15,string\n" +
    "Last name,15,string\n" +
    "Weight,5,numeric"

  val metadata = Metadata(Seq(
    Column("Birth date", 10, Type.Date),
    Column("First name", 15, Type.String),
    Column("Last name", 15, Type.String),
    Column("Weight", 5, Type.Numeric)
  ))
}

class MetadataSpec extends Specification {
  import MetadataSpec._

  "Metadata should" >> {

    "construct a Metadata from a source" >> {
      Metadata(Source.fromString(metadataTest)) mustEqual metadata
    }
  }
}
