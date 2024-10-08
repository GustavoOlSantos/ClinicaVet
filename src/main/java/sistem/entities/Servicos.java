package sistem.entities;

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
}
