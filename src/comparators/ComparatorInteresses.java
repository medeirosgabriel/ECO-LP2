package comparators;

import java.io.Serializable;
import java.util.Comparator;

import projetos.Projeto;

/**
 * Compara os interesses da pessoa com os interesses de uma proposta.
 * @author Edimar Junior, Gabriel Brandao, Gabriel Medeiros, Ruan Gomes.
 *
 */
public class ComparatorInteresses  implements Comparator<Projeto>, Serializable{
	
	private static final long serialVersionUID = 1L;
	
	/**
	 * String que representa os interesses da pessoa.
	 */
	private String interesses;
	
	/**
	 * Construtor da classe ComparatorInteresses.
	 * @param interesses String contendo os interesses de uma pessoa separados por virgula.
	 */
	public ComparatorInteresses(String interesses) {
		this.interesses = interesses;
	}

	/**
	 * Compara dois projetos a partir dos interesses em comum com os interesses da pessoa.
	 * @param p1 Primeiro projeto a ser comparado.
	 * @param p2 Segundo projeto a ser comparado.
	 * @return Valor inteiro da comparacao dos objetos.
	 */
	@Override
	public int compare(Projeto p1, Projeto p2) {
		if (p1.retornaQuantidadeDeInteresses(this.interesses) < p2.retornaQuantidadeDeInteresses(this.interesses)){
			return 1;
		}else if (p1.retornaQuantidadeDeInteresses(this.interesses) > p2.retornaQuantidadeDeInteresses(this.interesses)){
			return -1;
		}else {
			return 0;
		}
	}
}