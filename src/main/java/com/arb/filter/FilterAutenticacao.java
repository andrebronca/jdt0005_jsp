package com.arb.filter;

import java.io.IOException;

import jakarta.servlet.Filter;
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
public class FilterAutenticacao extends HttpFilter implements Filter {

	private static final long serialVersionUID = 5444527906793324109L;

	public FilterAutenticacao() {
	}

	// encerra os processos quando o servidor é parado
	// ex. finaliza a conexão com BD
	public void destroy() {
	}

	// intercepta as requisições e as respostas no sistema
	// tudo o que fizer no sistema passa por aqui.
	// ex. validar autenticação, commit e rollback, redirecionamento específico
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		
		HttpServletRequest req = (HttpServletRequest) request;
		HttpSession session = req.getSession();
		String usuarioLogado = (String) session.getAttribute("usuario");
		String urlAutenticar = req.getServletPath(); // url que está sendo acessada
		try {
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
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// inicia os processos ou recursos quando o servidor sobe o projeto
	// ex. iniciar a conexão com o BD
	public void init(FilterConfig fConfig) throws ServletException {
	}

}
