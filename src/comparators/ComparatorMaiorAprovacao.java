package comparators;

import java.io.Serializable;
import java.util.Comparator;

import projetos.Projeto;

/**
 * Classe que sera usada como comparador de propostas, a partir da quantida de aprovacao de cada projeto.
 * @author Edimar Junior, Gabriel Brandao, Gabriel Medeiros, Ruan Gomes.
 *
 */
public class ComparatorMaiorAprovacao  implements Comparator<Projeto>, Serializable{

	private static final long serialVersionUID = 1L;
	/**
	 * Interesses da pessoa a ser comparada.
	 */
	private String interesses;
	/**
	 * Comparador de interesses.
	 */
	private ComparatorInteresses compInt;
	
	/**
	 * Construtor da classe ComparatorMaiorAprovacao.
	 * @param interesses Interesses da pessoa que vai ser comparada.
	 */
	public ComparatorMaiorAprovacao(String interesses) {
		this.interesses = interesses;
		this.compInt = new ComparatorInteresses(this.interesses);
	}
	
	/**
	 * Retorna um inteiro a partir da comparacao de duas propostas.
	 * @param p1 Primeiro Projeto a ser comparado.
	 * @param p2 Segundo Projeto a ser comparado.
	 * @return valor inteiro da comparacao dos objetos.
	 */
	@Override
	public int compare(Projeto p1, Projeto p2) {
		if (this.compInt.compare(p1, p2) == 0) {
			if (p1.retornaQuantidadeAprovacoes() > p2.retornaQuantidadeAprovacoes()) {
				return -1;
			}else if (p1.retornaQuantidadeAprovacoes() < p2.retornaQuantidadeAprovacoes()) {
				return 1;
			}else {
				return this.empate(p1, p2);
			}
		}else {
			return this.compInt.compare(p1, p2);
		}
	}

	/**
	 * Em caso de empate, esse metodo sera chamado para verificar a idade do projeto, para que haja um desempate.
	 * @param p1 Primeiro Projeto a ser comparado.
	 * @param p2 Segundo Projeto a ser comparado.
	 * @return Valor inteiro representando a comparacao dos projetos.
	 */
	public int empate(Projeto p1, Projeto p2) {
		String[] codigo1 = p1.getCodigo().split(" ");
		String[] codigo2 = p2.getCodigo().split(" ");
		int ano1 = Integer.parseInt(codigo1[1].split("/")[1]);
		int ano2 = Integer.parseInt(codigo2[1].split("/")[1]);
		if (ano1 > ano2) {
			return 1;
		}else if(ano1 < ano2) {
			return -1;
		}else {
			if (p1.getNumero() < p2.getNumero()) {
				return -1;
			}else {
				return 1;
			}
		}
	}
}
