package com.arb.connection;

import java.sql.Connection;
import java.sql.DriverManager;

public class SingleConnectionBanco {
	private static String url = "jdbc:postgresql://localhost:5432/jdt0005?autoReconnect=true";
	private static String password = "postgres13";
	private static String user = "postgres";
	private static Connection connection = null;
	private static boolean conectado = false;
	
	static {
		conectar();
	}
	
	public SingleConnectionBanco() {
		conectar();
	}
	
	public static Connection getConnection() {
		return connection;
	}
	
	private static void conectar() {
		try {
			if (connection == null) {
				Class.forName("org.postgresql.Driver");
				connection = DriverManager.getConnection(url, user, password);
				connection.setAutoCommit(false);
				conectado = true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static boolean getConectado() {
		return conectado;
	}
}
