package sistem.exceptions;

public class DomainException extends Exception{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	//=> Passa a mensagem de Erro para a SuperClasse
	public DomainException(String msg) {
		super(msg);
	}

}
