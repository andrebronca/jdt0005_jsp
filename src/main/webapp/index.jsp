<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<!-- Bootstrap CSS -->
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3" crossorigin="anonymous">

<style>
	<%@include file="/WEB-INF/resources/css/button12.css" %>
</style>
<title>Curso JSP</title>
</head>
<body>
  <div class="container">
	<%
	out.print("<h3 class=\"text-center\">Sistema em Java Server Pages</h3>");
	%>
	
	<!-- depois aplica somente classe do bootstrap -->
	<div style="text-align:center;padding:5px;" class="text-warning">${msg}</div>
		<form action="<%= path %>/ServletLogin" method="post" class="row g-3 needs-validation" novalidate>
		  <input type="hidden" value="<%= request.getParameter("url") %>" name="url">
		  <div class="col-md-6">
  			<label for="login" class="form-label">Login</label>
	   		<input type="text" name="login" id="login" class="form-control" required value="admin">
	   		<div class="invalid-feedback">É necessário informar o login!</div>
	   		<div class="valid-feedback">Ok</div>
		  </div>
			<div class="col-md-6">
				<label for="senha" class="form-label">Senha</label>
				<input type="password" name="senha" id="senha" class="form-control" required value="admin">
				<div class="invalid-feedback">É necessário informar a senha!</div>
				<div class="valid-feedback">Ok</div>
			</div>
			<div class="col-12">
  			<input type="submit" value="Acessar" class="btn btn-primary">
			</div> 
		</form>
	</div>
	<!-- Option 1: Bootstrap Bundle with Popper -->
  <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-ka7Sk0Gln4gmtz2MlQnikT1wXgYsOg+OMhuP+IlRH9sENBO0LRn5q+8nbTov4+1p" crossorigin="anonymous"></script>
	<script type="text/javascript">
	// Example starter JavaScript for disabling form submissions if there are invalid fields
	//essa função é para a validação do formulário antes de fazer o submit
	(function () {
	  'use strict'

	  // Fetch all the forms we want to apply custom Bootstrap validation styles to
	  var forms = document.querySelectorAll('.needs-validation')

	  // Loop over them and prevent submission
	  Array.prototype.slice.call(forms)
	    .forEach(function (form) {
	      form.addEventListener('submit', function (event) {
	        if (!form.checkValidity()) {
	          event.preventDefault()
	          event.stopPropagation()
	        }

	        form.classList.add('was-validated')
	      }, false)
	    })
	})()
	</script>
</body>
</html>