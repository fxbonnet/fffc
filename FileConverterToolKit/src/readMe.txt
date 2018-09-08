Name: Sweta Vijay Menon

Front End: Jsp
Software component: Java Servlet
Servlet Container: Tomcat v7.0
Java Version: 7
Unit Testing: JUnit
IDE: Eclipse

1. Create a workspace and import the 'File Converter' project into it.
2. Please ensure the environment is equipped with all the above technologies and containers.
3. Add the tomcat server to the workspace.
4. Ensure that the java compiler version is 1.7.
5. Build the Java source code.
6. Run the tomcat server.
7. Call the servlet from the desired web browser.
	URL: http://localhost:8080/FileConverter/
	
What the Servlet will do:
1. The UI displays 3 input fields for the Meta data, Data and Destination file location. The screen is equipped with appropriate validation.
2. Once all the inputs have been filled in, click the "Convert to CSV" button.
3. If the process meets with an error, you will be redirected to an error page. Since this project has been treated as the developer's version of the tool, the error page displays the stack trace.
	The error page has a "Back to Home Page" link to go back to the home page and retry the file transfer.
4. If the conversion is successful, you are redirected back to the home page with a success message. The success message will contain the name of the CSV file that is generated.

Unit Testing:
JUnits was used to test the source code. In addition, the path below also has all the file combinations used for testing success and error flows.
Scenario sheet: /FileConverterToolKit/src/Test/ScenarioSheet.xlsx
Scenario Files:
/FileConverterToolKit/src/Test/Scenario1 to /FileConverterToolKit/src/Test/Scenario7