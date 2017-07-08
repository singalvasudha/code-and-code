<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Online Yak Shop - ADMIN</title>
</head>
<body>
	In Stock:<br> 
	${adminCommand.milk} litres of milk
	<br>
	${adminCommand.skins} skins of wool
	<br>
	<br>
	Herd:<br>
	
	<c:forEach items="${adminCommand.herd.labYaks}" var="yak">
       <td>${yak.name}</td><td> </td><td>${yak.age+adminCommand.daysElapsed/100}</td> years old
       <br>
      </c:forEach> 

</body>
</html>