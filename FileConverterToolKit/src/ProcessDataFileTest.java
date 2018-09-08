import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.LinkedHashMap;
import java.util.Map;

import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class ProcessDataFileTest {
	private static MetaDataDO metaDataDO;
	
	
	@BeforeClass
	public static void runOnceBeforeClass() {
		// initialize the meta data do
		Map<String, Integer> columnDataLengthMap = new LinkedHashMap<String, Integer>();
		Map<String, String> columnDataTypeMap = new LinkedHashMap<String, String>();

		columnDataLengthMap.put("Name", 25);
		columnDataLengthMap.put("Joining_Date", 10);
		columnDataLengthMap.put("Age---", 5);

		columnDataTypeMap.put("Name", "String");
		columnDataTypeMap.put("Joining_Date", "date");
		columnDataTypeMap.put("Age---", "numeric");

		metaDataDO = new MetaDataDO();
		metaDataDO.setColumnDataLengthMap(columnDataLengthMap);
		metaDataDO.setColumnDataTypeMap(columnDataTypeMap);
		metaDataDO.setTotalLengthPerLine(40);

	}
	
	/******************************************************************************************************/
	/******************************************************************************************************/
	/** Methods to test the validateNumeric method */
	
	@Rule
	public ExpectedException expectedEx = ExpectedException.none();
	@Test
	public void validateNumericTestBlank() {
		// Use reflections to test the private method.
		String errorMsg = "The numeric:  on line 0 did not meet the standard numeric format. The data file cannot be processed. ";
		ProcessDataFile metaTest = new ProcessDataFile("","",metaDataDO);
		Class<?>[] classArray = new Class<?>[2];
		classArray[0] = java.lang.String.class;
		classArray[1] = int.class;
		Method method = null;
		try {
			method = metaTest.getClass().getDeclaredMethod(
					"validateNumeric", classArray);
			method.setAccessible(true);

		} catch (NoSuchMethodException | SecurityException e) {
			e.printStackTrace();
		}

		String number = "";
		int linecount = 0;
		Object[] param = { number,linecount };
		try {
			method.invoke(metaTest, param);
			expectedEx.expect(RuntimeException.class);
			expectedEx
					.expectMessage(errorMsg);


		} catch (IllegalAccessException | IllegalArgumentException
				| InvocationTargetException e) {
			if (e.getCause()
					.getMessage()
					.endsWith(errorMsg)) {
				// do nothing.
			} else {
				e.printStackTrace();
			}

		}
	}

	@Test
	public void validateNumericTestSpl() {
		// Use reflections to test the private method.
		String errorMsg = "The numeric: 1\\ on line 0 did not meet the standard numeric format. The data file cannot be processed. ";
		ProcessDataFile metaTest = new ProcessDataFile("","",metaDataDO);
		Class<?>[] classArray = new Class<?>[2];
		classArray[0] = java.lang.String.class;
		classArray[1] = int.class;
		Method method = null;
		try {
			method = metaTest.getClass().getDeclaredMethod(
					"validateNumeric", classArray);
			method.setAccessible(true);

		} catch (NoSuchMethodException | SecurityException e) {
			e.printStackTrace();
		}

		String number = "1\\";
		int linecount = 0;
		Object[] param = { number,linecount };
		try {
			method.invoke(metaTest, param);
			expectedEx.expect(RuntimeException.class);
			expectedEx
					.expectMessage(errorMsg);


		} catch (IllegalAccessException | IllegalArgumentException
				| InvocationTargetException e) {
			if (e.getCause()
					.getMessage()
					.endsWith(errorMsg)) {
				// do nothing.
			} else {
				e.printStackTrace();
			}

		}
	}
	
	@Test
	public void validateNumericTestWhole() {
		// Use reflections to test the private method.
		ProcessDataFile metaTest = new ProcessDataFile("","",metaDataDO);
		Class<?>[] classArray = new Class<?>[2];
		classArray[0] = java.lang.String.class;
		classArray[1] = int.class;
		Method method = null;
		try {
			method = metaTest.getClass().getDeclaredMethod(
					"validateNumeric", classArray);
			method.setAccessible(true);

		} catch (NoSuchMethodException | SecurityException e) {
			e.printStackTrace();
		}

		String number = "100";
		int linecount = 0;
		Object[] param = { number,linecount };
		try {
			method.invoke(metaTest, param);
			


		} catch (IllegalAccessException | IllegalArgumentException
				| InvocationTargetException e) {
			e.printStackTrace();

		}
	}
	
	@Test
	public void validateNumericTestNeg() {
		// Use reflections to test the private method.
		ProcessDataFile metaTest = new ProcessDataFile("","",metaDataDO);
		Class<?>[] classArray = new Class<?>[2];
		classArray[0] = java.lang.String.class;
		classArray[1] = int.class;
		Method method = null;
		try {
			method = metaTest.getClass().getDeclaredMethod(
					"validateNumeric", classArray);
			method.setAccessible(true);

		} catch (NoSuchMethodException | SecurityException e) {
			e.printStackTrace();
		}

		String number = "-123659874123659";
		int linecount = 0;
		Object[] param = { number,linecount };
		try {
			method.invoke(metaTest, param);
			


		} catch (IllegalAccessException | IllegalArgumentException
				| InvocationTargetException e) {
			e.printStackTrace();

		}
	}
	
	@Test
	public void validateNumericTestFloat() {
		// Use reflections to test the private method.
		ProcessDataFile metaTest = new ProcessDataFile("","",metaDataDO);
		Class<?>[] classArray = new Class<?>[2];
		classArray[0] = java.lang.String.class;
		classArray[1] = int.class;
		Method method = null;
		try {
			method = metaTest.getClass().getDeclaredMethod(
					"validateNumeric", classArray);
			method.setAccessible(true);

		} catch (NoSuchMethodException | SecurityException e) {
			e.printStackTrace();
		}

		String number = "789654123.32659874";
		int linecount = 0;
		Object[] param = { number,linecount };
		try {
			method.invoke(metaTest, param);
			


		} catch (IllegalAccessException | IllegalArgumentException
				| InvocationTargetException e) {
			e.printStackTrace();

		}
	}
	
	@Test
	public void validateNumericTestFloatNeg() {
		// Use reflections to test the private method.
		ProcessDataFile metaTest = new ProcessDataFile("","",metaDataDO);
		Class<?>[] classArray = new Class<?>[2];
		classArray[0] = java.lang.String.class;
		classArray[1] = int.class;
		Method method = null;
		try {
			method = metaTest.getClass().getDeclaredMethod(
					"validateNumeric", classArray);
			method.setAccessible(true);

		} catch (NoSuchMethodException | SecurityException e) {
			e.printStackTrace();
		}

		String number = "-789654123.32659874";
		int linecount = 0;
		Object[] param = { number,linecount };
		try {
			method.invoke(metaTest, param);
			


		} catch (IllegalAccessException | IllegalArgumentException
				| InvocationTargetException e) {
			e.printStackTrace();

		}
	}
	
	/******************************************************************************************************/
	/******************************************************************************************************/
	/** Methods to test the processDataLine method */
	
	@Test
	public void processDataLineTest() {
		// Use reflections to test the private method.
		String errorMsg = "Data file cannot be processed. Failure at line number: 0. Length of each line is expected to be "+metaDataDO.getTotalLengthPerLine()+".";
		ProcessDataFile metaTest = new ProcessDataFile("","",metaDataDO);
		Class<?>[] classArray = new Class<?>[2];
		classArray[0] = java.lang.StringBuffer.class;
		classArray[1] = int.class;
		Method method = null;
		try {
			method = metaTest.getClass().getDeclaredMethod(
					"processDataLine", classArray);
			method.setAccessible(true);

		} catch (NoSuchMethodException | SecurityException e) {
			e.printStackTrace();
		}

		StringBuffer currStr = new StringBuffer();
		int linecount = 0;
		Object[] param = { currStr,linecount };
		try {
			method.invoke(metaTest, param);
			expectedEx.expect(RuntimeException.class);
			expectedEx
					.expectMessage(errorMsg);


		} catch (IllegalAccessException | IllegalArgumentException
				| InvocationTargetException e) {
			if (e.getCause()
					.getMessage()
					.endsWith(errorMsg)) {
				// do nothing.
			} else {
				e.printStackTrace();
			}

		}
	}
	@Test
	public void processDataLineLongLine() {
		// Use reflections to test the private method.
		String errorMsg = "Data file cannot be processed. Failure at line number: 0. Length of each line is expected to be "+metaDataDO.getTotalLengthPerLine()+".";
		ProcessDataFile metaTest = new ProcessDataFile("","",metaDataDO);
		Class<?>[] classArray = new Class<?>[2];
		classArray[0] = java.lang.StringBuffer.class;
		classArray[1] = int.class;
		Method method = null;
		try {
			method = metaTest.getClass().getDeclaredMethod(
					"processDataLine", classArray);
			method.setAccessible(true);

		} catch (NoSuchMethodException | SecurityException e) {
			e.printStackTrace();
		}

		StringBuffer currStr = new StringBuffer("aaaaa*****bbbbb&&&&&ccccc^^^^^ddddd)))))eeeee");
		int linecount = 0;
		Object[] param = { currStr,linecount };
		try {
			method.invoke(metaTest, param);
			expectedEx.expect(RuntimeException.class);
			expectedEx
					.expectMessage(errorMsg);


		} catch (IllegalAccessException | IllegalArgumentException
				| InvocationTargetException e) {
			if (e.getCause()
					.getMessage()
					.endsWith(errorMsg)) {
				// do nothing.
			} else {
				e.printStackTrace();
			}

		}
	}
	
	@Test
	public void processDataLineSpl() {
		// Use reflections to test the private method.
		String errorMsg = "Data file cannot be processed. Failure at line number: 0. Length of each line is expected to be "+metaDataDO.getTotalLengthPerLine()+".";
		ProcessDataFile metaTest = new ProcessDataFile("","",metaDataDO);
		Class<?>[] classArray = new Class<?>[2];
		classArray[0] = java.lang.StringBuffer.class;
		classArray[1] = int.class;
		Method method = null;
		try {
			method = metaTest.getClass().getDeclaredMethod(
					"processDataLine", classArray);
			method.setAccessible(true);

		} catch (NoSuchMethodException | SecurityException e) {
			e.printStackTrace();
		}

		StringBuffer currStr = new StringBuffer("Mary Ann-Lee             2018-10-10  22 ");
		int linecount = 0;
		Object[] param = { currStr,linecount };
		try {
			method.invoke(metaTest, param);
			expectedEx.expect(RuntimeException.class);
			expectedEx
					.expectMessage(errorMsg);


		} catch (IllegalAccessException | IllegalArgumentException
				| InvocationTargetException e) {
			if (e.getCause()
					.getMessage()
					.endsWith(errorMsg)) {
				// do nothing.
			} else {
				e.printStackTrace();
			}

		}
	}
}
