<%@page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"

%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"  %>																																																																																																																																																		
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
<style type="text/css">
button {
  width: 100%;
  margin: 5px; /* or whatever you like */
}
</style>
</head>
<body>
	<div>
		<form name="form" action="/updateView" method="post">
			<input type="hidden" name="userId" value="${userId}">
			<input type="hidden" name="date">
			<table class="table">
				<tr>
					<td> ${month}     ${year} 																		${ username }	</td>
				</tr>
				<tr>
					<td>
						<input type="submit" name="prev" value="prev"/>
					</td>
					
					<td>
						<table class="table">
							<thead class="thead-dark">
								<tr> 
									<th> Sunday </th>
									<th> Monday  </th>
									<th> Tuesday </th>
									<th> Wednesday </th>
									<th> Thursday </th>
									<th> Friday </th>
									<th> Saturday </th>
								</tr>
								
							</thead>
							
							 <c:forEach items="${obj}" var="s">
								<tr>
									<c:forEach items="${s}" var="i">
										<td>
										<c:if test="${(i != ' ')}">
											<button type="submit" id="dateBtn" name="eventDate" value="${i}/${month}/${year}" onclick="{document.form.date.value=this.value;document.form.action='/eventPage';}">${i}</button>
										</c:if>
										</td>
									</c:forEach>
								</tr>
							 </c:forEach>
					
						</table>
					
					</td>
				
					<td>
						<input type="submit" name="next" value="next"/>
					</td>
				</tr>
				
				<br/>
				<br/>
			</table>
			<button type="button"> <a href="/get/allEvents/${userId}">Get Events of the month</a></button>
		</form>
		
	</div>
		

</body>
</html>