import scala.io.Source


class ParserSpec extends Specification {
  import ParserSpec._

  "Metadata should" >> {

    "construct a Metadata from a source" >> {
      Metadata(Source.fromString(metadataTest)) mustEqual metadata
    }
  }
}
