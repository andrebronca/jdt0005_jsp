package com.arb.servlets;

import java.io.IOException;

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

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			String id = request.getParameter("id");
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

			System.out.println(user);

			dao.salvarUsuario(user);

			request.setAttribute("mLogin", user); // retorna os dados para a tela, mas não com id gerado
			redirectComMsg("/principal/usuario.jsp", "Adição realizada com sucesso!", request, response);
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
