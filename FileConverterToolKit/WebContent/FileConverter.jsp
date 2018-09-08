<%@ page contentType="text/html; charset=UTF-8" %>

<html>
<head>
<meta charset="ISO-8859-1">
<title>Fixed File Format Converter</title>

<style>
table, th, td {
    padding: 18px;
}
</style>


</head>
<body>
<script type="text/javascript">
function validateForm() {
    var metaLoc = document.forms["fileConverterForm"]["metaFileLocation"].value;
    var dataLoc = document.forms["fileConverterForm"]["dataFileLocation"].value;
    var destLoc = document.forms["fileConverterForm"]["destinationLocation"].value;
    if (metaLoc == "") {
        alert("Please enter the Meta Data File Location");
        return false;
    }
    if (dataLoc == "") {
        alert("Please enter the Data File Location");
        return false;
    }    
    if (destLoc == "") {
        alert("Please enter the Destination Location for the converted CSV file.");
        return false;
    }
}
</script>
<form name="fileConverterForm" action="${pageContext.request.contextPath}/fileConvert" onsubmit="return validateForm()" method="post">
<h2>Fixed File Format Converter</h2>
<font color="red">${requestScope.errorString == null ? "" : requestScope.errorString}</font>
<br>
<font color="blue">${requestScope.successString == null ? "" : requestScope.successString}</font>
<br>
<i> Please enter path for Meta Data File, Data File and Destination path for the converted CSV before clicking the 'Submit' button.</i>  
<br><br>
<table>
	<tr>
		<td><b>Meta Data File</b></td> 
		<td><input type="file" name="metaFileLocation" required> </td>
	</tr>
	<tr>
		<td><b>Data File</b></td> 
		<td><input type="file" name="dataFileLocation" required> </td>
	</tr>
	<tr>
		<td><b>Destination Path</b></td> 
		<td><input type="text" name="destinationLocation"/></td>
	</tr>
	<tr> 
		<td> <input type="submit" value="Convert to CSV"> </td>
	</tr>
	
</table>


</form>
</body>
</html>