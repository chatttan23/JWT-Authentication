<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Home-Page LogIn-Page</title>
</head>
<body>
	<form action="/auth/login" method="post">
	<label>UserName</label>
		<input type="text" name="username" id="username">
			<label>Password</label>
			<input type="password" name="password" id="password">
		
				<input type="submit" value="submit" >
	
	
	</form>
</body>
</html>