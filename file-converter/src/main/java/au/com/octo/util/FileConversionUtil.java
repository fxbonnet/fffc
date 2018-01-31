package au.com.octo.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author l067757
 *
 */
/**
 * @author l067757
 *
 */
public class FileConversionUtil {

	private static final Logger logger = LoggerFactory.getLogger(FileConversionUtil.class);

	private static final String DATA_EMPTY = "The date is empty";

	private static final String DATEPATTERN_YYYY_MM_DD = "yyyy-MM-dd";

	private static final String DATEPATTERN_DD_MM_YYYY = "dd/MM/yyyy";

	private FileConversionUtil() {
		// empty constructor
	}

	/**
	 * Convert date string from yyyy-MM-dd  >> dd/MM/yyyy
	 * @param strDate
	 * @return
	 */
	public static String convertDateFormat(String strDate) {
		return convertDateToString(validateDate(strDate));
	}

	
	/**
	 * Parse date in yyyy-MM-dd format from date String
	 * @param strDate
	 * @return
	 */
	private static Date validateDate(String strDate) {
		try {
			if ( StringUtils.isBlank(strDate) ) {
				logger.warn(DATA_EMPTY);
				return null;
			}

			return getDateFormat(DATEPATTERN_YYYY_MM_DD).parse(strDate);

		} catch (ParseException e) {
			logger.warn("Invalid date format returned from UCM");
			return null;
		}
	}

	
	/**
	 * get  date string in dd/MM/yyyy style from date 
	 * @param date
	 * @return
	 */
	private static String convertDateToString(Date date) {
		if ( null == date ) {
			logger.warn(DATA_EMPTY);
			return "";
		}

		return getDateFormat(DATEPATTERN_DD_MM_YYYY).format(date);

	}

	/**
	 * Get Date Format
	 * @param fromFormat
	 * @return
	 */
	private static DateFormat getDateFormat(String fromFormat) {
		DateFormat dfFrom = new SimpleDateFormat(fromFormat);

		dfFrom.setLenient(false);

		return dfFrom;
	}

}
