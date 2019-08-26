package entidades;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import controllers.Validacao;

/**
 * Essa classe permite criar um objeto do tipo Comissao.
 * @author Edimar Junior, Gabriel Brandao, Gabriel Medeiros, Ruan Gomes.
 *
 */
public class Comissao implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	/**
	 * Tema da comissao.
	 */
	private String tema;
	/**
	 * Conjunto de dni's dos deputados que participam da comissao.
	 */
	private Set<String> deputados;
	
	/**
	 * Construtor da classe Comissao.
	 * @param tema String que vai representar o tema da comissao.
	 * @param politicos Array de strings contendo os dni's dos deputados que participam da comissao.
	 */
	public Comissao(String tema, String[] politicos) {
		Validacao.validarString(tema, "Erro na criacao da comissao: tema invalido");
		this.tema = tema;
		this.deputados = new HashSet<>();
		this.adicionaPoliticos(politicos);
	}

	/**
	 * Adiciona politicos no conjunto de deputados.
	 * @param politicos Array de Strings que contem os dni's dos deputados relacionados a comissao.
	 */
	private void adicionaPoliticos(String[] politicos) {
		for (String dni : politicos) {
			this.deputados.add(dni);
		}
	}
	
	/**
	 * Retorna um conjunto de Strings, contendo os dni's dos deputados relacionados a comissao.
	 * @return Conjunto de Strings dos dni's.
	 */
	public Set<String> getDeputados() {
		return this.deputados;
	}
	
	/**
	 * Retorna o tema da comissao.
	 * @return String que representa o tema da comissao.
	 */
	public String getTema() {
		return this.tema;
	}
	
	/**
	 * Gera um hashcode para o Objeto.
	 * @return inteiro representando o hashCode do objeto.
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((tema == null) ? 0 : tema.hashCode());
		return result;
	}

	/**
	 * Retorna um booleano representando o resultado de uma comparacao de igualdade entre dois objetos.
	 * @param obj Objeto que vai ser comparado com o objeto em questao.
	 * @return resultado booleano da comparacao.
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Comissao other = (Comissao) obj;
		if (tema == null) {
			if (other.tema != null)
				return false;
		} else if (!tema.equals(other.tema))
			return false;
		return true;
	}
}
