<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"  %>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
	<div>
		<form action="/eventRegister" method="post">
			<input type="hidden" name="userId" value="${userId}">
			<input type="hidden" name="date" value="${date}"> 
			<input type="time" name="stime" placeholder="event start time"><br>
			<input type="time" name="etime" placeholder="event end time"><br>
			<input type = "email" name="toMail" placeholder="recipient mail address"><br>
			<textarea rows="10" cols="20" name="eventDes"></textarea><br>
			<input type="submit">
		</form>
		
	</div>
</body>
</html>