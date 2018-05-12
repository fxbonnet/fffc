import org.specs2.mutable.Specification

import scala.io.Source

object MetadataSpec {

  val metadata =
    "Birth date,10,date\n" +
    "First name,15,string\n" +
    "Last name,15,string\n" +
    "Weight,5,numeric"
}

class MetadataSpec extends Specification {
  import MetadataSpec._
  import Metadata._

  "Metadata should" >> {

    "construct a Metadata from a source" >> {
      Metadata(Source.fromString(metadata)) mustEqual Metadata(Seq(
        Column("Birth date", 10, Type.Date),
        Column("First name", 15, Type.String),
        Column("Last name", 15, Type.String),
        Column("Weight", 5, Type.Numeric)
      ))
    }
  }
}
