<%@page import="com.arb.model.ModelLogin"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%
 String path = request.getContextPath();
%>
<!DOCTYPE html>
<html lang="en">
<head>
<title></title>
<jsp:include page="head.jsp"></jsp:include>
</head>
<body>

	<jsp:include page="theme-loader.jsp"></jsp:include>

	<!-- Pre-loader end -->
	<div id="pcoded" class="pcoded">
		<div class="pcoded-overlay-box"></div>
		<div class="pcoded-container navbar-wrapper">

			<jsp:include page="navbar.jsp"></jsp:include>

			<div class="pcoded-main-container">
				<div class="pcoded-wrapper">

					<!-- Menu vertical do lado esquerdo -->
					<jsp:include page="navbar-main-menu.jsp"></jsp:include>

					<div class="pcoded-content">

						<jsp:include page="page-header.jsp"></jsp:include>

						<!-- Page-header end -->
						<div class="pcoded-inner-content">
							<!-- Main-body start -->
							<div class="main-body">
								<div class="page-wrapper">
									<!-- Page-body start -->
									<div class="page-body">

										<div class="col-sm-12">
											<!-- Basic Form Inputs card start -->
											<div class="card">
												<div class="card-header">
													<h5>Cadastro de usuário</h5>
												</div>
												<span style="padding: 2px 10px;">${msg}</span>
												<div class="card-block">

													<form id="formUser" class="form-material" method="post" action="<%= path %>/ServletUsuarioController">
														<input type="hidden" name="acao" id="acao" value="">
														<div class="form-group form-default form-static-label">
															<input type="text" name="id" id="id" class="form-control" readonly="readonly" value="${mLogin.id}">
															<span class="form-bar"></span>
															<label class="float-label" style="color:black;">ID</label>
														</div>
														<div class="form-group form-default">
															<input type="text" name="nome" id="nome" class="form-control" required="required" value="${mLogin.nome}">
															<span class="form-bar"></span>
															<label class="float-label">Nome</label>
														</div>
														<div class="form-group form-default">
														  <!--type="email" fica zuado -->
							                              <input type="text" name="email" id="email" class="form-control" autocomplete="off" required="required" value="${mLogin.email}">
							                              <span class="form-bar"></span>
							                              <label class="float-label">E-mail</label>
							                            </div>
							                            <div class="form-group form-default">
							                              <input type="text" name="login" id="login" class="form-control" required="required" value="${mLogin.login}">
							                              <span class="form-bar"></span>
							                              <label class="float-label">Login</label>
							                            </div>
							                            <div class="form-group form-default">
							                              <input type="password" name="senha" id="senha" autocomplete="off" class="form-control" required="required" value="${mLogin.senha}">
							                              <span class="form-bar"></span>
							                              <label class="float-label">Senha</label>
							                            </div>
														<button type="button" class="btn btn-success btn-out waves-effect waves-light" onclick="limparForm();">Novo</button>
														<button type="button" class="btn btn-warning btn-out waves-effect waves-light" onclick="deletarUser();">Remover</button>
														<button type="submit" class="btn btn-primary btn-out waves-effect waves-light">Salvar</button>
													</form>

												</div>
												<!-- card-block -->
											</div>
											<!-- card -->
										</div>
										<!-- col-sm-12 -->
									</div>
									<!-- Page-body end -->
								</div>
								<div id="styleSelector"></div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>


	<jsp:include page="javascript-file.jsp"></jsp:include>
	<script type="text/javascript">
		function limparForm(){
			var elementos = document.getElementById("formUser").elements;
			for(var p = 0; p < elementos.length; p++){
				elementos[p].value = '';
			}
		}

		function deletarUser(){
			document.getElementById("formUser").method = 'get';
			document.getElementById("acao").value = 'deletar';
			document.getElementById("formUser").submit();
		}
	</script>
	
</body>

</html>
