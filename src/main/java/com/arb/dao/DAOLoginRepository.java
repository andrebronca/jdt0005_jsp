package com.arb.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.arb.connection.SingleConnectionBanco;
import com.arb.model.ModelLogin;

public class DAOLoginRepository {
	private Connection connection;

	public DAOLoginRepository() {
		connection = SingleConnectionBanco.getConnection();
	}

	/**
	 * @param obj: do tipo ModelLogin
	 * @return true se existir o usuário, false se não existir cadastrado no BD
	 * @throws Exception: Filter irá mostrar a exception
	 */
	public boolean validarAutenticacao(ModelLogin obj) throws Exception {
		//Não vou seguir a orientação de elevar para maiúsculo login e senha.
//		String sql1 = "select * from model_login where upper(login) = upper(?) and upper(senha) = upper(?)";
		String sql = "select login, senha from model_login where login = ? and senha = ?";
		PreparedStatement ps = connection.prepareStatement(sql);
		ps.setString(1, obj.getLogin());
		ps.setString(2, obj.getSenha());
		ResultSet rs = ps.executeQuery();
		
		if(rs.next()) {
			return true;	//existe o usuário e está autenticado
		}
		return false;	//não autenticado
	}
}
