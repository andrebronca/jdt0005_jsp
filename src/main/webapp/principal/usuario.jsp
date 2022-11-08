<%@ page import="com.arb.model.ModelLogin"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
												<span style="padding: 2px 10px;" id="msg">${msg}</span>
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
														<button type="button" class="btn btn-warning btn-out waves-effect waves-light" onclick="deleteComAjax();">Remover (Ajax)</button>
														<button type="submit" class="btn btn-primary btn-out waves-effect waves-light">Salvar</button>
														<button type="button" class="btn btn-secondary btn-out waves-effect waves-light" data-toggle="modal" data-target="#modalUsuario">Pesquisar</button>
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

  <!-- Modal de pesquisa -->
  <div class="modal fade" id="modalUsuario" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
    <div class="modal-dialog" role="document">
      <div class="modal-content">
        <div class="modal-header">
          <h5 class="modal-title" id="exampleModalLabel">Pesquisa de usuário</h5>
          <button type="button" class="close" data-dismiss="modal" aria-label="Close">
            <span aria-hidden="true">&times;</span>
          </button>
        </div>
        <div class="modal-body">
          
          <div class="input-group mb-3">
					  <input type="text" class="form-control" placeholder="Nome do usuário" id="txt_buscar_nome"
					   aria-label="nome" aria-describedby="basic-addon2">
					  <div class="input-group-append">
					    <button class="btn btn-outline-success" type="button" onclick="buscarUsuario();">Buscar</button>
					  </div>
					</div>
					
					<div style="height: 300px; overflow: scroll;">
						<table class="table table-hover" id="listaUsuariosPesquisados">
						  <thead>
						    <tr>
						      <th scope="col">ID</th>
						      <th scope="col">Nome</th>
						      <th scope="col">Ver</th>
						    </tr>
						  </thead>
						  <tbody>
						  </tbody>
						</table>
					</div>
			<span id="totalResultados"></span>					
        </div>
        <div class="modal-footer">
          <button type="button" class="btn btn-secondary" data-dismiss="modal">Fechar</button>
        </div>
      </div>
    </div>
  </div>

	<jsp:include page="javascript-file.jsp"></jsp:include>
	<script type="text/javascript">
		function limparForm(){
			let elementos = document.getElementById("formUser").elements;
			for(let p = 0; p < elementos.length; p++){
				elementos[p].value = '';
			}
		}

		function deletarUser(){
			if(confirm('Deseja excluir o cadastro?')){
				document.getElementById("formUser").method = 'get';
				document.getElementById("acao").value = 'deletar';
				document.getElementById("formUser").submit();
			}
		}

		function verEditar(id){
			let urlAction = document.getElementById('formUser').action;
			window.location.href = urlAction +'?acao=buscarEditar&id='+id;
		}
		
		function buscarUsuario(){
			let nome_buscar = document.getElementById('txt_buscar_nome').value.trim();
			
			
			//Esse null é pra cair o c do b. só objeto é null.
			if (nome_buscar != null && nome_buscar != ''){
				let urlAction = document.getElementById('formUser').action;
				
			  $.ajax({
				  method: 'get',
				  url: urlAction,
				  contentType: 'application/x-www-form-urlencoded;charset=utf-8',
				  data: 'acao=buscarNomeAjax&nomebuscar='+ nome_buscar,
				  success: function(response){
					  let lista_usuarios = JSON.parse(response);
					  $('#listaUsuariosPesquisados > tbody > tr').remove();
					  $('#totalResultados').text('');
					  
					  for(let i = 0; i < lista_usuarios.length; i++){
						  let idUser = lista_usuarios[i].id;
						  let nomeUser = lista_usuarios[i].nome;
						  $('#listaUsuariosPesquisados > tbody').append('<tr>'+
								  '<td>'+ idUser +'</td>'+
								  '<td>'+ nomeUser +'</td>'+
								  '<td><button type="button" onclick="verEditar('+ idUser +');" class="btn btn-info btn-out waves-effect waves-light">Ver</button></td>'+
								  '</tr>');
					}
					$('#totalResultados').text('Total de resultados: '+ lista_usuarios.length);
					  
				  }
			  }).fail(function(xhr, status, errorThrown){
				  alert('Erro ao pesquisar o usuário: '+ xhr.responseText);
			  });		
			}
		}

		

		function deleteComAjax(){
			if(confirm('Deseja excluir o cadastro?')){
				//action do formulário
				let urlAction = document.getElementById('formUser').action;
				let idUser = document.getElementById('id').value;

				$.ajax({
					method: 'get',
					url: urlAction,
					data: 'acao=ajaxdeletar&id='+ idUser,
					success: function(response){ 
						document.getElementById('msg').textContent = response;
						limparForm();
					}
				}).fail(function(xhr, status, errorThrown){
					alert('Erro ao deletar usuário por id: '+ xhr.responseText);
				});
			}
		}
	</script>
	
</body>

</html>
