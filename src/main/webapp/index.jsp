<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Curso JSP</title>
</head>
<body>
  <% 
  out.print("<h1>JSP</h1>"); 
  %>
  
  <form action="receber-nome.jsp">
    <input type="text" name="nome" id="nome"><br>
    <input type="text" name="idade" id="idade"><br>
    <input type="submit" value="Enviar">
  </form>
</body>
</html>