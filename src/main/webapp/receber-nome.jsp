<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title></title>
</head>
<body>
<%
 String nome = request.getParameter("nome");
 String idade = request.getParameter("idade");
 if(nome != null){
   out.println("Nome: "+ nome);
 }
 out.println("<br>");
 if(idade != null){
	 out.println("Idade: "+ idade);
 }
%>
</body>
</html>