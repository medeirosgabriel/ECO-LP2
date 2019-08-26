package controllers;

import java.util.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 * Essa classe permite criar um objeto do tipo Validacao. Auxilia o tratamento de erro do sistema.
 * @author Edimar Junior, Gabriel Brandao, Gabriel Medeiros, Ruan Gomes.
 *
 */
public class Validacao {
	
	/**
	 * Valida uma string e lanca um erro se necessario.
	 * @param palavra String que representa a String que vai ser verificada.
	 * @param mensagem String que contem a mensagem de erro que pode ser exibida.
	 */
	public static void validarString(String palavra, String mensagem) {
		if(palavra == null) {
			throw new NullPointerException(mensagem);
		}else if (palavra.trim().equals("")) {
			throw new IllegalArgumentException(mensagem);
		}
	}
	
	/**
	 * Valida a String dni, a partir da formatacao requerida. Lanca um erro se necessario.
	 * @param dni String que representa o dni a ser verificado.
	 * @param mensagem String que contem a mensagem de erro que pode ser exibida.
	 */
	public static void validarDni(String dni, String mensagem) {
		if (!dni.matches("\\d{9}-\\d")) {
			throw new IllegalArgumentException(mensagem);
		}
	}
	
	/**
	 * Valida uma data, seguindo a formatacao requerida e das datas que devem ser avaliadas ou nao.
	 * @param data String que representa uma data a ser verificada.
	 * @param mensagem1 String que contem uma mensagem de erro que pode ser exibida.
	 * @param mensagem2 String que contem uma mensagem de erro que pode ser exibida.
	 */
	public static void validarData(String data, String mensagem1, String mensagem2) {
		Date dataAtual = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("ddMMyyyy");
		sdf.setLenient(false);
		try {
			if (sdf.parse(data).compareTo(dataAtual) > 0) {
				throw new IllegalArgumentException(mensagem1);
			}
		}catch(ParseException pe) {
			throw new IllegalArgumentException(mensagem2);
		}
	}
	
	/**
	 * Valida data de um projeto de acordo com os requerimentos do sistema.
	 * @param ano inteiro que representa o ano do projeto.
	 */
	public static void validarDataProjeto(int ano) {
		if (ano < 1988) {
			throw new IllegalArgumentException("Erro ao cadastrar projeto: ano anterior a 1988");
		}else if (ano > 2019){
			throw new IllegalArgumentException("Erro ao cadastrar projeto: ano posterior ao ano atual");
		}
	}
}