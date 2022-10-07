package com.arb.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.arb.connection.SingleConnectionBanco;
import com.arb.model.ModelLogin;

public class DAOUsuarioRepository {
	private Connection connection;

	public DAOUsuarioRepository() {
		connection = SingleConnectionBanco.getConnection();
	}
	
	public void salvarUsuario(ModelLogin user) throws SQLException {
		String sql = "INSERT INTO model_login(nome, email, login, senha) VALUES (?, ?, ?, ?)";
		PreparedStatement ps = connection.prepareStatement(sql);
		ps.setString(1, user.getNome());
		ps.setString(2, user.getEmail());
		ps.setString(3, user.getLogin());
		ps.setString(4, user.getSenha());
		ps.executeUpdate();
		
		connection.commit();
	}
}
