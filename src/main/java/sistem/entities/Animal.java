package sistem.entities;

import java.io.Serializable;
import java.util.Arrays;

import sistem.enums.AnimalEmergencia;
import sistem.enums.AnimalInternado;
import sistem.enums.AnimalSexo;
import sistem.enums.AnimalTipo;
import sistem.exceptions.DomainException;

public class Animal implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private String nome;
	private AnimalSexo sexo;
	private AnimalTipo tipo; 
	private AnimalEmergencia emergencia;
	private AnimalInternado internado;
	
	public int[] servicos = new int[9];
	private double orcamento;
	
	public Animal() {
		Arrays.fill(this.servicos, -1); //=> Define toda a array como não incializada
	}
	
	public Animal(String nome, int tipo, int sexo) throws DomainException{
		this.nome = nome;
		
		if(nome.length() == 0) {
			throw new DomainException("O nome do Animal não pode estar vazio.");
		}
		
		setSexo(sexo);
		setTipo(tipo);
		
		
		Arrays.fill(this.servicos, -1); //=> Define toda a array como não incializada
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
	
	public int getIntSexo() {
		
		int sexo = -1;
		
		switch(this.sexo){
			case MACHO: 		sexo = 0; break;
			case FÊMEA: 		sexo = 1; break;
			case HERMAFRODITA: 	sexo = 2; break;
		}
		
		return sexo;
	}
	
	public void setSexo(int sexo) throws DomainException {
		switch(sexo) {
			case 0: this.sexo = AnimalSexo.MACHO; 		 break;
			case 1: this.sexo = AnimalSexo.FÊMEA; 		 break;
			case 2: this.sexo = AnimalSexo.HERMAFRODITA; break;
			default: throw new DomainException("Opção Inválida apra Sexo do Animal.");
		}
	}
	
	public void setTipo(int tipo) throws DomainException {
		switch(tipo) {
			case 0: this.tipo = AnimalTipo.GATO; 				break;
			case 1: this.tipo = AnimalTipo.CACHORRO; 			break;
			case 2: this.tipo = AnimalTipo.AVE;					break;
			case 3: this.tipo = AnimalTipo.COELHO; 				break;
			case 4: this.tipo = AnimalTipo.TARTARUGA; 			break;
			case 5: this.tipo = AnimalTipo.COBRA; 				break;
			case 6: this.tipo = AnimalTipo.LAGARTO; 			break;
			case 7: this.tipo = AnimalTipo.OUTROS_SILVESTRES; 	break;
			default: throw new DomainException("Tipo Inválido de animal.");
		}
	}
	
	public AnimalTipo getTipo() {
		return tipo;
	}
	
	public int getIntTipo() {
		
		int type = -1;
		
		switch(tipo) {
			case GATO: 			 	type = 0;	break;
			case CACHORRO: 		 	type = 1; 	break;
			case AVE:				type = 2; 	break;
			case COELHO: 			type = 3;	break;
			case TARTARUGA: 		type = 4; 	break;
			case COBRA: 			type = 5;	break;
			case LAGARTO: 		   	type = 6; 	break;
			case OUTROS_SILVESTRES: type = 7; 	break;
		}
		
		return type;
	}
	
	public AnimalEmergencia getEmergencia() {
		return emergencia;
	}
	
	public int getIntEmergencia() {
		
		int emergencia = -1;
		
		switch(this.emergencia){
			case SIM: emergencia = 0; break;
			case NÃO: emergencia = 1; break;
		}
		
		return emergencia;
	}
	
	public void setEmergencia(int emergencia) throws DomainException {
		switch(emergencia) {
			case 0: this.emergencia = AnimalEmergencia.SIM; 		 break;
			case 1: this.emergencia = AnimalEmergencia.NÃO; 		 break;
			default: throw new DomainException("Opção Inválida para Emergência.");
		}
	}
	
	public AnimalInternado getInternado() {
		return internado;
	}
	
	public int getIntInternado() {
		
		int internado = -1;
		
		switch(this.internado){
			case SIM: internado = 0; break;
			case NÃO: internado = 1; break;
		}
		
		return internado;
	}
	
	public void setInternado(int internado) throws DomainException {
		switch(internado) {
			case 0: this.internado = AnimalInternado.SIM; 		 break;
			case 1: this.internado = AnimalInternado.NÃO; 		 break;
			default: throw new DomainException("Opção Inválida para Internação.");
		}
	}
	
	//======> Orçamento
	public double getOrcamento(){
		return this.orcamento;
	}
	
	public void setOrcamento(double value){
		orcamento += value;
	}
	
	//======> Serviços
	public void printServicos(Servicos[] serv) {
		for(Integer servicoSelecionado : this.servicos) {
			if(servicoSelecionado >= 0 && servicoSelecionado < serv.length && servicoSelecionado != -1) {
				System.out.println(serv[servicoSelecionado].nome + " " + "-".repeat(50 - serv[servicoSelecionado].nome.length()) + " R$" + serv[servicoSelecionado].preco);
			}
		}
	}
	
	public int[] getServicos() {
		return servicos;
	}
}

