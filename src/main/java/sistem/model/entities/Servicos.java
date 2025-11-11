package sistem.model.entities;

public class Servicos {
	public int id;
	public String nome;
	public double preco;
	
	public Servicos(int id, String nome, double preco) {
		this.id = id;
		this.nome = nome;
		this.preco = preco;
	}
	
	public void Exibir() {
		System.out.printf("[%d]: %s "+ "-".repeat(50-nome.length()) +" R$%.2f \n", id, nome, preco);
	}

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

	public double getPreco() {
		return preco;
	}
	
	public String getPrecoStr() {
		return "R$" + preco;
	}

	public void setPreco(double preco) {
		this.preco = preco;
	}
	
	
}
