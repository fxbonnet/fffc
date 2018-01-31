package au.com.octo.validation;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import au.com.octo.constants.FileConversionConstants;

public class FileConversionValidations {

	private static final Logger logger = LoggerFactory.getLogger(FileConversionValidations.class);
	
	private FileConversionValidations() {
		//empty constructor
	}
	
	/**
	 * Validations for Column Metadata
	 * 1. There should be only 3 types of metadata for a column : Name, Length, Type
	 * 2. Name shouldn't be blank
	 * 3. Length should be number
	 * 4. Data type should be Date/Numeric/String
	 * @param column
	 */
	public static void metadataColumnFormatValidation(String[] column) {
		if ( column.length != 3 || StringUtils.isBlank(column[0]) || !NumberUtils.isNumber(column[1])
				|| !FileConversionConstants.DATA_TYPE.contains(column[2]) ) {
			logger.debug("Invalid MetData File Format");
			throw new RuntimeException();
		}
	}
}
