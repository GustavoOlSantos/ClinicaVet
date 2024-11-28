package sistem.entities;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import sistem.enums.FormaPagamento;
import sistem.enums.Situacao;
import sistem.enums.StatusPagamento;
import sistem.exceptions.DomainException;
import sistem.services.CpfCnpjMask;
import sistem.services.TelefoneMask;


public class Cliente{
	
	private final int NAO_INICIALIZADO = -1;
	private final int LIMITE_PARCELA = 6;
	public final DateTimeFormatter timeFormat = DateTimeFormatter.ofPattern("dd/MM/yyyy - HH:mm");

	private int id;
	private String nome;
	private String cpf;
	private String email;
	private String telefone;
	public int qtdAnimal;
	public Animal[] animal;
	private double orcamentoTotal;
	private StatusPagamento status;
	private FormaPagamento formaPagamento;
	public int parcelaPagamento;
	private Situacao trabalho;
	private String observacao;
	private LocalDateTime dataCadastro;
	private LocalDateTime dataFinalizado;

	public Cliente() {
		this.trabalho = Situacao.EM_ATENDIMENTO; 		 	
		this.status = StatusPagamento.PENDENTE;
	}

	public Cliente(String nome, String cpf, String num, int qtd) throws DomainException {
		
		if(nome.length() == 0) {
			throw new DomainException("O nome do Cliente não pode estar vazio.");
		}
		if(num.length() == 0) {
			throw new DomainException("O telefone do Cliente não pode estar vazio.");
		}
		
		if(qtd == 0) {
			throw new DomainException("Não é possível cadastrar um Cliente sem nenhum animal.");
		}
		
		this.nome 		= nome;
		this.cpf 		= CpfCnpjMask.Mask(cpf);
		this.telefone 	= TelefoneMask.Mask(TelefoneMask.Unmask(num));
		this.qtdAnimal 	= qtd;

		this.animal 	= new Animal[qtd];
		this.trabalho = Situacao.EM_ATENDIMENTO; 		 	
		this.status = StatusPagamento.PENDENTE;
		this.dataCadastro = LocalDateTime.now();
		
	}
	
	//======> Id
	public int getId() {
		return this.id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	//======> Nome
	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}
	
