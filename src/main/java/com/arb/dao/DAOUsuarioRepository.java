package com.arb.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.arb.connection.SingleConnectionBanco;
import com.arb.model.ModelLogin;

public class DAOUsuarioRepository {
	private Connection connection;

	public DAOUsuarioRepository() {
		connection = SingleConnectionBanco.getConnection();
	}
	
	public ModelLogin salvarUsuario(ModelLogin user) throws SQLException {
		String sql = "INSERT INTO model_login(nome, email, login, senha) VALUES (?, ?, ?, ?)";
		PreparedStatement ps = connection.prepareStatement(sql);
		ps.setString(1, user.getNome());
		ps.setString(2, user.getEmail());
		ps.setString(3, user.getLogin());
		ps.setString(4, user.getSenha());
		ps.executeUpdate();
		
		connection.commit();
		
		//usar o this, senão não traz o id do usuário
		return this.consultarUsuario(user.getLogin());
	}
	
	/**
	 * Retorna o usuário caso exista no BD
	 * @param login - que será pesquisado na tabela
	 * @return
	 * @throws SQLException 
	 */
	public ModelLogin consultarUsuario(String login) throws SQLException {
		ModelLogin obj = null;
		//novamente, não vou adotar a conversão para maiúsculo.
//		String sql = "SELECT * FROM model_login where upper(login) = upper('?')";
		String sql = "SELECT id, nome, email, login, senha FROM model_login where login = ?";
		PreparedStatement ps = connection.prepareStatement(sql);
		ps.setString(1, login);
		ResultSet rs = ps.executeQuery();
		while(rs.next()) {
			obj = new ModelLogin();
			obj.setId(rs.getLong("id"));
			obj.setNome(rs.getString("nome"));
			obj.setEmail(rs.getString("email"));
			obj.setLogin(rs.getString("login"));
			obj.setSenha(rs.getString("senha"));
		}
		return obj;
	}
	
	//nem precisa desse método, dá pra usar o consultarUsuario, se retornar null é pq não existe o cadastro
	public boolean validarLogin(String login) throws SQLException {
		String sql = "select count(1) > 0 as existe from model_login where login = ?";
		PreparedStatement ps = connection.prepareStatement(sql);
		ps.setString(1, login);
		ResultSet rs = ps.executeQuery();
		rs.next();	//para acessar o resultado
		return rs.getBoolean("existe");
	}
}
