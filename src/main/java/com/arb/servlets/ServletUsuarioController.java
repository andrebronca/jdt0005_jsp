package com.arb.servlets;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import com.arb.constantes.Constantes;
import com.arb.dao.DAOUsuarioRepository;
import com.arb.model.ModelLogin;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet(urlPatterns = { "/ServletUsuarioController" })
public class ServletUsuarioController extends ServletGenericUtil {

	private static final long serialVersionUID = 13025390838445016L;
	private final String PG_USER = "/principal/usuario.jsp";
	private DAOUsuarioRepository dao = new DAOUsuarioRepository();

	public ServletUsuarioController() {
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			
			String acao = request.getParameter("acao");
			String msg = null;
			String idUser = request.getParameter("id");
			
			Long id = null;
			
			if (idUser != null) {
				id = Long.parseLong(idUser);
			}
			
			if (acao != null && !acao.isEmpty()) {
				if(acao.equalsIgnoreCase("deletar")) {
					boolean removido = dao.deletarUser(id);
					if (removido) {
						msg = "Excluido com sucesso!";
					} else {
						msg = "Não é possível remover o id: "+ id +", pois este usuário realizou cadastros!";
					}
					
					request.setAttribute("usersLista", dao.getTodosUsers(super.getUserIdLogado(request)));
					
					redirectComMsg(PG_USER, msg, request, response);
				} else if(acao.equalsIgnoreCase("ajaxdeletar")){
					//específico para Ajax
					dao.deletarUser(id);
					response.getWriter().write("Excluido com sucesso via Ajax!");
				} else if (acao.equalsIgnoreCase("buscarNomeAjax")) {
					String nomeBuscar = request.getParameter("nomebuscar");
					List<ModelLogin> listaUsuarios = dao.buscarUsuario(nomeBuscar, super.getUserIdLogado(request));
					ObjectMapper mapper = new ObjectMapper();
					String jsonUsuarios = mapper.writeValueAsString(listaUsuarios);
					response.getWriter().write(jsonUsuarios);
				} else if (acao.equalsIgnoreCase("buscarEditar")) {
					String idEditar = request.getParameter("id");
					if (idEditar != null) {
						ModelLogin user = dao.getUsuarioPorId(Long.parseLong(idEditar), super.getUserIdLogado(request));
						msg = "Usuario pronto para edi&ccedil;&atilde;o";
						request.setAttribute("mLogin", user); // retorna os dados para a tela, mas não com id gerado
						
						request.setAttribute("usersLista", dao.getTodosUsers(super.getUserIdLogado(request)));
						redirectComMsg(PG_USER, msg, request, response);
					}
				} else if (acao.equalsIgnoreCase("listarUser")) {
//					List<ModelLogin> usersLista = dao.getTodosUsers();
//					request.setAttribute("usersLista", usersLista);
//					msg = usersLista.size() > 0 ? "Total de usuários: "+ usersLista.size() : null; 
//					redirectComMsg(PG_USER, msg, request, response);
					redirecionarListaUsuarios(request, response);
				}
			} else {
//				List<ModelLogin> usersLista = dao.getTodosUsers();
//				request.setAttribute("usersLista", usersLista);
//				msg = usersLista.size() > 0 ? "Total de usuários: "+ usersLista.size() : null; 
//				redirectComMsg(PG_USER, msg, request, response);
				redirecionarListaUsuarios(request, response);
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
	
	private void redirecionarListaUsuarios(HttpServletRequest request, HttpServletResponse response) 
			throws SQLException, ServletException, IOException {
		List<ModelLogin> usersLista = dao.getTodosUsers(super.getUserIdLogado(request));
		request.setAttribute("usersLista", usersLista);
		String msg = usersLista.size() > 0 ? "Total de usuários: "+ usersLista.size() : null; 
		redirectComMsg(PG_USER, msg, request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			
			String id = request.getParameter("id");
			String msg = "Adicao realizada com sucesso!";
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
				msg ="Ja existe esse login cadastrado!";
			} else {	//salvar um novo ou atualizar
				msg = user.isNovo3() ? "Adicionado com sucesso!" : "Atualizado com sucesso!";
				user = dao.salvarUsuario(user, super.getUserIdLogado(request));
			}
			System.out.println(user);

			request.setAttribute("mLogin", user); // retorna os dados para a tela, mas não com id gerado
			request.setAttribute("usersLista", dao.getTodosUsers(super.getUserIdLogado(request)));	//lista de usuarios
			redirectComMsg(PG_USER, msg, request, response);
		} catch (Exception e) {
			e.printStackTrace();
			redirectComMsg(Constantes.ERRORPAGE, "Exception: "+ e.getMessage(), request, response);
		}
	}

	/**
	 * Fiz esse metodo para facilitar a codificacao e evitar que fique muito grando devido a quantidade
	 * de vezes em que o codigo eh chamado.
	 * @param pageDestino
	 * @param msgExibirTela
	 * @param request (recebe um objeto que ser� percorrido para preencher o formulario)
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
