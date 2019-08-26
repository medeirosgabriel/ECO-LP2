package entidades;

/**
 * Interface que define a funcao da pessoa(Deputado ou civil).
 * @author Edimar Junior, Gabriel Brandao, Gabriel Medeiros, Ruan Gomes.
 *
 */
public interface Funcao {
	
	/**
	 * Metodo que deve ser sobrescrito nas classes relacionadas.
	 * @param nome String que representa o nome da pessoa.
	 * @param dni String que representa o dni da pessoa. 
	 * @param estado String que representa a sigla do estado da pessoa.
	 * @param interesses String que representa os interesses da pessoa.
	 * @param partido String que representa a sigla do partido o qual a pessoa e filiada.
	 * @return String que contem a representacao textual da pessoa, dependendo da sua funcao.
	 */
	public String representacao(String nome, String dni, String estado, String interesses, String partido);
	
}
