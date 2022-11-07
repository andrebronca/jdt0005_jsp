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
			String idUser = request.getParameter("id");
			String nomeBuscar = request.getParameter("nomebuscar");
			Long id = null;
			
			if (idUser != null) {
				id = Long.parseLong(idUser);
			}
			
			if (acao != null && !acao.isEmpty()) {
				if(acao.equalsIgnoreCase("deletar")) {
					dao.deletarUser(id);
					msg = "Excluido com sucesso!";
					redirectComMsg("/principal/usuario.jsp", msg, request, response);
				} else if(acao.equalsIgnoreCase("ajaxdeletar")){
					//específico para Ajax
					dao.deletarUser(id);
					response.getWriter().write("Excluido com sucesso via Ajax!");
				} else if (acao.equalsIgnoreCase("buscarNomeAjax")) {
					System.out.println(nomeBuscar);
					//dao.buscarUsuario(nomeBuscar);
					//response.getWriter();
				}	
			}
			
		} catch (NumberFormatException e) {
			e.printStackTrace();
			redirectComMsg(Constantes.ERRORPAGE, "Exception: "+ e.getMessage(), request, response);
		} catch (ServletException e) {
			e.printStackTrace();
			redirectComMsg(Constantes.ERRORPAGE, "Exception: "+ e.getMessage(), request, response);
		} catch (IOException e) {
			e.printStackTrace();
			redirectComMsg(Constantes.ERRORPAGE, "Exception: "+ e.getMessage(), request, response);
		} catch (SQLException e) {
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

			//novo usuario o id é null. Se já existe retorna true
			if (dao.validarLogin(user.getLogin()) && user.getId() == null) {
				msg ="Já existe esse login cadastrado!";
			} else {	//salvar um novo ou atualizar
				msg = user.isNovo3() ? "Adicionado com sucesso!" : "Atualizado com sucesso!";
				user = dao.salvarUsuario(user);
			}
			System.out.println(user);

			request.setAttribute("mLogin", user); // retorna os dados para a tela, mas não com id gerado
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
