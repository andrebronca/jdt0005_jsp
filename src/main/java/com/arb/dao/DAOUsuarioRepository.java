package com.arb.dao;

import java.awt.GridLayout;
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

	public ModelLogin salvarUsuario(ModelLogin user, Long idUserLogado) throws SQLException {
		if (user.isNovo()) {
			String sql = "INSERT INTO model_login(nome, email, login, senha, usuario_id) VALUES (?, ?, ?, ?, ?)";
			PreparedStatement ps = connection.prepareStatement(sql);
			ps.setString(1, user.getNome());
			ps.setString(2, user.getEmail());
			ps.setString(3, user.getLogin());
			ps.setString(4, user.getSenha());
			ps.setLong(5, idUserLogado);
			ps.executeUpdate();

			connection.commit();

			// usar o this, sen�o n�o traz o id do usu�rio
		} else {
			atualizarUsuario(user, idUserLogado);
		}
		return consultarUsuario(user.getLogin(), idUserLogado);
	}
	
	public List<ModelLogin> getTodosUsers(Long idUsuarioLogado) throws SQLException {
		List<ModelLogin> listaUsuarios = new ArrayList<>();
		String sql = "select id, nome, email, login from model_login ";
		if (!isAdmin(idUsuarioLogado)) {
			sql += " where usuario_id = ? and not useradmin ";
		}
		PreparedStatement ps = connection.prepareStatement(sql);
		if (!isAdmin(idUsuarioLogado)) {
			ps.setLong(1, idUsuarioLogado);
		}
		ResultSet rs = ps.executeQuery();
		while(rs.next()) {
			ModelLogin user = new ModelLogin();
			user.setId(rs.getLong("id"));
			user.setNome(rs.getString("nome"));
			user.setEmail(rs.getString("email"));
			user.setLogin(rs.getString("login"));
			listaUsuarios.add(user);
		}
		
		return listaUsuarios;
	}
	
	//vou implentar quem fez a atualização
	public void atualizarUsuario(ModelLogin user, Long idUserLogado) throws SQLException {
		String sql = "UPDATE model_login SET nome=?, email=?, login=?, senha=?, usuario_id=? WHERE id = ?";
		PreparedStatement ps = connection.prepareStatement(sql);
		ps.setString(1, user.getNome());
		ps.setString(2, user.getEmail());
		ps.setString(3, user.getLogin());
		ps.setString(4, user.getSenha());
		ps.setLong(5, idUserLogado);
		ps.setLong(6, user.getId());
		ps.executeUpdate();
		connection.commit();
	}

	/**
	 * Retorna o usuario caso exista no BD
	 * idUserLogado é null quando há o primeiro acesso
	 * @param login - que sera pesquisado na tabela
	 * @param idUserLogado - só visualiza o que o usuário cadastrou
	 * @return
	 * @throws SQLException
	 * Alex resolveu que só pode consultar o que o usuario cadastrou
	 * Fica estranho isso agora, e se o usuario que cadastrou saiu da empresa.
	 * O admin tem que poder mexer em qualquer um.
	 * Dependendo o que for cadastrado não faz sentido, pois pode ter outra pessoa precisando ver o que outros cadastraram
	 */
	public ModelLogin consultarUsuario(String login, Long idUserLogado) throws SQLException {
		ModelLogin obj = null;
		// novamente, nao vou adotar a conversao para maiusculo.
//		String sql = "SELECT * FROM model_login where upper(login) = upper('?')";
		String sql = "SELECT id, nome, email, login, senha FROM model_login where login = ? ";
		
		//esse ajuste é pq a ServletGenericUtil não tem um id pra informar, teria que ter um método igual esse só com login
		if (idUserLogado != null && !isAdmin(idUserLogado)) {
			sql += " and usuario_id = ? and not useradmin"; 
		}
		
		PreparedStatement ps = connection.prepareStatement(sql);
		ps.setString(1, login);
		
		if (idUserLogado != null) {
			ps.setLong(2, idUserLogado);
		}
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
	
	//Admin tem que poder visualizar todos
	//não implementado pelo curso
	public boolean isAdmin(String login) throws SQLException {
		String sql = "select useradmin from model_login where login = ?";
		PreparedStatement ps = connection.prepareStatement(sql);
		ps.setString(1, login);
		
		return checkUserAdm(ps);
	}
	
	//não implementado pelo curso
	public boolean isAdmin(Long idUser) throws SQLException {
		String sql = "select useradmin from model_login where id = ?";
		PreparedStatement ps = connection.prepareStatement(sql);
		ps.setLong(1, idUser);
		return checkUserAdm(ps);
	}
	
	//não implementado pelo curso
	private boolean checkUserAdm(PreparedStatement ps) throws SQLException {
		boolean isAdm = false;
		ResultSet rs = ps.executeQuery();
		while(rs.next()) {
			isAdm = rs.getBoolean("useradmin");
		}
		return isAdm;
	}
	
	/**
	 * Fiz esse metodo pq nao havia nenhum buscando por id.
	 * Na consulta por usuario, do forumlario modal, � pra retornar uma lista
	 * entao ir� pesquisar daqui cada usuario e adicionar na lista.
	 * Nao retorna a senha preenchida.
	 * @param id
	 * @return
	 * @throws SQLException
	 */
	public ModelLogin getUsuarioPorId(Long id, Long idUserLogado) throws SQLException {
		String sql = "select id, nome, email, login from model_login where id = ? ";
		
		if (!isAdmin(idUserLogado)) {
			sql += " and not useradmin and usuario_id = ?";
		}
		PreparedStatement ps = connection.prepareStatement(sql);
		ps.setLong(1, id);
		ps.setLong(2, idUserLogado);
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
	 * vai via ajax para o servlet. Como a busca pode trazer mais de um usuário
	 * tem que retornar uma lista. No form que está em modal terá uma tabela
	 * para listar o que foi comparado.
	 * @param nome ( parte do nome que sera buscado com like no sql)
	 * @return
	 * @throws SQLException
	 */
	public List<ModelLogin> buscarUsuario(String nome, Long idUserLogado) throws SQLException {
		List<ModelLogin> users = new ArrayList<>();
		String sql = "SELECT id from model_login where lower(nome) like ? and usuario_id = ? ";
		if (!isAdmin(idUserLogado)) {
			sql += " and usuario_id = ? not useradmin ";
		}
		PreparedStatement ps = connection.prepareStatement(sql);
		ps.setString(1, "%"+ nome.toLowerCase() +"%");
		ps.setLong(2, idUserLogado);
		ResultSet rs = ps.executeQuery();
		while(rs.next()) {
			ModelLogin user = getUsuarioPorId(rs.getLong("id"), idUserLogado);
			if (user != null) {
				users.add(user);
			}
		}
		return users;
	}

	// nem precisa desse m�todo, d� pra usar o consultarUsuario, se retornar null �
	// pq n�o existe o cadastro
	public boolean validarLogin(String login) throws SQLException {
		String sql = "select count(1) > 0 as existe from model_login where login = ? ";
		PreparedStatement ps = connection.prepareStatement(sql);
		ps.setString(1, login);
		ResultSet rs = ps.executeQuery();
		rs.next(); // para acessar o resultado
		return rs.getBoolean("existe");
	}
	
	//em uma aplicação, farei exclusão lógica e anotar quem fez
	public void deletarUser(Long id) throws SQLException {
		String sql = "DELETE FROM model_login WHERE id = ? ";
		if (!isAdmin(id)) {
			sql += " and not useradmin ";
		}
		PreparedStatement ps = connection.prepareStatement(sql);
		ps.setLong(1, id);
		ps.executeUpdate();
		connection.commit();
	}
}
