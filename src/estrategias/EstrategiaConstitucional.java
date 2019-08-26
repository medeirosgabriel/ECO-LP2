package estrategias;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;

import comparators.ComparatorConstitucional;
import projetos.Projeto;

/**
 * Classe que controla a escolha do projeto a partir do tipo de projeto, implementa EstrategiaOrdenacao.
 * @author Edimar Junior, Gabriel Brandao, Gabriel Medeiros, Ruan Gomes.
 *
 */
public class EstrategiaConstitucional implements EstrategiaOrdenacao, Serializable{

	private static final long serialVersionUID = 1L;
	
	/**
	 * Comparator que vai ser usado no sort das propostas.
	 */
	private ComparatorConstitucional cc;
	
	/**
	 * Retorna o codigo da proposta mais relacionada, com desempate relacionado ao tipo da proposta..
	 * @param projetos Lista de projetos.
	 * @param interesses String que contem os interesses de uma pessoa.
	 * @return String que contem o codigo da proposta mais relacionada.
	 */
	@Override
	public String ordenar(List<Projeto> projetos, String interesses) {
		
		this.cc = new ComparatorConstitucional(interesses);
		
		Collections.sort(projetos, cc);
		
		return this.imprimir(projetos);
	}
}
