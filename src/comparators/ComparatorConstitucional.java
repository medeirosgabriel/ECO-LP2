package comparators;
import java.io.Serializable;
import java.util.Comparator;

import projetos.Projeto;

/**
 * Classe que sera usada como comparador de propostas, com desempate nos tipos das propostas.
 * @author Edimar Junior, Gabriel Brandao, Gabriel Medeiros, Ruan Gomes.
 *
 */
public class ComparatorConstitucional implements Comparator<Projeto>, Serializable{

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
	 * Construtor da classe ComparatorConstitucional.
	 * @param interesses Interesses da pessoa que vai ser comparada.
	 */
	public ComparatorConstitucional(String interesses) {
		this.interesses = interesses;
		this.compInt = new ComparatorInteresses(this.interesses);
	}
	
	/**
	 * Compara dois projetos, verificando os tipos das propostas.
	 * @param p1 Primeiro Projeto a ser comparado.
	 * @param p2 Segundo Projeto a ser comparado.
	 * @return valor inteiro da comparacao dos objetos.
	 */
	@Override
	public int compare(Projeto p1, Projeto p2) {
		if (this.compInt.compare(p1, p2) == 0) {
			int c1 = this.defineAuxiliar(p1);
			int c2 = this.defineAuxiliar(p2);
			if (c1 > c2){
				return 1;
			}else if (c1 < c2) {
				return -1;
			}else {
				return empate(p1, p2);	
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
	
	/**
	 * Retorna um valor inteiro de acordo com o tipo do projeto.
	 * @param p1 Projeto que sera verificado.
	 * @return Valor inteiro representando o tipo do projeto.
	 */
	public int defineAuxiliar(Projeto p1) {
		if (p1.getCodigo().substring(0, 3).equals("PEC")) {
			return 1;
		}else if(p1.getCodigo().substring(0, 3).equals("PLP")) {
			return 2;
		}else{
			return 3;
		}
	}
}
