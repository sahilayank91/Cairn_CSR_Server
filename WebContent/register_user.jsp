<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
<form id="register" method="POST" action="rest/AuthenticationService/register" >
name<input type="text" name="user_name"/><br/>
email<input type="text" name="user_email"/><br/>
password<input type="text" name="user_password"/><br/>
phone<input type="text" name="user_phone"/><br/>
<input type="submit" name="submit" value="submit"/>
</form>
</body>
</html>