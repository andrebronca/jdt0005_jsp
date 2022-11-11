package com.arb.servlets;

import java.io.Serializable;
import java.sql.SQLException;

import com.arb.dao.DAOUsuarioRepository;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

public class ServletGenericUtil extends HttpServlet implements Serializable {

	private static final long serialVersionUID = -1796125745069345134L;
	
	private DAOUsuarioRepository dao = new DAOUsuarioRepository();
	
    /**
     * Chamar esse método sem instanciar irá retornar uma Exception
     * @param request
     * @return
     * @throws SQLException
     */
    public Long getUserIdLogado(HttpServletRequest request) throws SQLException, NullPointerException {
		HttpSession session = request.getSession();
		String usuarioLogado = (String) session.getAttribute("usuario");
		return dao.consultarUsuario(usuarioLogado, null).getId();
    }
	
}
