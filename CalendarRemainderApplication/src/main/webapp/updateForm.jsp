<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
	
	<form action="/updateToDb" method=post>
		<input type="hidden" name="userId" value="${userId}">
		<input type="hidden" name="date" value="${date}">
		<input type="time" name="eventStartTime" value="${eventStartTime}"><br/>
		<input type="time" name="eventEndTime" value="${eventEndTime}"><br/>
		<input type="text" name="toMail" value="${toMail}"><br/>
		<textarea name = "eventDesc" rows="20" cols="25">"${eventDesc}"</textarea>
		<input type = "submit" name="save">
	</form>
</body>
</html>