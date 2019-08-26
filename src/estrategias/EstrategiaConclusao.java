package estrategias;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;

import comparators.ComparatorConclusao;
import projetos.Projeto;

/**
 * Classe que controla a escolha do projeto mais proximo da conclusao, implementa EstrategiaOrdenacao.
 * @author Edimar Junior, Gabriel Brandao, Gabriel Medeiros, Ruan Gomes.
 *
 */
public class EstrategiaConclusao implements EstrategiaOrdenacao, Serializable{
	
	private static final long serialVersionUID = 1L;
	
	/**
	 * Comparator que vai ser usado no sort das propostas.
	 */
	private ComparatorConclusao cc;

	/**
	 * Retorna o codigo da proposta mais relacionada, com desempate relacionado a proposta com mais proximidade
	 * da conclusao.
	 * @param projetos Lista de projetos.
	 * @param interesses String que contem os interesses de uma pessoa.
	 * @return String que contem o codigo da proposta mais relacionada.
	 */
	@Override
	public String ordenar(List<Projeto> projetos, String interesses) {
		
		this.cc = new ComparatorConclusao(interesses);
		
		Collections.sort(projetos, cc);
		
		return this.imprimir(projetos);
	}

}
