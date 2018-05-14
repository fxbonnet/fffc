import java.util.{Calendar, Date}

trait TestUtils {

  def newDate(day: Int, month: Int, year: Int): Date = {
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
