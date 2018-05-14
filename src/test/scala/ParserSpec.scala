import java.util.{Calendar, Date}

import org.specs2.mutable.Specification

import scala.io.Source
import Metadata._

import scala.util.Success

class ParserSpec extends Specification with TestUtils {

  "Parser should" >> {

    "parse a string" >> {
      val str = Source.fromString("abcdefghijklmnopqrstuvwxyz")
      new StringParser(Column("string-typed column", 8, Type.String)).read(str, 1) mustEqual Success("abcdefgh")
    }

    "parse a numeric" >> {
      val num = Source.fromString("    -134.5abcdefg")
      new NumericParser(Column("numeric-typed column", 10, Type.Numeric)).read(num, 1) mustEqual Success(BigDecimal("-134.5"))
    }

    "parse a date" >> {
      val date = Source.fromString("27-11-2015xyz")
      new DateParser(Column("date-typed column", 10, Type.Date), "dd-MM-yyyy").read(date, 1) mustEqual Success(newDate(27,11,2015))
    }

    "record a parsing failure line and column information" >> {
      val num = Source.fromString("a457b.8c")
      new NumericParser(Column("numeric-typed column", 8, Type.Numeric)).read(num, 57) must
        beFailedTry.withThrowable[ParserException]("Parsing error on column 'numeric-typed column' at line 57: java.lang.NumberFormatException")
    }
  }
}
