package sistem.model.entities.dto;

import sistem.model.enums.AnimalEmergencia;
import sistem.model.enums.AnimalSexo;
import sistem.model.enums.SituacaoPet;

public class animalDTO {
	
	private int id;
	private int idCliente;
	private String nome;
	private AnimalSexo sexo; 
	private SituacaoPet situacao;
	private AnimalEmergencia emergencia;
	private String nomeTutor;
	private String servicos;
	private String orcamento;
	
	public animalDTO(String nome, AnimalSexo sexo, SituacaoPet situacao, AnimalEmergencia emergencia, String nomeTutor) {
		this.nome = nome;
		this.sexo = sexo;
		this.situacao = situacao;
		this.emergencia = emergencia;
		this.nomeTutor = nomeTutor;
	}
	
	public animalDTO(int id, int idCliente, String nome, AnimalSexo sexo, SituacaoPet situacao, AnimalEmergencia emergencia, String servicos, String orcamento,  String nomeTutor) {
		
		this.id = id;
		this.idCliente = idCliente;
		this.nome = nome;
		this.sexo = sexo;
		this.situacao = situacao;
		this.emergencia = emergencia;
		this.servicos = servicos;
		this.orcamento = orcamento;
		this.nomeTutor = nomeTutor;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public AnimalSexo getSexo() {
		return sexo;
	}

	public void setSexo(AnimalSexo sexo) {
		this.sexo = sexo;
	}

	public SituacaoPet getSituacao() {
		return situacao;
	}

	public void setSituacao(SituacaoPet situacao) {
		this.situacao = situacao;
	}

	public AnimalEmergencia getEmergencia() {
		return emergencia;
	}

	public void setEmergencia(AnimalEmergencia emergencia) {
		this.emergencia = emergencia;
	}

	public String getNomeTutor() {
		return nomeTutor;
	}

	public void setNomeTutor(String nomeTutor) {
		this.nomeTutor = nomeTutor;
	}
	public String getServicos() {
		return servicos;
	}

	public void setServicos(String servicos) {
		this.servicos = servicos;
	}

	public String getOrcamento() {
		return orcamento;
	}

	public void setOrcamento(String orcamento) {
		this.orcamento = orcamento;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getIdCliente() {
		return idCliente;
	}

	public void setIdCliente(int idCliente) {
		this.idCliente = idCliente;
	}
	
	
	


}
