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
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	}

	/*
	 * recebe os dados de formulÃ¡rio
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String login = request.getParameter("login");
		String senha = request.getParameter("senha");
		if (login != null && !login.isEmpty() && senha != null && !senha.isEmpty()) {
			ModelLogin mLogin = new ModelLogin();
			mLogin.setLogin(login);
			mLogin.setSenha(senha);
		} else {
			RequestDispatcher redirecionar = request.getRequestDispatcher("index.jsp");
			request.setAttribute("msg", "Informe o login e senha corretamente!");
			redirecionar.forward(request, response);
		}
		
	}

}
