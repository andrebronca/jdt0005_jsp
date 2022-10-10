package com.arb.model;

import java.io.Serializable;

public class ModelLogin implements Serializable {
	private static final long serialVersionUID = -3523719899338312152L;

	private String login;
	private String senha;
	private Long id;
	private String nome;
	private String email;

	//deixar anotado, mas não vejo necessidade desse método
	public boolean isNovo() {
		if (this.id == null) {
			return true;	//inserir um novo cadastro
		} else if (this.id != null && this.id > 0) {
			return false;	//atualizar cadastro de usuário
		} else {
			return false;
		}
	}
	
	//testar novos métodos
	public boolean isNovo2() {
		return (this.id == null) ? true : false;
	}
	
	//se for null é novo usuário
	public boolean isNovo3() {
		return this.id == null;
	}
	
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	@Override
	public String toString() {
		return "ModelLogin [login=" + login + ", senha=" + senha + ", id=" + id + ", nome=" + nome + ", email=" + email
				+ "]";
	}
	
	/**
	 * Essa classe ficou estranha, usada pra login e depois pra cadastro de usuÃ¡rio.
	 * Talvez o que ficou mais estranha foi o nome da classe.
	 */

}
