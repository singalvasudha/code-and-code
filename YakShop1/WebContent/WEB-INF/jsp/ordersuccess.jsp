<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Online Yak Shop - REST</title>
</head>
<body>

status = ${orderCommand.status}
<br>
{
<c:if test="${orderCommand.milkDelivered > 0}"> 
<br>
"milk" : 
${orderCommand.milkDelivered}
</c:if>
<c:if test="${orderCommand.skinsDelivered > 0 && orderCommand.milkDelivered > 0}">,</c:if>
<c:if test="${orderCommand.skinsDelivered > 0}">
<br>
"skins" :  
${orderCommand.skinsDelivered}
</c:if>
<br>
}
		<%-- <form:form method ="POST" action ="saveXml">
			Customer Name:<form:input path="custName"/>
			<br>
			Elapsed Days: <form:input path="daysElapsed"/>
			<br>
			Milk Required: <form:input path="milkReqd"/>
			<br>
			Skins Required: <form:input path="skinsReqd"/>
			<br>
			<input type="submit" value="Submit" />
			<br>
			${adminCommand.error}
      </form:form>

	<pre>${adminCommand.jsonStock}</pre> --%>
	
</body>
</html>