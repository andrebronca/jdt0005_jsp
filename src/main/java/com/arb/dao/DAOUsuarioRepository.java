package com.arb.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.arb.connection.SingleConnectionBanco;
import com.arb.model.ModelLogin;
/*
 * obs.: Em outro momento vou colocar os try/catch todos aqui, pq fica um c�digo muito poluido na servlet.
 */
public class DAOUsuarioRepository {
	private Connection connection;

	public DAOUsuarioRepository() {
		connection = SingleConnectionBanco.getConnection();
	}

	public ModelLogin salvarUsuario(ModelLogin user) throws SQLException {
		if (user.isNovo()) {
			String sql = "INSERT INTO model_login(nome, email, login, senha) VALUES (?, ?, ?, ?)";
			PreparedStatement ps = connection.prepareStatement(sql);
			ps.setString(1, user.getNome());
			ps.setString(2, user.getEmail());
			ps.setString(3, user.getLogin());
			ps.setString(4, user.getSenha());
			ps.executeUpdate();

			connection.commit();

			// usar o this, sen�o n�o traz o id do usu�rio
		} else {
			atualizarUsuario(user);
		}
		return this.consultarUsuario(user.getLogin());
	}
	
	public void atualizarUsuario(ModelLogin user) throws SQLException {
		String sql = "UPDATE model_login SET nome=?, email=?, login=?, senha=? WHERE id = ?";
		PreparedStatement ps = connection.prepareStatement(sql);
		ps.setString(1, user.getNome());
		ps.setString(2, user.getEmail());
		ps.setString(3, user.getLogin());
		ps.setString(4, user.getSenha());
		ps.setLong(5, user.getId());
		ps.executeUpdate();
		connection.commit();
	}

	/**
	 * Retorna o usu�rio caso exista no BD
	 * 
	 * @param login - que ser� pesquisado na tabela
	 * @return
	 * @throws SQLException
	 */
	public ModelLogin consultarUsuario(String login) throws SQLException {
		ModelLogin obj = null;
		// novamente, n�o vou adotar a convers�o para mai�sculo.
//		String sql = "SELECT * FROM model_login where upper(login) = upper('?')";
		String sql = "SELECT id, nome, email, login, senha FROM model_login where login = ?";
		PreparedStatement ps = connection.prepareStatement(sql);
		ps.setString(1, login);
		ResultSet rs = ps.executeQuery();
		while (rs.next()) {
			obj = new ModelLogin();
			obj.setId(rs.getLong("id"));
			obj.setNome(rs.getString("nome"));
			obj.setEmail(rs.getString("email"));
			obj.setLogin(rs.getString("login"));
			obj.setSenha(rs.getString("senha"));
		}
		return obj;
	}
	
	/**
	 * No formulário do usuário tem um campo de pesquisa, é uma parcial de string
	 * vai via ajax para o servlet. Como a busca pode trazer mais de um usuário
	 * tem que retornar uma lista. No form que está em modal terá uma tabela
	 * para listar o que foi comparado.
	 * @param str (é parte do nome que será buscado com like no sql)
	 * @return
	 * @throws SQLException
	 */
	public List<ModelLogin> buscarUsuario(String str) throws SQLException {
		List<ModelLogin> users = new ArrayList<>();
		String sql = "SELECT id, nome from model_login where login like (?)";
		PreparedStatement ps = connection.prepareStatement(sql);
		ps.setString(1, str);
		ResultSet rs = ps.executeQuery();
		while(rs.next()) {
			
		}
		
		return users;
	}

	// nem precisa desse m�todo, d� pra usar o consultarUsuario, se retornar null �
	// pq n�o existe o cadastro
	public boolean validarLogin(String login) throws SQLException {
		String sql = "select count(1) > 0 as existe from model_login where login = ?";
		PreparedStatement ps = connection.prepareStatement(sql);
		ps.setString(1, login);
		ResultSet rs = ps.executeQuery();
		rs.next(); // para acessar o resultado
		return rs.getBoolean("existe");
	}
	
	public void deletarUser(Long id) throws SQLException {
		String sql = "DELETE FROM model_login WHERE id = ?";
		PreparedStatement ps = connection.prepareStatement(sql);
		ps.setLong(1, id);
		ps.executeUpdate();
		connection.commit();
	}
}
