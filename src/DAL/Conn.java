package DAL;

import java.sql.Connection;
import java.sql.DriverManager;

public class Conn {
	public Conn() {
		this.servidor = "127.0.0.1";
		this.porta = "3306";
		this.bancoDados = "cred_events";
		this.usuario = "root";
		this.senha = "root";
	}

	public Conn(String servidor, String porta, String bancoDados, String usuario, String senha) {
		this.servidor = servidor;
		this.porta = porta;
		this.bancoDados = bancoDados;
		this.usuario = usuario;
		this.senha = senha;
	}

	private String servidor;
	private String porta;
	private String bancoDados;
	private String usuario;
	private String senha;

	private Connection cnn;

	public Connection getCnn() {
		return this.cnn;
	}

	public void setCnn(Connection value) {
		this.cnn = value;
	}

	protected void abrir() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			this.cnn = DriverManager.getConnection(
					"jdbc:mysql://" + this.servidor + ":" + this.porta + "/" + this.bancoDados + "?useTimezone=true&serverTimezone=UTC", this.usuario,
					this.senha);
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	protected void fechar() {
		try {
			cnn.close();
		} catch (Exception e) {
			System.out.println(e);
		}
	}
}
