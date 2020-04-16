<%@page import="java.util.Map"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
<body>
在线用户：
<table width="400" border="1">
<%
Map<String , String> online = (Map<String , String>)application
    .getAttribute("online");
for (String sessionId : online.keySet())
{%>
<tr>
    <td><%=sessionId%>
    <td><%=online.get(sessionId)%>
</tr>
<%}%>
</body>
</body>
</html>