package controllers;


import java.util.HashMap;

import entidades.Deputado;
import entidades.Pessoa;
import estrategias.EstrategiaAprovacao;
import estrategias.EstrategiaConclusao;
import estrategias.EstrategiaConstitucional;

/**
 * Classe que permite criar objetos do tipo ControllerPessoa. Utilizado para gerencias pessoas.
 * @author Edimar Junior, Gabriel Brandao, Gabriel Medeiros, Ruan Gomes.
 *
 */
public class ControllerPessoa {
	
	/**
	 * Mapa de chave dni e pessoas como valores.
	 */
	private HashMap<String, Pessoa> pessoas;
	
	/**
	 * Construtor da classe ControllerPessoa.
	 */
	public ControllerPessoa() {
		this.pessoas = new HashMap<>();
	}
	/**
	 * Cadastra uma pessoa sem partido no sistema ECO.
	 * @param nome String que representara o nome da pessoa.
	 * @param dni String que representara o documento de identificacao da pessoa.
	 * @param estado String que representara a sigla do estado da pessoa.
	 * @param interesses String que representara os interesses da pessoa.
	 */
	public void cadastrarPessoa(String nome, String dni, String estado, String interesses) {
		if(this.verificaExistenciaPessoa(dni)) {
			throw new IllegalArgumentException("Erro ao cadastrar pessoa: dni ja cadastrado");
		}
		pessoas.put(dni, new Pessoa(nome, dni, estado, interesses));
	}
	
	/**
	 * Cadastra uma pessoa que possui partido no sistema ECO.
	 * @param nome String que representara o nome da pessoa.
	 * @param dni  String que representara o documento de identificacao da pessoa.
	 * @param estado String que representara a sigla do estado da pessoa.
	 * @param interesses String que representara os interesses da pessoa.
	 * @param partido String que representara os interesses da pessoa.
	 */
	public void cadastrarPessoa(String nome, String dni, String estado, String interesses, String partido) {
		if(this.verificaExistenciaPessoa(dni)) {
			throw new IllegalArgumentException("Erro ao cadastrar pessoa: dni ja cadastrado");
		}
		this.pessoas.put(dni, new Pessoa(nome, dni, estado, interesses, partido));
	}
	
	/**
	 * Retorna a representacao textual de uma pessoa.
	 * @param dni String que representa o dni da pessoa a ser pesquisada.
	 * @return String que contem a representacao textual da pessoa.
	 */
	public String exibirPessoa(String dni) {
		if (this.pessoas.containsKey(dni)) {
			return this.pessoas.get(dni).representacao();
		}else {
			throw new IllegalArgumentException("Erro ao exibir pessoa: pessoa nao encontrada");
		}
	}
	
	/**
	 * Cadastra um deputado. Isso se a pessoa estiver cadastrada no sistema.
	 * @param dni String que representa o dni da pessoa a ser cadastrada
	 * como deputado.
	 * @param dataDeInicio String que representa a data em que a pessoa
	 * passou a ser deputado.
	 */
	public void cadastrarDeputado(String dni, String dataDeInicio) {
		this.verificaPartidoPessoa(dni, "Erro ao cadastrar deputado: pessoa sem partido");
		this.pessoas.get(dni).setFuncao(new Deputado(dataDeInicio));
	}
	
	// METODOS AUXILIARES
	
	/**
	 * Verifica o partido da pessoa a partir do dni da pessoa. Metodo usado para tratamento de erro.
	 * @param dni String que representa o dni da pessoa.
	 * @param mensagem String que contem uma mensagem de erro.
	 */
	public void verificaPartidoPessoa(String dni, String mensagem) {
		if (this.pessoas.get(dni).getPartido() == null) {
			throw new IllegalArgumentException(mensagem);
		}
	}
	
	/**
	 * Verifica se uma pessoa existe.
	 * @param dni String que representa o dni associado a pessoa.
	 * @return booleano do resultado da busca da pessoa.
	 */
	public boolean verificaExistenciaPessoa(String dni) {
		if (this.pessoas.containsKey(dni)) {
			return true;
		}
		return false;
	}
	
	/**
	 * Retorna um Objeto Pessoa a partir de uma pesquisa por dni.
	 * @param dni String que representa o dni associado a pessoa.
	 * @return Objeto Pessoa a ser retornado.
	 */
	public Pessoa retornaPessoa(String dni) {
		return this.pessoas.get(dni);
	}
	
	/**
	 * Valida erros relacionados a um politico.
	 * @param politicos String que contem os dni's dos deputados.
	 * @param mensagem1 Mensagem que pode ser lancada como excecao.
	 * @param mensagem2 Mensagem que pode ser lancada como excecao.
	 * @param mensagem3 Mensagem que pode ser lancada como excecao.
	 */
	public void validarPoliticos(String politicos, String mensagem1, String mensagem2, String mensagem3) {
		String[] arrayDeputados = politicos.split(",");
		for (String dni : arrayDeputados) {
			Validacao.validarDni(dni, mensagem1);
			if (!this.verificaExistenciaPessoa(dni)) {
				throw new IllegalArgumentException(mensagem2);
			}
			this.verificaPartidoPessoa(dni, mensagem3);
		}
	}
	
	/**
	 * Chama o metodo fazer lei de um deputado.
	 * @param dni String que representa o dni do deputado em questao.
	 */
	public void fazerLei(String dni) {
		this.pessoas.get(dni).fazerLei();
	}
	
	/**
	 * Configura uma estrategia de desempate para uma certa pessoa. 
	 * @param dni String que representa o dni da pessoa que vai ter a estrategia de desempate definida.
	 * @param estrategia Nova estrategia de desempate.
	 */
	public void configurarEstrategiaPropostaRelacionada(String dni, String estrategia) {
		if (this.pessoas.containsKey(dni)) {
			if (estrategia.equals("CONSTITUCIONAL")) {
				this.pessoas.get(dni).setEstrategia(new EstrategiaConstitucional());
			}else if (estrategia.equals("CONCLUSAO")) {
				this.pessoas.get(dni).setEstrategia(new EstrategiaConclusao());
			}else {
				this.pessoas.get(dni).setEstrategia(new EstrategiaAprovacao());
			}
		}else {
			throw new IllegalArgumentException("Pessoa nao inexistente");
		}
	}
	
	/**
	 * Retorna o mapa de pessoas.
	 * @return Mapa de pessoas.
	 */
	public HashMap<String, Pessoa> getPessoas() {
		return pessoas;
	}
	
	/**
	 * Atribui um novo valor ao mapa de pessoas.
	 * @param pessoas Mapa de pessoas que e passado como parametro.
	 */
	public void setMapaPessoas(HashMap<String, Pessoa> pessoas) {
		this.pessoas = pessoas;
		
	}
}