import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class ProcessMetaFileTest {

	
	/******************************************************************************************************/
	/******************************************************************************************************/
	/** Methods to test the exception cases for CurrMeta array length */
	
	@Rule
	public ExpectedException expectedEx = ExpectedException.none();
	@Test
	public void validateMetaDataLineCurrMetaLen0() {
		// Use reflections to test the private method.
		ProcessMetaFile metaTest = new ProcessMetaFile("");
		Class<?>[] classArray = new Class<?>[1];
		classArray[0] = java.lang.String[].class;
		Method method = null;
		try {
			method = metaTest.getClass().getDeclaredMethod(
					"validateMetaDataLine", classArray);
			method.setAccessible(true);

		} catch (NoSuchMethodException | SecurityException e) {
			e.printStackTrace();
		}

		String[] input = new String[0];
		Object[] param = { input };
		try {
			method.invoke(metaTest, param);
			expectedEx.expect(RuntimeException.class);
			expectedEx
					.expectMessage("Meta data file cannot contain more than 3 comma separated details. Accepted Format-column name, Data Length, Data Type.");


		} catch (IllegalAccessException | IllegalArgumentException
				| InvocationTargetException e) {
			if (e.getCause()
					.getMessage()
					.endsWith(
							"Meta data file cannot contain more than 3 comma separated details. Accepted Format-column name, Data Length, Data Type.")) {
				// do nothing.
			} else {
				e.printStackTrace();
			}

		}
	}

	@Test
	public void validateMetaDataLineCurrMetaLen1() {
		// Use reflections to test the private method.
		ProcessMetaFile metaTest = new ProcessMetaFile("");
		Class<?>[] classArray = new Class<?>[1];
		classArray[0] = java.lang.String[].class;
		Method method = null;
		try {
			method = metaTest.getClass().getDeclaredMethod(
					"validateMetaDataLine", classArray);
			method.setAccessible(true);

		} catch (NoSuchMethodException | SecurityException e) {
			
			e.printStackTrace();
		}
		Object[] param ={new String[1]};
		try {
			method.invoke(metaTest, param);
			expectedEx.expect(RuntimeException.class);
			expectedEx
					.expectMessage("Meta data file cannot contain more than 3 comma separated details. Accepted Format-column name, Data Length, Data Type.");


		} catch (IllegalAccessException | IllegalArgumentException
				| InvocationTargetException e) {
			if (e.getCause()
					.getMessage()
					.endsWith(
							"Meta data file cannot contain more than 3 comma separated details. Accepted Format-column name, Data Length, Data Type.")) {
				// do nothing.
			} else {
				e.printStackTrace();
			}
		}
	}

	@Test
	public void validateMetaDataLineCurrMetaLen4() {
		// Use reflections to test the private method.
		ProcessMetaFile metaTest = new ProcessMetaFile("");
		Class<?>[] classArray = new Class<?>[1];
		classArray[0] = java.lang.String[].class;
		Method method = null;
		try {
			method = metaTest.getClass().getDeclaredMethod(
					"validateMetaDataLine", classArray);
			method.setAccessible(true);

		} catch (NoSuchMethodException | SecurityException e) {
			
			e.printStackTrace();
		}
		Object[] param ={new String[4]};
		try {
			method.invoke(metaTest, param);
			expectedEx.expect(RuntimeException.class);
			expectedEx
					.expectMessage("Meta data file cannot contain more than 3 comma separated details. Accepted Format-column name, Data Length, Data Type.");

		} catch (IllegalAccessException | IllegalArgumentException
				| InvocationTargetException e) {
			if (e.getCause() == null || e.getCause()
					.getMessage()
					.endsWith(
							"Meta data file cannot contain more than 3 comma separated details. Accepted Format-column name, Data Length, Data Type.")) {
				// do nothing.
			} else {
				e.printStackTrace();
			}
		}
	}
	/******************************************************************************************************/
	/******************************************************************************************************/
	
	
	/******************************************************************************************************/
	/******************************************************************************************************/
	/**Methods to check the input values within the currMeta String array*/
	
	@Test
	public void validateMetaDataLineCurrMetaVal1Err() {
		// Use reflections to test the private method.
		ProcessMetaFile metaTest = new ProcessMetaFile("");
		Class<?>[] classArray = new Class<?>[1];
		classArray[0] = java.lang.String[].class;
		// for (int i = 0; i < paramCount; i++) {
		// classArray[i] = params[i].getClass();
		// }
		Method method = null;
		try {
			method = metaTest.getClass().getDeclaredMethod(
					"validateMetaDataLine", classArray);
			method.setAccessible(true);

		} catch (NoSuchMethodException | SecurityException e) {
			
			e.printStackTrace();
		}
		
		String[] input = new String[3];
		input[0] = "";
		input[1] = "cc";
		input[2] = "cc";
		
		
		 Object[] param ={input};

		try {
			method.invoke(metaTest, param);
			expectedEx.expect(RuntimeException.class);
			expectedEx
					.expectMessage("Meta data file cannot be processed. Column name cannot be blank.");

		} catch (IllegalAccessException | IllegalArgumentException
				| InvocationTargetException e) {
			if (e.getCause().getMessage().endsWith("Meta data file cannot be processed. Column name cannot be blank.")){
				// do nothing.
			} else {
			e.printStackTrace();}
		}
	}
	
	@Test
	public void validateMetaDataLineCurrMetaValString() {
		// Use reflections to test the private method.
		ProcessMetaFile metaTest = new ProcessMetaFile("");
		Class<?>[] classArray = new Class<?>[1];
		classArray[0] = java.lang.String[].class;
		// for (int i = 0; i < paramCount; i++) {
		// classArray[i] = params[i].getClass();
		// }
		Method method = null;
		try {
			method = metaTest.getClass().getDeclaredMethod(
					"validateMetaDataLine", classArray);
			method.setAccessible(true);

		} catch (NoSuchMethodException | SecurityException e) {
			
			e.printStackTrace();
		}

		String[] input = new String[3];
		input[0] = "name";
		input[1] = "10";
		input[2] = "string";

		Object[] param = { input };

		try {
			method.invoke(metaTest, param);

		} catch (IllegalAccessException | IllegalArgumentException
				| InvocationTargetException e) {
			e.printStackTrace();
		}

	}	
	
	@Test
	public void validateMetaDataLineCurrMetaValNumeric() {
		// Use reflections to test the private method.
		ProcessMetaFile metaTest = new ProcessMetaFile("");
		Class<?>[] classArray = new Class<?>[1];
		classArray[0] = java.lang.String[].class;
		// for (int i = 0; i < paramCount; i++) {
		// classArray[i] = params[i].getClass();
		// }
		Method method = null;
		try {
			method = metaTest.getClass().getDeclaredMethod(
					"validateMetaDataLine", classArray);
			method.setAccessible(true);

		} catch (NoSuchMethodException | SecurityException e) {
			
			e.printStackTrace();
		}

		String[] input = new String[3];
		input[0] = "level";
		input[1] = "5";
		input[2] = "numeric";

		Object[] param = { input };

		try {
			method.invoke(metaTest, param);

		} catch (IllegalAccessException | IllegalArgumentException
				| InvocationTargetException e) {
			e.printStackTrace();
		}

	}
	
	@Test
	public void validateMetaDataLineCurrMetaValDate() {
		// Use reflections to test the private method.
		ProcessMetaFile metaTest = new ProcessMetaFile("");
		Class<?>[] classArray = new Class<?>[1];
		classArray[0] = java.lang.String[].class;
		// for (int i = 0; i < paramCount; i++) {
		// classArray[i] = params[i].getClass();
		// }
		Method method = null;
		try {
			method = metaTest.getClass().getDeclaredMethod(
					"validateMetaDataLine", classArray);
			method.setAccessible(true);

		} catch (NoSuchMethodException | SecurityException e) {
			
			e.printStackTrace();
		}

		String[] input = new String[3];
		input[0] = "joining date";
		input[1] = "10";
		input[2] = "date";

		Object[] param = { input };

		try {
			method.invoke(metaTest, param);

		} catch (IllegalAccessException | IllegalArgumentException
				| InvocationTargetException e) {
			e.printStackTrace();
		}

	}
	/************************************************************************************************************************/
	/************************************************************************************************************************/
	/************************************************************************************************************************/
	@Test
	public void validateDataLengthAndTypeTestDTBlank() {
		// Use reflections to test the private method.
		ProcessMetaFile metaTest = new ProcessMetaFile("");
		Class<?>[] classArray = new Class<?>[2];
		classArray[0] = java.lang.String.class;
		classArray[1] = java.lang.String.class;
		Method method = null;
		try {
			method = metaTest.getClass().getDeclaredMethod(
					"validateDataLengthAndType", classArray);
			method.setAccessible(true);

		} catch (NoSuchMethodException | SecurityException e) {
			
			e.printStackTrace();
		}
		
		try {
			method.invoke(metaTest, "","");
			expectedEx.expect(RuntimeException.class);
			expectedEx
					.expectMessage("Meta data file cannot be processed. Data Type  is not supported.");

		} catch (IllegalAccessException | IllegalArgumentException
				| InvocationTargetException e) {
			if (e.getCause().getMessage().endsWith("Meta data file cannot be processed. Data Type  is not supported.")){
				// do nothing.
			} else {
			e.printStackTrace();}
		}
	}
	
	@Test
	public void validateDataLengthAndTypeTestDTErr() {
		// Use reflections to test the private method.
		ProcessMetaFile metaTest = new ProcessMetaFile("");
		Class<?>[] classArray = new Class<?>[2];
		classArray[0] = java.lang.String.class;
		classArray[1] = java.lang.String.class;
		Method method = null;
		try {
			method = metaTest.getClass().getDeclaredMethod(
					"validateDataLengthAndType", classArray);
			method.setAccessible(true);

		} catch (NoSuchMethodException | SecurityException e) {
			
			e.printStackTrace();
		}
		
		try {
			method.invoke(metaTest, "","tring");
			expectedEx.expect(RuntimeException.class);
			expectedEx
					.expectMessage("Meta data file cannot be processed. Data Type tring is not supported.");

		} catch (IllegalAccessException | IllegalArgumentException
				| InvocationTargetException e) {
			if (e.getCause().getMessage().endsWith("Meta data file cannot be processed. Data Type tring is not supported.")){
				// do nothing.
			} else {
			e.printStackTrace();}
		}
	}
	
	@Test
	public void validateDataLengthAndTypeTestDLBlank() {
		// Use reflections to test the private method.
		ProcessMetaFile metaTest = new ProcessMetaFile("");
		Class<?>[] classArray = new Class<?>[2];
		classArray[0] = java.lang.String.class;
		classArray[1] = java.lang.String.class;
		Method method = null;
		try {
			method = metaTest.getClass().getDeclaredMethod(
					"validateDataLengthAndType", classArray);
			method.setAccessible(true);

		} catch (NoSuchMethodException | SecurityException e) {
			
			e.printStackTrace();
		}
		
		try {
			method.invoke(metaTest, "","String");
			expectedEx.expect(RuntimeException.class);
			expectedEx
					.expectMessage("Meta data file cannot be processed. Data Length must be numeric. Value  is not supported.");

		} catch (IllegalAccessException | IllegalArgumentException
				| InvocationTargetException e) {
			if (e.getCause().getMessage().endsWith("Meta data file cannot be processed. Data Length must be numeric. Value  is not supported.")){
				// do nothing.
			} else {
			e.printStackTrace();}
		}
	}
	
	@Test
	public void validateDataLengthAndTypeTestDLERR() {
		// Use reflections to test the private method.
		ProcessMetaFile metaTest = new ProcessMetaFile("");
		Class<?>[] classArray = new Class<?>[2];
		classArray[0] = java.lang.String.class;
		classArray[1] = java.lang.String.class;
		Method method = null;
		try {
			method = metaTest.getClass().getDeclaredMethod(
					"validateDataLengthAndType", classArray);
			method.setAccessible(true);

		} catch (NoSuchMethodException | SecurityException e) {
			
			e.printStackTrace();
		}
		
		try {
			method.invoke(metaTest, "1*","String");
			expectedEx.expect(RuntimeException.class);
			expectedEx
					.expectMessage("Meta data file cannot be processed. Data Length must be numeric. Value 1* is not supported.");

		} catch (IllegalAccessException | IllegalArgumentException
				| InvocationTargetException e) {
			if (e.getCause().getMessage().endsWith("Meta data file cannot be processed. Data Length must be numeric. Value 1* is not supported.")){
				// do nothing.
			} else {
			e.printStackTrace();}
		}
	}
	
	@Test
	public void validateDataLengthAndTypeTestDLERR1() {
		// Use reflections to test the private method.
		ProcessMetaFile metaTest = new ProcessMetaFile("");
		Class<?>[] classArray = new Class<?>[2];
		classArray[0] = java.lang.String.class;
		classArray[1] = java.lang.String.class;
		Method method = null;
		try {
			method = metaTest.getClass().getDeclaredMethod(
					"validateDataLengthAndType", classArray);
			method.setAccessible(true);

		} catch (NoSuchMethodException | SecurityException e) {
			
			e.printStackTrace();
		}
		
		try {
			method.invoke(metaTest, "11","date");
			expectedEx.expect(RuntimeException.class);
			expectedEx
					.expectMessage("Meta data file cannot be processed. Data Length must be 10 for the data type of a 'Date'. Value 11 is not supported.");

		} catch (IllegalAccessException | IllegalArgumentException
				| InvocationTargetException e) {
			if (e.getCause().getMessage().endsWith("Meta data file cannot be processed. Data Length must be 10 for the data type of a 'Date'. Value 11 is not supported.")){
				// do nothing.
			} else {
			e.printStackTrace();}
		}
	}
	/******************************************************************************************************/
	/******************************************************************************************************/
	
	
	/******************************************************************************************************/
	/******************************************************************************************************/
	/**Methods to check the public method*/
	@Rule
	public ExpectedException thrown = ExpectedException.none();

	@Test
	public void processMetaFileDataErrLocation() {
		// Use reflections to test the private method.
		ProcessMetaFile metaTest = new ProcessMetaFile("c:/xyz");
		// arrange
		thrown.expect(RuntimeException.class);
		metaTest.processMetaFileData();
	}
}
