package com.arb.servlets;

import java.io.IOException;

import com.arb.dao.DAOLoginRepository;
import com.arb.model.ModelLogin;

import jakarta.servlet.RequestDispatcher;
/*
 * jakarta com tomcat 10 funciona a obtenção via request.
 * Caso mude para tomcat 9 pode ser que tenha que usar o javax em vez do jakarta
 */
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet(urlPatterns = {"/principal/ServletLogin", "/ServletLogin"})
public class ServletLogin extends HttpServlet {
	private static final long serialVersionUID = 4372181908333331079L;
	private final String INDEX = "/index.jsp";
	private final String PRINCIPAL = "/principal/principal.jsp";
	private DAOLoginRepository daoLoginRepository = new DAOLoginRepository();

	public ServletLogin() {
	}

	/*
	 * recebe os dados pela url
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
	}

	/*
	 * recebe os dados de formulário
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String login = request.getParameter("login");
		String senha = request.getParameter("senha");
		//está no filterAutentication, caso o usuário não logado tente acessar
		String url = request.getParameter("url");
		//se não tiver o try/catch não ocorre a validação lógica dos: IFs
		try {
			ModelLogin mLogin = null;

			if ((login != null && !login.isEmpty()) && (senha != null && !senha.isEmpty())) {
				mLogin = new ModelLogin();
				mLogin.setLogin(login);
				mLogin.setSenha(senha);
				
				if (mLogin != null) {
					if (daoLoginRepository.validarAutenticacao(mLogin)) {
						request.getSession().setAttribute("usuario", mLogin.getLogin());
						
						if (url == null || url.equals("null")) {
							url = PRINCIPAL;
						}
						redirectComMsg(url, null, request, response);
					} else {
						redirectComMsg(INDEX, "Informe o login e senha corretamente!", request, response);
					}
				}
			} else {
				redirectComMsg(INDEX, "Informe o login e senha corretamente!", request, response);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private void redirectComMsg(String toPage, String msg, HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		RequestDispatcher redirecionar = request.getRequestDispatcher(toPage);
		if (msg != null) {
			request.setAttribute("msg", msg);
		}
		redirecionar.forward(request, response);
	}
}
