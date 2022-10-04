package com.arb.servlets;

import java.io.IOException;
/*
 * jakarta com tomcat 10 funciona a obtenção via request.
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
	 * recebe os dados de formulário
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println(request.getParameter("nome"));
		System.out.println(request.getParameter("idade"));
	}

}
