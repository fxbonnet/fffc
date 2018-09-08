<%@ page isErrorPage="true" import="java.io.*"%>
<html><head></head>
</body>
<h1>Server Error</h1>
<font color="red">Message:</font><br>
<%=exception.getMessage()%>
<br><br><br>
<font color="red">StackTrace:</font><br>
<%
	StringWriter stringWriter = new StringWriter();
	PrintWriter printWriter = new PrintWriter(stringWriter);
	exception.printStackTrace(printWriter);
	out.println(stringWriter);
	printWriter.close();
	stringWriter.close();
%>
<br><br><br>
<h3>
<a href="FileConverter.jsp">Back to Home Page</a>
</h3>
</body>
</html>