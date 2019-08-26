package comparators;

import java.io.Serializable;
import java.util.Comparator;

import projetos.Projeto;

/**
 * Classe que sera usada como comparador de propostas, com desempate na que estiver mais proxima da conclusao.
 * @author Edimar Junior, Gabriel Brandao, Gabriel Medeiros, Ruan Gomes.
 *
 */
public class ComparatorConclusao  implements Comparator<Projeto>, Serializable{

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
	 * Construtor da classe
	 * @param interesses Interesses da pessoa que vai ser comparada.
	 */
	public ComparatorConclusao(String interesses) {
		this.interesses = interesses;
		this.compInt = new ComparatorInteresses(this.interesses);
	}
	
	/**
	 * Compara dois projetos, verificando qual esta mais proximo da conclusao.
	 * @param p1 Primeiro Projeto a ser comparado.
	 * @param p2 Segundo Projeto a ser comparado.
	 * @return valor inteiro da comparacao dos objetos.
	 */
	@Override
	public int compare(Projeto p1, Projeto p2) {
		if (this.compInt.compare(p1, p2) == 0) {
			if (this.verificaPlenario(p1.getSituacao()) && this.verificaPlenario(p2.getSituacao())) {
				return -1 * (p1.getSituacao().compareTo(p2.getSituacao()));
			}
			else {
				if (this.verificaPlenario(p1.getSituacao())) {
					return -1;
				}else if (this.verificaPlenario(p2.getSituacao())) {
					return 1;
				}else {
					if (p1.retornaQuantidadeDeComissoes() > p2.retornaQuantidadeDeComissoes()) {
						return -1;
					}else if (p1.retornaQuantidadeDeComissoes() < p2.retornaQuantidadeDeComissoes()) {
						return 1;
					}else{
						return this.empate(p1, p2);
					}
				}
			}
		}else {
			return this.compInt.compare(p1, p2);
		}
	}
	/**
	 * Verifica se o local da situacao do projeto é o plenario.
	 * @param situacao String que representa a situacao da proposta.
	 * @return Valor booleano representando se esta no plenario ou nao.
	 */
	private boolean verificaPlenario(String situacao) {
		if (situacao.equals("EM VOTACAO (Plenario - 1o turno)") || 
			situacao.equals("EM VOTACAO (Plenario - 2o turno)") || 
			situacao.equals("EM VOTACAO (Plenario)")) {
			return true;
		}
		return false;
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
