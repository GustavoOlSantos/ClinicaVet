package sistem.services;

public class CpfCnpjMask {
	private static final String REGEX_CPF = "([\\d]{3})([\\d]{3})([\\d]{3})([\\d]{2})";
	private static final String REPLACE_CPF = "$1\\.$2\\.$3-$4";
	public static final int LENGTH_CPF = 11;

	private static final String REGEX_CNPJ = "([\\d]{2})([\\d]{3})([\\d]{3})([\\d]{4})([\\d]{2})";
	private static final String REPLACE_CNPJ = "$1\\.$2\\.$3/$4-$5";
	public static final int LENGTH_CNPJ = 14;

	public static String Mask(String cpfCnpj) {

		if (cpfCnpj.length() == 0) {
			return "";
		}
		
		if (isCnpj(cpfCnpj)) {
			return cpfCnpj.replaceAll(REGEX_CNPJ, REPLACE_CNPJ);
		} 
		else if (isCpf(cpfCnpj)) {
			return cpfCnpj.replaceAll(REGEX_CPF, REPLACE_CPF);
		} 
		else if (isCpf(cpfCnpj.substring(3))) {
			return cpfCnpj.substring(3).replaceAll(REGEX_CPF, REPLACE_CPF);
		} 
		else {
			return cpfCnpj;
		}
	}

	public static String Unmask(String cpfCnpj) {
		return cpfCnpj.replace(".", "").replace("\\/", "").replace("-", "");
	}

	public static boolean isCnpj(String cpfCnpj) {
		return cpfCnpj.length() == LENGTH_CNPJ;
	}

	public static boolean isCpf(String cpfCnpj) {
		return cpfCnpj.length() == LENGTH_CPF;
	}

}
