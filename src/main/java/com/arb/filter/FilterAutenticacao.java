package com.arb.filter;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import com.arb.connection.SingleConnectionBanco;

//import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@WebFilter(urlPatterns = { "/principal/*" }) // intercepta todas as requisições que vierem do projeto ou mapeamento
public class FilterAutenticacao extends HttpFilter {

	private static final long serialVersionUID = 5444527906793324109L;
	private static Connection connection;

	public FilterAutenticacao() {
	}

	// encerra os processos quando o servidor é parado
	// ex. finaliza a conexão com BD
	public void destroy() {
		try {
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	// intercepta as requisições e as respostas no sistema
	// tudo o que fizer no sistema passa por aqui.
	// ex. validar autenticação, commit e rollback, redirecionamento específico
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		try {
			HttpServletRequest req = (HttpServletRequest) request;
			HttpSession session = req.getSession();
			String usuarioLogado = (String) session.getAttribute("usuario");
			String urlAutenticar = req.getServletPath(); // url que está sendo acessada
			// validar se está logado, senão redirecionar para a tela de login
			if (usuarioLogado == null && !urlAutenticar.equals("/principal/ServletLogin")) {
				// redireciona para a página de login e envia a url que tentou acessar, caso
				// tenha acesso, será redirecionado.
				RequestDispatcher redireciona = request.getRequestDispatcher("/index.jsp?url=" + urlAutenticar);
				request.setAttribute("msg", "Por favor realizar o login!");
				redireciona.forward(request, response);
				return; // para a execução e redireciona para o login
			} else {
				chain.doFilter(request, response);
			}
			connection.commit(); // se tudo ocorreu bem, salva as alterações no BD
		} catch (Exception e) {
			e.printStackTrace();

			// aqui não vou criar um método como foi na ServletLogin
			RequestDispatcher redirecionar = request.getRequestDispatcher("/erro.jsp");
			request.setAttribute("msg", e.getMessage());
			redirecionar.forward(request, response);
			/*
			 * Um problema que vejo é o fornecimento de detalhes, seria melhor criar uma outra forma de tratamento.
			 * ERROR: column "senhas" does not exist Dica: Perhaps you meant to reference the column "model_login.senha". Posição: 59
			 */

			try {
				connection.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}
	}

	// inicia os processos ou recursos quando o servidor sobe o projeto
	// ex. iniciar a conexão com o BD
	public void init(FilterConfig fConfig) throws ServletException {
		connection = SingleConnectionBanco.getConnection();
	}

}
