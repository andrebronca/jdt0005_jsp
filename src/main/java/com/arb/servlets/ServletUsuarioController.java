package com.arb.servlets;

import java.io.IOException;
import java.sql.SQLException;

import com.arb.constantes.Constantes;
import com.arb.dao.DAOUsuarioRepository;
import com.arb.model.ModelLogin;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet(urlPatterns = { "/ServletUsuarioController" })
public class ServletUsuarioController extends HttpServlet {

	private static final long serialVersionUID = 13025390838445016L;
	private DAOUsuarioRepository dao = new DAOUsuarioRepository();

	public ServletUsuarioController() {
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			String acao = request.getParameter("acao");
			String msg = null;
			if(acao != null && !acao.isEmpty() && acao.equalsIgnoreCase("deletar")) {
				String idUser = request.getParameter("id");
				Long id = null;
				try {
					if (idUser != null) {
						id = Long.parseLong(idUser);
					}
					dao.deletarUser(id);
					msg = "Excluído com sucesso!";
				} catch (SQLException e) {
					e.printStackTrace();
					redirectComMsg(Constantes.ERRORPAGE, "Exception: "+ e.getMessage(), request, response);
				}
			}
			redirectComMsg("/principal/usuario.jsp", msg, request, response);
		} catch (NumberFormatException e) {
			e.printStackTrace();
			redirectComMsg(Constantes.ERRORPAGE, "Exception: "+ e.getMessage(), request, response);
		} catch (ServletException e) {
			e.printStackTrace();
			redirectComMsg(Constantes.ERRORPAGE, "Exception: "+ e.getMessage(), request, response);
		} catch (IOException e) {
			e.printStackTrace();
			redirectComMsg(Constantes.ERRORPAGE, "Exception: "+ e.getMessage(), request, response);
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			String id = request.getParameter("id");
			String msg = "Adição realizada com sucesso!";
			Long idUser = id != null && !id.isEmpty() ? Long.parseLong(id) : null; // preferi separar
			String nome = request.getParameter("nome");
			String email = request.getParameter("email");
			String login = request.getParameter("login");
			String senha = request.getParameter("senha");

			ModelLogin user = new ModelLogin();
			user.setId(idUser);
			user.setNome(nome);
			user.setEmail(email);
			user.setLogin(login);
			user.setSenha(senha);

			//novo usuário o id é null. Se já existe retorna true
			if (dao.validarLogin(user.getLogin()) && user.getId() == null) {
				msg ="Já existe esse login cadastrado!";
			} else {	//salvar um novo ou atualizar
				msg = user.isNovo3() ? "Adicionado com sucesso!" : "Atualizado com sucesso!";
				user = dao.salvarUsuario(user);
			}
			System.out.println(user);

			request.setAttribute("mLogin", user); // retorna os dados para a tela, mas nÃ£o com id gerado
			redirectComMsg("/principal/usuario.jsp", msg, request, response);
		} catch (Exception e) {
			e.printStackTrace();
			redirectComMsg(Constantes.ERRORPAGE, "Exception: "+ e.getMessage(), request, response);
		}
	}

	/**
	 * 
	 * @param pageDestino
	 * @param msgExibirTela
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	private void redirectComMsg(String pageDestino, String msgExibirTela, HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		RequestDispatcher redirecionar = request.getRequestDispatcher(pageDestino);
		if (msgExibirTela != null) {
			request.setAttribute("msg", msgExibirTela);
		}
		redirecionar.forward(request, response);
	}

}
