<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Ocorr&ecirc;ncia de Erros</title>
</head>
<body>
<h2>Mensagem de erro, entre em contato com o suporte do sistema.</h2>

<%
out.print(request.getAttribute("msg"));
%>
</body>
</html>