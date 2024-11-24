package sistem.entities.dto;

import sistem.enums.AnimalEmergencia;
import sistem.enums.AnimalInternado;
import sistem.enums.AnimalSexo;
import sistem.enums.AnimalTipo;
import sistem.enums.SituacaoPet;

public class animalDTO {
	
	private String nome;
	private AnimalSexo sexo; 
	private SituacaoPet situacao;
	private AnimalEmergencia emergencia;
	private String nomeTutor;
	
	public animalDTO(String nome, AnimalSexo sexo, SituacaoPet situacao, AnimalEmergencia emergencia, String nomeTutor) {
		this.nome = nome;
		this.sexo = sexo;
		this.situacao = situacao;
		this.emergencia = emergencia;
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
	
	


}
