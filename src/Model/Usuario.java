package Model;

public class Usuario {
	private int id;
	private String nome;
	private String cpf;
	private String email;
	private int evento_id;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public int getEvento_id() {
		return evento_id;
	}

	public void setEvento_id(int evento_id) {
		this.evento_id = evento_id;
	}
}
