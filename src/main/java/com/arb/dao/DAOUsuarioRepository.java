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
 * obs.: Em outro momento vou colocar os try/catch todos aqui, pq fica um cï¿½digo muito poluido na servlet.
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

			// usar o this, senï¿½o nï¿½o traz o id do usuï¿½rio
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
	 * Retorna o usuï¿½rio caso exista no BD
	 * 
	 * @param login - que serï¿½ pesquisado na tabela
	 * @return
	 * @throws SQLException
	 */
	public ModelLogin consultarUsuario(String login) throws SQLException {
		ModelLogin obj = null;
		// novamente, nï¿½o vou adotar a conversï¿½o para maiï¿½sculo.
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
	 * Fiz esse metodo pq nao havia nenhum buscando por id.
	 * Na consulta por usuario, do forumlario modal, é pra retornar uma lista
	 * entao irá pesquisar daqui cada usuario e adicionar na lista.
	 * Nao retorna a senha preenchida.
	 * @param id
	 * @return
	 * @throws SQLException
	 */
	public ModelLogin getUsuarioPorId(Long id) throws SQLException {
		String sql = "select id, nome, email, login from model_login where id = ?";
		PreparedStatement ps = connection.prepareStatement(sql);
		ps.setLong(1, id);
		ResultSet rs = ps.executeQuery();
		ModelLogin user = null;
		while(rs.next()) {
			user = new ModelLogin();
			user.setId(rs.getLong("id"));
			user.setNome(rs.getString("nome"));
			user.setEmail(rs.getString("email"));
			user.setLogin(rs.getString("login"));
		}
		return user;
	}
	
	/**
	 * No formulario do usuario tem um campo de pesquisa, eh uma parcial de string
	 * vai via ajax para o servlet. Como a busca pode trazer mais de um usuÃ¡rio
	 * tem que retornar uma lista. No form que estÃ¡ em modal terÃ¡ uma tabela
	 * para listar o que foi comparado.
	 * @param nome ( parte do nome que sera buscado com like no sql)
	 * @return
	 * @throws SQLException
	 */
	public List<ModelLogin> buscarUsuario(String nome) throws SQLException {
		List<ModelLogin> users = new ArrayList<>();
		String sql = "SELECT id from model_login where lower(nome) like ? ";
		PreparedStatement ps = connection.prepareStatement(sql);
		ps.setString(1, "%"+ nome.toLowerCase() +"%");
		ResultSet rs = ps.executeQuery();
		while(rs.next()) {
			ModelLogin user = getUsuarioPorId(rs.getLong("id"));
			if (user != null) {
				users.add(user);
			}
		}
		return users;
	}

	// nem precisa desse mï¿½todo, dï¿½ pra usar o consultarUsuario, se retornar null ï¿½
	// pq nï¿½o existe o cadastro
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