	//======> CPF
	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = CpfCnpjMask.Mask(cpf);
	}
	
	//======> Telefone
	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) throws DomainException{
		this.telefone = TelefoneMask.Mask(TelefoneMask.Unmask(telefone));
	}
	
	//======> Email
	public String getEmail() {
		return email;
	}

	public void setEmail(String email){
			this.email = email;
		}
	
	//======> Orçamento
	public double getOrcamentoTotal(){
		return this.orcamentoTotal;
	}
	
	public String getOrcamentoTotalStr(){
		return "R$ " + this.orcamentoTotal;
	}

	public void setOrcamentoInc(double value){
		orcamentoTotal += value;
	}
	
	public void setOrcamentoTotal(double value){
		this.orcamentoTotal = value;
	}
	
	//======> Forma de Pagamento
    public FormaPagamento getFormaPagamento() {
        return formaPagamento;
    }
    
    public int getIntFormaPagamento(){
    	int fpg = -1;
    	
        switch(formaPagamento) {
        	case DEBITO: fpg = 0; break;
        	case CREDITO: fpg = 1; break;
        	case DINHEIRO: fpg = 2; break;
        	case PIX: fpg = 3; break;
        }
        
        return fpg;
    }
    
    public void setFormaPagamento(int formaPg) throws DomainException {
    	
    	switch(formaPg) {
			case 0: this.formaPagamento = FormaPagamento.DEBITO; break;
			case 1: this.formaPagamento = FormaPagamento.CREDITO; break;
			case 2: this.formaPagamento = FormaPagamento.DINHEIRO; break;
			case 3: this.formaPagamento = FormaPagamento.PIX; break;
			default: throw new DomainException("Forma de Pagamento Inválida");
    	}
        
        //=> Define o Valor da Parcela como 1
        if(formaPg != 1) {
        	this.parcelaPagamento = 1;
        }
    }
    
	public void setFormaPagamento(FormaPagamento selecionado) {
		this.formaPagamento = selecionado;
	}
    
    //======> Status do Pagamento
    public StatusPagamento getStatusPagamento() {
    	return status;
    }
    
    public int getIntStatusPagamento(){
    	int spg = -1;
    	
    	switch(status) {
	    	case PENDENTE: 	spg = 0; break;
			case PAGO: 		spg = 1; break;
    	}
    	
    	return spg;
    }
    
    public void setStatusPagamento(int stts){
    	switch(stts) {
    		case 0:  this.status = StatusPagamento.PENDENTE; break;
    		case 1: this.status = StatusPagamento.PAGO; break;
    	}
    }
    
	public void setStatusPagamento(StatusPagamento stts) {
		this.status = stts;
	}
    
    //======> Parcelas
    public int getParcelas() {
    	return parcelaPagamento;
    }
    
    public void setParcela(int parcela) throws DomainException {
    	if(parcela > LIMITE_PARCELA) {
    		throw new DomainException("Erro: Quantidade Inválida de parcelas.");
    	}
    	this.parcelaPagamento = parcela;
    }
    
    ///======> Finalizado
    public Situacao getSituacao() {
        return trabalho;
    }
    
    public Integer getIntSituacao() {
    	int stt = -1;
    	
        switch(trabalho) {
	        case EM_ATENDIMENTO: stt = 0; break;
			case FINALIZADO: stt = 1; break;
			case CANCELADO: stt = 2; break;
			case EXCLUÍDO: stt = 3; break;
        }
        
        return stt;
    }
    
    public void setSituacao(int stts) {
    	switch(stts) {
		    case 0: this.trabalho = Situacao.EM_ATENDIMENTO; break;
			case 1: this.trabalho = Situacao.FINALIZADO; break;
			case 2: this.trabalho = Situacao.CANCELADO; break;
			case 3: this.trabalho = Situacao.EXCLUÍDO; break;
    	}
    }	
    
	public void setSituacao(Situacao stts) {
		this.trabalho = stts;
	}
    
    //======> Observações do Cliente
  	public void setObservacao(String obs) {
  		this.observacao = obs;
  		
  	}
  	
  	public String getObservacao() {
      	return observacao;
      }
    
    //======> Data de Cadastro
	public void setDataCadastro(LocalDateTime date) {
		this.dataCadastro = date;
		
	}
	
	public LocalDateTime getDataCadastro() {
    	return dataCadastro;
    }
	
	//======> Data Finalizado
	public void setDataFinalizado(LocalDateTime date) {
		this.dataFinalizado = date;
		
	}
	
	public LocalDateTime getDataFinalizado() {
    	return dataFinalizado;
    }
    
	
	public void Finalizar() {
		this.setDataFinalizado(LocalDateTime.now());
		this.setSituacao(Situacao.FINALIZADO);
	}
	
    //=> Provável descarte
    public void listaEdit() {
    	System.out.println("[0] Nome: " + nome);
    	System.out.println("[1] CPF: " + cpf);
    	System.out.println("[2] Telefone: " + telefone);
 
    	System.out.println("[3] Forma de Pagamento: " + formaPagamento);
    	
    	if(formaPagamento == FormaPagamento.CREDITO) {
    		System.out.println("[4] Qtd Parcela: " + parcelaPagamento);
    	}
    	
    	else {
    		System.out.println("[4] Qtd Parcela: Não se aplica");
    	}
    	
    	System.out.println("[5] Status de Pagamento: " + status);
    	System.out.println("[6] Situação: " + trabalho);
    	
    	/*int i = 0, index = 7;
    	for(Animal pet : animal) {
    		System.out.printf("[%d] Nome do pet %d (%s): %s \n", index, i, pet.getTipo(), pet.getNome());
    		index++;
    		System.out.printf("[%d] Sexo do pet %d: %s \n", index, i, pet.getSexo());
    		
    		index++;
    		i++;
    	}*/
    }
    
    //=> Pode ser útil se modificar
    public String toString() {
    	
    	String objStr;
    	
    	objStr = "Nome: " + nome + " / Status: " + trabalho  + " / statusPg: " + status;
    	
    	/* Printa o Nome dos Animais
		for(int j = 0; j < qtdAnimal; j++) {
			
			objStr += " - pet: " + animal[j].getNome() + " ("+ animal[j].getTipo() +")";
		} */
    	
    	return objStr;
    }
    
    /* MANTER ESSA FUNÇÂO
     * Serve para reunir o nome
     * e espécie de todos os 
     * animais de um cliente
     * em uma string
     * (Usado em tabelas de cliente)
     */
    public String getAnimal() {
    	String objStr = "";
    	//=> Printa o Nome dos Animais
		for(int j = 0; j < qtdAnimal; j++) {	
			objStr += animal[j].getNome() + " ("+ animal[j].getTipo() +")";
			
			if(j + 1 < qtdAnimal) {
				objStr += " | ";
			}
		}
    	
    	return objStr;
	}

    public void printClienteSheet(Object[] serv) {
    	//System.out.println("id: " + this.getId());
		System.out.println("Nome: " + this.getNome());
		System.out.print("CPF: " + this.getCpf());
		System.out.println(" ".repeat(5) + "Telefone: " + this.getTelefone());
		
		System.out.println("\n===========> Pets Cadastrados");
		
		for(Animal pet : this.animal) {
			System.out.print("\nNome do pet: " + pet.getNome());
			System.out.print(" ".repeat(5) + "Tipo: " + pet.getTipo());
			System.out.print(" ".repeat(5) + "Sexo: " + pet.getSexo());
			System.out.print("\nStatus: " + pet.getSituacao());
			System.out.print(" ".repeat(5) + "Emergência: " + pet.getEmergencia());
			System.out.print(" ".repeat(5) + "Medicamentos: " + pet.getMedicamentos());
			System.out.print("\nObservações: " + pet.getObservacoes());
			System.out.print(" ".repeat(5) + "Diagnóstico: " + pet.getDiagnostico());

			System.out.println("\n\nServiços do pet:");
			pet.printServicos(serv);
			System.out.println(" ".repeat(34) + "Orçamento do pet: R$" + pet.getOrcamento());
			
			System.out.println("========================================================>");
			
		}
		
		
		System.out.println("\n" + " ".repeat(34) + "Orçamento Total: R$" + this.getOrcamentoTotal());
		System.out.print("Forma de Pagamento: " + this.getFormaPagamento());
		
		if(this.getFormaPagamento().toString() == "CREDITO") {
			System.out.print(" ".repeat(8) +"Parcelas: " + this.parcelaPagamento + "x");
		}
		System.out.println("");
		
		System.out.println("Status Pagamento: " + this.getStatusPagamento());
		System.out.println("\nSituação do Cliente: " + this.getSituacao());
		System.out.println("\n\nData de Cadastro: " + this.getDataCadastro().format(timeFormat));
		System.out.println("\n\n");
    }


}
