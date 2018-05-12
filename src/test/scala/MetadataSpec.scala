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

//  val inputTest =
//    "1970-01-01John           Smith           81.5\n" +
//    "1975-01-31Jane           Doe             61.1\n" +
//    "1988-11-28Bob            Big            102.4"
}

class MetadataSpec extends Specification {
  import MetadataSpec._

  "Metadata should" >> {

    "construct a Metadata from a source" >> {
      Metadata(Source.fromString(metadataTest)) mustEqual metadata
    }
  }
}
