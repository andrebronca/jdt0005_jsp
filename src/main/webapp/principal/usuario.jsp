<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
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
												<div class="card-block">

													<form class="form-material">
														<div class="form-group form-default">
															<input type="text" name="id" id="id" class="form-control" readonly="readonly"><span
																class="form-bar"></span><label class="float-label">ID</label>
														</div>
														<div class="form-group form-default">
															<input type="text" name="nome" id="nome"
																class="form-control" required><span
																class="form-bar"></span><label class="float-label">Nome</label>
														</div>
														<div class="form-group form-default">
                              <input type="text" name="email" id="email"
                                class="form-control" required><span
                                class="form-bar"></span><label class="float-label">E-mail</label>
                            </div>
                            <div class="form-group form-default">
                              <input type="password" name="senha" id="senha"
                                class="form-control" required><span
                                class="form-bar"></span><label class="float-label">Senha</label>
                            </div>
														<!-- btn-primary, success, info, warning, danger, inverse, disabled disabled -->
														<button class="btn btn-primary waves-effect waves-light">Salvar</button>
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
</body>

</html>
