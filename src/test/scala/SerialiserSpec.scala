import Metadata.{Column, Type}
import org.specs2.mutable.Specification

import scala.util.{Failure, Success}

class SerialiserSpec extends Specification with TestUtils {

  "Serialiser should" >> {

    "serialise a string" >> {
      val str = Success("this is a random string   ")
      new StringSerialiser(Column("string-typed column", 8, Type.String), trim = false).write(str) mustEqual "this is a random string   "
    }

    "serialise and trim a string" >> {
      val str = Success("this is a random string   ")
      new StringSerialiser(Column("string-typed column", 8, Type.String), trim = true).write(str) mustEqual "this is a random string"
    }

    "serialise a numeric" >> {
      val num = Success(BigDecimal("125.68"))
      new NumericSerialiser(Column("numeric-typed column", 10, Type.Numeric)).write(num) mustEqual "125.68"
    }

    "serialise a date" >> {
      val date = Success(newDate(27,11,2015))
      new DateSerialiser(Column("date-typed column", 10, Type.Date), "dd~MM~yyyy").write(date) mustEqual "27~11~2015"
    }

    "throw an error if there is no value available to serialise and failOnError is true" >> {
      val failure = new Exception("No value available due to some error")
      val failed = Failure(failure)
      new NumericSerialiser(Column("numeric-typed column", 10, Type.Date), false).write(failed) mustEqual(failure.getMessage)
    }

    "serialise the error if there is no value available to serialise and failOnError is false" >> {
      val failure = new Exception("No value available due to some error")
      val failed = Failure(failure)
      new NumericSerialiser(Column("numeric-typed column", 10, Type.Date), true).write(failed) must throwA(failure)
    }
  }
}
