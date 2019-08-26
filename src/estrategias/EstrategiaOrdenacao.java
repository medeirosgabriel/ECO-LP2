package estrategias;

import projetos.Projeto;
import java.util.List;

/**
 * Classe mae das estrategias de ordenacao para escolha de propostas mais semelhantes.
 * @author Edimar Junior, Gabriel Brandao, Gabriel Medeiros, Ruan Gomes.
 *
 */
public interface EstrategiaOrdenacao {
	/**
	 * Ordena os projetos de acordo com a estrategia de desempate. 
	 * @param projetos Lista de Projetos que vao ser verificados.
	 * @param interesses String que contem os interesses de uma pessoa.
	 * @return String que contem o codigo do projeto mais relacionado.
	 */
	public String ordenar(List<Projeto> projetos, String interesses);
	
	/**
	 * Retorna o codigo da proposta mais relacionada.
	 * @param projetos Lista de projetos ordenada de acordo com a estrategia escolhida.
	 * @return Strign que representa o codigo da proposta mais relacionada.
	 */
	public default String imprimir(List<Projeto> projetos) {
		if (projetos.size() > 0) {
			return projetos.get(0).getCodigo();
		}
		return "";
	}
}
