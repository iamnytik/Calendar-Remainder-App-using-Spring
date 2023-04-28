<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Register Form</title>
</head>
<body>

	<form method="post" action="newUser">
		<table class="table">
			<tr>
				<td>
					Username:
				</td>
				<td>
					<input type="text" name="username">
				</td>
			</tr>
			<tr>
				<td>
					PhoneNumber:
				</td>
				<td>
					<input type="text" name="phonenumber">
				</td>
			</tr>
			<tr>
				<td>
					Email:
				</td>
				<td>
					<input type="email" name="email">
				</td>
			</tr>
			<tr>
				<td>
					Enter Password:
				</td>
				<td>
					<input type="password" name="password">
				</td>
			</tr>
			<tr>
				<td>
					Confirm password:
				</td>
				<td>
					<input type="password" name="re_password">
				</td>
			</tr>
			<tr>
				<td>
					<input type="submit">
				</td>
			</tr>
		</table>
	</form>

</body>
</html>