package controllers;

import java.util.HashMap;

import entidades.Comissao;


/**
 * Essa classe permite criar um objeto do tipo ControllerComissao, que auxilia o gerenciamento do sistema ECO.
 * @author Edimar Junior, Gabriel Brandao, Gabriel Medeiros, Ruan Gomes.
 *
 */
public class ControllerComissao {
	/**
	 * Mapa do tema e de um Objeto Comissao.
	 */
	private HashMap<String, Comissao> comissoes;
	
	/**
	 * Construtor da classe ControllerComissao.
	 */
	public ControllerComissao() {
		this.comissoes = new HashMap<>();
	}

	/**
	 * Cadastra uma comissao no sistema a partir do tema da comissao e dos politicos.
	 * @param tema String que representa o tema da comissao.
	 * @param politicos Array de Strings que contem os dni's dos politicos.
	 */
	public void cadastrarComissao(String tema, String[] politicos) {
		this.existeComissao(tema, "essa comissao ja existe");
		this.comissoes.put(tema, new Comissao(tema, politicos));
	}
	
	/**
	 * Verifica se a comissao existe e lanca uma excecao se necessario.
	 * @param tema String que representa o tema da comissao.
	 * @param mensagem String que representa a mensagem que deve ser exibida.
	 */
	public void existeComissao(String tema, String mensagem) {
		if (this.comissoes.containsKey(tema)) {
			throw new IllegalArgumentException(mensagem);
		}
	}
	
	/**
	 * Retorna o mapa de comissoes do objeto.
	 * @return Mapa de comissoes que vai ser retornado.
	 */
	public HashMap<String, Comissao> getComissoes() {
		return this.comissoes;
	}
	
	/**
	 * Verifica se ha uma comissao relacionada ao tema que e passado.
	 * @param tema String que representa o tema da possivel comissao.
	 * @return valor booleano do resultado da pesquisa.
	 */
	public boolean existeComissao(String tema) {
		return this.comissoes.containsKey(tema);
	}
	
	/**
	 * Atribui um novo valor ao mapa de comissoes.
	 * @param comissoes Novo mapa de comissoes.
	 */
	public void setMapaComissoes(HashMap<String, Comissao> comissoes) {
		this.comissoes = comissoes;
		
	}
}