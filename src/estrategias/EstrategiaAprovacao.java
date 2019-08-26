package estrategias;
import java.io.Serializable;
import java.util.Collections;
import java.util.List;

import comparators.ComparatorMaiorAprovacao;
import projetos.Projeto;

/**
 * Classe que controla a estrategia de maior quantida de votacoes, que e usada em desempate, 
 * e implementa EstrategiaOrdenacao.
 * @author Edimar Junior, Gabriel Brandao, Gabriel Medeiros, Ruan Gomes.
 *
 */
public class EstrategiaAprovacao implements EstrategiaOrdenacao, Serializable{

	private static final long serialVersionUID = 1L;
	
	/**
	 * Comparator que vai ser usado no sort das propostas.
	 */
	private ComparatorMaiorAprovacao cma;

	/**
	 * Retorna o codigo do projeto mais semelhante aos interesses de uma pessoa.
	 * @param projetos Lista de projetos.
	 * @param interesses Interesses de uma pessoa.
	 * @return String que representa o codigo do projeto mais semelhante.
	 */
	@Override
	public String ordenar(List<Projeto> projetos, String interesses) {
		
		this.cma = new ComparatorMaiorAprovacao(interesses);
		
		Collections.sort(projetos, cma);
		
		return this.imprimir(projetos);
	}
}
