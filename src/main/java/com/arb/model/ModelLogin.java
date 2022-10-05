package com.arb.model;

import java.io.Serializable;

public class ModelLogin implements Serializable {
	private static final long serialVersionUID = -3523719899338312152L;

	private String login;
	private String senha;

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

}
