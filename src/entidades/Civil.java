package entidades;

import java.io.Serializable;

/**
 * Essa classe permite criar um objeto do tipo Civil, que possui Pessoa como classe pai.
 * @author Edimar Junior, Gabriel Brandao, Gabriel Medeiros, Ruan Gomes.
 *
 */
public class Civil implements Funcao , Serializable{
	
	
	private static final long serialVersionUID = 1L;

	/**
	 * Retorna uma String contendo a representacao de uma pessoa civil.
	 * @param nome String que representa o nome de uma pessoa civil.
	 * @param dni String que representa o documento de identificacao de uma pessoa civil.
	 * @param estado String que representa a sigla do estado de uma pessoa civil.
	 * @param interesses String que representa os interesses de uma pessoa civil.
	 * @param partido String que representa a sigla do partido de uma pessoa civil.
	 * @return String contendo a representacao de uma pessoa civil.
	 */
	@Override
	public String representacao(String nome, String dni, String estado, String interesses, String partido) {
		if (partido == null && interesses.equals("")) {
			return nome + " - " + dni + " (" + estado + ")";
		}else if (partido == null && !interesses.equals("")){
			return nome + " - " + dni + " (" + estado + ")" + " - " +  "Interesses: " + interesses;
		}else if (partido != null && interesses.equals("")){
			return nome + " - " + dni + " (" + estado + ")" + " - " + partido;
		}else {
			return nome + " - " + dni + " (" + estado + ")" + " - " + partido + " - " + "Interesses: " + interesses;
		}
	}
}