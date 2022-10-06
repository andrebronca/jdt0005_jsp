package com.arb.servlets;

import java.io.IOException;

import com.arb.model.ModelLogin;

import jakarta.servlet.RequestDispatcher;
/*
 * jakarta com tomcat 10 funciona a obten��oo via request.
 * Caso mude para tomcat 9 pode ser que tenha que usar o javax em vez do jakarta
 */
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet(urlPatterns = {"/principal/ServletLogin", "/ServletLogin"})
public class ServletLogin extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private final String INDEX = "/index.jsp";
	private final String PRINCIPAL = "/principal/principal.jsp";

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
		try {
			ModelLogin mLogin = null;

			if ((login != null && !login.isEmpty()) && (senha != null && !senha.isEmpty())) {
				mLogin = new ModelLogin();
				mLogin.setLogin(login);
				mLogin.setSenha(senha);
			} else {
				redirectComMsg(INDEX, "Informe o login e senha corretamente!", request, response);
			}
			// simulando um login com direcionamento para pagina administrativa
			if (mLogin != null) {
				if (mLogin.getLogin().equals("admin") && mLogin.getSenha().equals("admin")) {
					request.getSession().setAttribute("usuario", mLogin.getLogin());
					
					if (url == null || url.equals("null")) {
						url = PRINCIPAL;
					}
					redirectComMsg(url, null, request, response);
				} else {
					redirectComMsg(INDEX, "Informe o login e senha corretamente!", request, response);
				}
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
