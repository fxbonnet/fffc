import java.util.{Calendar, Date}

import org.specs2.mutable.Specification

import scala.io.Source
import Metadata._

import scala.util.Success

class ParserSpec extends Specification {

  "Parser should" >> {

    "parse a string" >> {
      val str = Source.fromString("abcdefghijklmnopqrstuvwxyz")
      Parser(Column("string-typed column", 8, Type.String)).flatMap(_.read(str)) mustEqual Success("abcdefgh")
    }

    "parse a numeric" >> {
      val num = Source.fromString("    -134.5abcdefg")
      Parser(Column("numeric-typed column", 10, Type.Numeric)).flatMap(_.read(num)) mustEqual Success(BigDecimal("-134.5"))
    }

    "parse a date" >> {
      val date = Source.fromString("27-11-2015xyz")
      Parser(Column("date-typed column", 10, Type.Date)).flatMap(_.read(date)) mustEqual Success(newDate(27,11,2015))
    }
  }

  private def newDate(day: Int, month: Int, year: Int): Date = {
    val c = Calendar.getInstance()
    c.set(Calendar.DAY_OF_MONTH, day)
    c.set(Calendar.MONTH, month - 1)
    c.set(Calendar.YEAR, year)
    c.set(Calendar.MILLISECOND, 0);
    c.set(Calendar.SECOND, 0);
    c.set(Calendar.MINUTE, 0);
    c.set(Calendar.HOUR_OF_DAY, 0);
    c.getTime
  }
}
