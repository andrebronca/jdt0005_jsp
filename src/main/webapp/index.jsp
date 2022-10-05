<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<style>
	<%@include file="/WEB-INF/resources/css/button12.css" %>
</style>
<title>Curso JSP</title>
</head>
<body>
	<%
	out.print("<h1 style=\"text-align: center;\">Sistema em Java Server Pages</h1>");
	%>
	
	<div style="color:red;text-align:center;padding: 5px;">${msg}</div>
	<div style="border:2px solid #566573;width:10%;margin:auto;text-align:center;padding:5px;border-radius:15px;background-color:#d5d8dc;">
		<form action="ServletLogin" method="post">
			<label for="login">Login</label>
			<input type="text" name="login" id="login">
			<br> 
			<label for="senha">Senha</label> 
			<input type="password" name="senha" id="senha">
			<br>
			<br>
			<input type="submit" value="Acessar" class="button-12">
		</form>
	</div>
</body>
</html>