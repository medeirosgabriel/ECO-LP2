package entidades;

import java.io.Serializable;

import controllers.Validacao;

/**
 * Essa classe permite criar um objeto do tipo Deputado, que possui Pessoa como classe pai.
 * @author Edimar Junior, Gabriel Brandao, Gabriel Medeiros, Ruan Gomes.
 *
 */
public class Deputado implements Funcao, Serializable{
	
	private static final long serialVersionUID = 1L;

	/**
	 * Quantidade de leis aprovadas que o deputado possui.
	 */
	private int quantidadeLeis;
	
	/**
	 * Data de iniciacao do deputado.
	 */
	private String dataInicio;

	/**
	 * Construtor da classe filha Deputado.
	 * @param dataInicio String que sera passada como parametro, que representa a data de iniciacao do deputado.
	 */
	public Deputado(String dataInicio) {
		Validacao.validarString(dataInicio, "Erro na criacao do Deputado");
		this.quantidadeLeis = 0;
		this.dataInicio = dataInicio;
	}
	
	/**
	 * Formata a data de iniciacao para uma forma aceitavel.
	 * @return String com a data ja modificada.
	 */
	private String formatarData() {
		return dataInicio.substring(0, 2) + "/" + dataInicio.substring(2, 4) + "/" + dataInicio.substring(4, 8);
	}
	
	/**
	 * Exibe uma representacao textual de um objeto Pessoa Deputado.
	 * @param nome String que representa o nome da Pessoa Deputado.
	 * @param dni String que representa o numero do documento de identificacao da Pessoa Deputado.
	 * @param estado String que representa a sigla do estado da Pessoa Deputado.
	 * @param interesses String que representa os interesses da Pessoa Deputado.
	 * @param partido Strign que representa o partido da Pessoa Deputado.
	 * @return String contendo a representacao textual de um objeto do tipo Deputado.
	 */
	@Override
	public String representacao(String nome, String dni, String estado, String interesses, String partido) {
		if (interesses.equals("")) {
			return "POL: " + nome + " - " + dni + " (" + estado + ")" + " - " + partido + " - " + this.formatarData() + 
					" - " + this.quantidadeLeis + " Leis";
		}else {
			return "POL: " + nome + " - " + dni + " (" + estado + ")" + " - " + partido + " - " + "Interesses: " + interesses  + " - " + this.formatarData() + 
					" - " + this.quantidadeLeis + " Leis";
		}
	}
	
	/**
	 * Incrementa em um a quantidade de leis de um deputado.
	 */
	public void fazerLei() {
		this.quantidadeLeis++;
	}
}