<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
<form id="add_post" method="POST" action="rest/PostService/addPost" >
<input type="text" name="post_author"/>
<input type="text" name="post_text"/>
<input type="text" name="post_smiles"/>
<input type="text" name="post_images"/>
<input type="submit" name="submit" value="submit"/>
</form>
</body>
</html>