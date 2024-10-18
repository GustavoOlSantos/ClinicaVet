package sistem.entities;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

import sistem.enums.FormaPagamento;
import sistem.enums.Situacao;
import sistem.enums.StatusPagamento;
import sistem.exceptions.DomainException;
import sistem.services.CpfCnpjMask;
import sistem.services.TelefoneMask;


public class Cliente implements Serializable {
	
	private static final long serialVersionUID = 1L;

	private int id;
	
	private String nome;
	private String cpf;
	private String telefone;
	
	//public int[] servicos = new int[8];
	private int NAO_INICIALIZADO;
	
	private double orcamentoTotal;
	
	private FormaPagamento formaPagamento; 
	
	public int parcelaPagamento;
	private final int LIMITE_PARCELA = 6;
	
	private StatusPagamento status;
	
	private Situacao trabalho;
	
	private LocalDateTime dataCadastro;
	
	public Animal[] animal;
	public int qtdAnimal;
	
	
	//=> Constrututor 
	public Cliente() {
		//this.id = UUID.randomUUID().hashCode();		   //=> Gera um ID para o cliente
		//this.NAO_INICIALIZADO = -1;

	
		
		//this.trabalho = Situacao.TRABALHANDO; 		 //=> Define a situação do cliente como trabalhando
		//this.status = StatusPagamento.PENDENTE;		//=> Deixa o pagamento como pendente
		//this.dataCadastro = LocalDateTime.now();
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
		
		animal = new Animal[qtd];
		
		//this.id = UUID.randomUUID().hashCode();		   //=> Gera um ID para o cliente
		this.NAO_INICIALIZADO = -1;
		
		this.trabalho = Situacao.TRABALHANDO; 		 	 //=> Define a situação do cliente como trabalhando
		this.status = StatusPagamento.PENDENTE;		    //=> Deixa o pagamento como pendente
		this.dataCadastro = LocalDateTime.now();
	}
	
	//===> MÉTODOS
	
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
	
	//======> Orçamento
	public double getOrcamento(){
		return this.orcamentoTotal;
	}

	public void setOrcamento(double value){
		orcamentoTotal += value;
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
			case 1: 
				this.formaPagamento = FormaPagamento.CREDITO; 
				/*int parcela;
				
				System.out.print("\n\nSerá parcelado em quantas vezes?(Limite "+ LIMITE_PARCELA  +")\nR:");
				parcela = in.nextInt();
				
				if(parcela <= 0 || parcela > LIMITE_PARCELA) {
					throw new DomainException("Quantidade Inválida de Parcelas.");
				}
				
				this.parcelaPagamento = parcela; 
				in.nextLine();*/
			break;
			case 2: this.formaPagamento = FormaPagamento.DINHEIRO; break;
			case 3: this.formaPagamento = FormaPagamento.PIX; break;
			default: throw new DomainException("Forma de Pagamento Inválida");
    	}
        
        //=> Define o Valor da Parcela como 1
        if(formaPg != 1) {
        	this.parcelaPagamento = 1;
        }
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
    
    ///======> Finalizado
    public Situacao getSituacao() {
        return trabalho;
    }
    
    public int getIntSituacao() {
    	int stt = -1;
    	
        switch(trabalho) {
	        case TRABALHANDO: stt = 0; break;
			case FINALIZADO: stt = 1; break;
			case CANCELADO: stt = 2; break;
			case EXCLUÍDO: stt = 3; break;
        }
        
        return stt;
    }
    
    public void setSituacao(int stts) {
    	switch(stts) {
		    case 0: this.trabalho = Situacao.TRABALHANDO; break;
			case 1: this.trabalho = Situacao.FINALIZADO; break;
			case 2: this.trabalho = Situacao.CANCELADO; break;
			case 3: this.trabalho = Situacao.EXCLUÍDO; break;
    	}
    }	
    
	public void setDataCadastro(LocalDateTime date) {
		this.dataCadastro = date;
		
	}
    
    public LocalDateTime getDataCadastro() {
    	return dataCadastro;
    }
    
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
    
    public String toString() {
    	
    	String objStr;
    	
    	objStr = "Nome: " + nome;
    	
    	//=> Printa o Nome dos Animais
		for(int j = 0; j < qtdAnimal; j++) {
			
			objStr += " - pet: " + animal[j].getNome() + " ("+ animal[j].getTipo() +")";
		}
    	
    	return objStr;
    }
    
    public void printClienteSheet(Servicos[] serv, DateTimeFormatter timeFormat) {
    	//System.out.println("id: " + this.getId());
		System.out.println("Nome: " + this.getNome());
		System.out.print("CPF: " + this.getCpf());
		System.out.println(" ".repeat(5) + "Telefone: " + this.getTelefone());
		
		System.out.println("\n===========> Pets Cadastrados");
		
		for(Animal pet : this.animal) {
			System.out.print("\nNome do pet: " + pet.getNome());
			System.out.print(" ".repeat(5) + "Tipo: " + pet.getTipo());
			
			
			System.out.println("\n\nServiços do pet:");
			pet.printServicos(serv);
			System.out.println(" ".repeat(34) + "Orçamento do pet: R$" + pet.getOrcamento());
			
			System.out.println("========================================================>");
			
		}
		
		
		System.out.println("\n" + " ".repeat(34) + "Orçamento Total: R$" + this.getOrcamento());
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
