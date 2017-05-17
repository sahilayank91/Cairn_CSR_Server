<%@page import="java.util.Date"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="java.sql.Connection"%>
<%@page import="java.sql.PreparedStatement"%>
<%@page import="com.cairnindia.csr.database.PostgreSQLConnection"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
  
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Cairn India CSR Android App</title>
</head>
<body>
<h2>Cairn India CSR Android App</h2>
List of active users (Live)
<table border="1" style="border-collapse:collapse">
<th>S.No.</th>
<th>Username</th><th>Email</th><th>Last Seen</th>

<%
response.setIntHeader("Refresh", 5);
Connection con=PostgreSQLConnection.getConnection();
PreparedStatement proc=con.prepareStatement("Select * from users order by last_seen;");

ResultSet rs =proc.executeQuery();
int i=1;
while(rs.next()){
%>
<tr>
<td><%=i++ %></td>
<td><%=rs.getString("name") %></td>
<td><%=rs.getString("email") %></td>
<td><%=rs.getTimestamp("last_seen")%></td>
</tr>
<%} %>
</table>

</body>
</html>