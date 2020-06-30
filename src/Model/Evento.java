package Model;

import java.time.LocalDate;

public class Evento {
	private int id;
	private String nome;
	private LocalDate data;
	private int partMax;

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

	public LocalDate getData() {
		return data;
	}

	public void setData(LocalDate data) {
		this.data = data;
	}

	public int getQuantiaMax() {
		return partMax;
	}

	public void setQuantiaMax(int quantiaMax) {
		this.partMax = quantiaMax;
	}
}