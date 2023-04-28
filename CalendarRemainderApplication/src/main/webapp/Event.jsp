<%@page import="javax.swing.text.Document"%>
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
	<input type="hidden" name="helper">
	<form name="form" action="" method="post">
		<input type="hidden" name="userId" value="${userId}">
		<input type="hidden" name="date" value="${date}">
		
		<table>
			<tr>
				<td>
					<button type="button">
						<a href="/updateEvent/Add/${userId}/${date}">Register</a>
					</button>
				</td>
			</tr>
			<th class="thead-dark">
				<tr>
					<td>
						Event Description
					</td>
					<td>
						Date
					</td>
					<td>
						Start time
					</td>
					<td>
						End time
					</td>
					<td>
						TO mail ID
					</td>
				</tr>
			</th>

			 <c:forEach items="${EventList}" var="el">
			 		<tr>
			 			<td> ${el.getEventDescription()} </td>
			 			<td> ${el.getDate()} </td>
			 			<td> ${el.getstartTime()} </td>
			 			<td> ${el.getendTime()} </td>
			 			<td> ${el.getToEmailAddr()}</td>
			 			<td> <button type="button">
			 					<a href="/updateEvent/update/${userId}/${el.getDate()}/${el.getstartTime()}/${el.getendTime()}">Update</a>
			 				 </button> 
			 			</td>
			 			<td>
			 			    <button type="submit">
			 					<a href="/updateEvent/delete/${userId}/${el.getDate()}/${el.getstartTime()}">Delete</a>	
			 				</button> 
			 			</td>
			 		</tr>
			 </c:forEach>
		</table>
		<button type="button">
			<a href='/goToCal/${userId}'>Go To calendar</a>
		</button>
	</form>
	
</body>
</html>