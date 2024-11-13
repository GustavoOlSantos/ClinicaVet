package sistem.entities;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import sistem.enums.AnimalEmergencia;
import sistem.enums.AnimalInternado;
import sistem.enums.AnimalSexo;
import sistem.enums.AnimalTipo;
import sistem.enums.SituacaoPet;
import sistem.exceptions.DomainException;

public class Animal{
	
	private int id;
	private int idCliente;
	private String nome;
	private AnimalSexo sexo;
	private AnimalTipo tipo; 
	private SituacaoPet situacao;
	private AnimalInternado internado;
	private AnimalEmergencia emergencia;
	public int[] servicos = new int[18];
	private double orcamento;
	private String observacoes;
	private String medicamentos;
	
	public String stringServicos;
	
	
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
	
	//======> Id
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	//======> Id Cliente
	public int getIdCliente() {
		return idCliente;
	}
	
	public void setIdCliente(int id) {
		this.idCliente = id;
	}
	
	//======> Nome
	public String getNome() {
		return nome;
	}
	
	public void setNome(String nome) {
		this.nome = nome;
	}
	
	//======> Sexo
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
	
	public void setSexo(AnimalSexo sexo){
		this.sexo = sexo;
	}
	
	//======> Tipo
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
	
	public void setTipo(AnimalTipo tipo){
		this.tipo = tipo;
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
	
	///======> Finalizado
    public SituacaoPet getSituacao() {
        return situacao;
    }
    
    public Integer getIntSituacao() {
    	int stt = -1;
    	
        switch(situacao) {
	        case SAUDAVEL: 				 stt = 0; break;
			case DOENTE: 				 stt = 1; break;
			case EM_RECUPERACAO: 		 stt = 2; break;
			case CRITICO: 				 stt = 3; break;
			case FALECIDO:				 stt = 4; break;
			case AGUARDANDO_ATENDIMENTO: stt = 5; break;
			case EM_ATENDIMENTO: 		 stt = 6; break;
			case INTERNADO: 			 stt = 7; break;
			case ALTA_MEDICA: 			 stt = 8; break;
			case ATIVO: 				 stt = 9; break;
			case INATIVO: 				 stt = 10; break;
			case NAO_LISTADO:			 stt = 11; break;

        }
        
        return stt;
    }
    
    public void setSituacao(int stts) {
    	switch(stts) {
		    case 0: this.situacao = SituacaoPet.SAUDAVEL; break;
			case 1: this.situacao = SituacaoPet.DOENTE; break;
			case 2: this.situacao = SituacaoPet.EM_RECUPERACAO; break;
			case 3: this.situacao = SituacaoPet.CRITICO; break;
			case 4: this.situacao = SituacaoPet.FALECIDO; break;
			case 5: this.situacao = SituacaoPet.AGUARDANDO_ATENDIMENTO; break;
			case 6: this.situacao = SituacaoPet.EM_ATENDIMENTO; break;
			case 7: this.situacao = SituacaoPet.INTERNADO; break;
			case 8: this.situacao = SituacaoPet.ALTA_MEDICA; break;
			case 9: this.situacao = SituacaoPet.ATIVO; break;
			case 10: this.situacao = SituacaoPet.INATIVO; break;
			case 11: this.situacao = SituacaoPet.NAO_LISTADO; break;

    	}
    }	
    
	public void setSituacao(SituacaoPet situacao) {
		this.situacao = situacao;
	}
      
	
	//======> Emergência
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
	
	public void setEmergencia(AnimalEmergencia emergencia){
		this.emergencia = emergencia;
	}
	
	//======> Internado
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
	
	public String getOrcamentoStr(){
		return "R$ " + this.orcamento;
	}
	
	public void setOrcamentoInc(double value){
		orcamento += value;
	}
	
	public void setOrcamento(double value){
		orcamento = value;
	}
	
	//======> Observações
	public void setObservacoes(String observacoes){
		this.observacoes = observacoes;
	}
	
	public String getObservacoes(){
		return this.observacoes;
	}
	
	//======> Medicamentos
		public void setMedicamentos(String medicamentos){
			this.medicamentos = medicamentos;
		}
		
		public String getMedicamentos(){
			return this.medicamentos;
		}
		
	
	//======> Serviços
	public void printServicos(Object[] serv) {
		for(Integer servicoSelecionado : this.servicos) {
			if(servicoSelecionado >= 0 && servicoSelecionado < serv.length && servicoSelecionado != -1) {
				System.out.println(((Servicos)serv[servicoSelecionado]).nome + " " + "-".repeat(50 - ((Servicos)serv[servicoSelecionado]).nome.length()) + " R$" + ((Servicos)serv[servicoSelecionado]).preco);
			}
		}
	}
	
	public int[] getServicos() {
		return servicos;
	}
	
	public void addServicos(int id) {
		for(int i = 0; i < this.servicos.length; i++) {
			if(this.servicos[i] == -1) {
				this.servicos[i] = id;
				return;
			}
		}
	}
	
	public void excluirServico(int id, Object[] serv) {
		
		printServicos(serv);
		
		for(int i = 0; i < serv.length; i++) {
			if(this.servicos[i] == id) {
				this.servicos[i] = -1;
				
				printServicos(serv);
				return;
			}
		}
	}
	
	public List<Servicos> getServicosList(Object[] serv){
		List<Servicos> servList = new ArrayList<>();
		
		for(Integer servAtual : this.servicos) {
			if(servAtual >= 0 && servAtual < serv.length && servAtual != -1) {
				int id = 		((Servicos)serv[servAtual]).id;
				String nome = 	((Servicos)serv[servAtual]).nome;
				double preco =  ((Servicos)serv[servAtual]).preco;
				
				Servicos service = new Servicos(id, nome, preco);
				servList.add(service);
			}
		}
		
		return servList;
	}
	
	public void transformServicos(Object[] serv) {
		
		stringServicos = "";
		boolean flag = false;
		
		for(Integer servicoSelecionado : this.servicos) {
			
			if(servicoSelecionado >= 0 && servicoSelecionado < serv.length && servicoSelecionado != -1) {
				
				if(flag == true) {
					stringServicos += " | ";
				}
				
				String nome = ((Servicos)serv[servicoSelecionado]).nome;
				stringServicos += nome;
				flag = true;
			}
		}
	}

	
	public String getStringServicos() {
		return stringServicos;
	}
}

