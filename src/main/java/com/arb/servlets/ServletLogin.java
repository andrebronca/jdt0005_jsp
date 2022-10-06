package com.arb.servlets;

import java.io.IOException;

import com.arb.model.ModelLogin;

import jakarta.servlet.RequestDispatcher;
/*
 * jakarta com tomcat 10 funciona a obtençãoo via request.
 * Caso mude para tomcat 9 pode ser que tenha que usar o javax em vez do jakarta
 */
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/ServletLogin")
public class ServletLogin extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public ServletLogin() {
	}

	/*
	 * recebe os dados pela url
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
	}

	/*
	 * recebe os dados de formulÃ¡rio
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		ModelLogin mLogin;
		String login = request.getParameter("login");
		String senha = request.getParameter("senha");
		if ((login != null && !login.isEmpty()) && (senha != null && !senha.isEmpty())) {
			mLogin = new ModelLogin();
			mLogin.setLogin(login);
			mLogin.setSenha(senha);
			
			//simulando um login com direcionamento para página administrativa
			if(mLogin.getLogin().equalsIgnoreCase("admin") && mLogin.getSenha().equalsIgnoreCase("admin")) {
				request.getSession().setAttribute("usuario", mLogin.getLogin());
				RequestDispatcher redirecionar = request.getRequestDispatcher("principal/principal.jsp");
				redirecionar.forward(request, response);
			} else {
				RequestDispatcher redirecionar = request.getRequestDispatcher("index.jsp");
				request.setAttribute("msg", "Informe o login e senha corretamente!");
				redirecionar.forward(request, response);		
			}
		} else {
			RequestDispatcher redirecionar = request.getRequestDispatcher("index.jsp");
			request.setAttribute("msg", "Informe o login e senha corretamente!");
			redirecionar.forward(request, response);
		}

	}

	private void redirectComMsg(String toPage, String msg, HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		RequestDispatcher redirecionar = request.getRequestDispatcher(toPage);
		if(msg != null) {
			request.setAttribute("msg", msg);
		}
		redirecionar.forward(request, response);
	}
}
