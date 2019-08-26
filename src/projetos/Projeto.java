package projetos;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.stream.Collectors;

import controllers.Validacao;
import entidades.Pessoa;

/**
 * Classe mae dos projetos PEC, PL e PLP, usada para re-uso de codigo.
 * @author Edimar Junior, Gabriel Brandao, Gabriel Medeiros, Ruan Gomes.
 *
 */
public abstract class Projeto implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	protected int numero;
	
	/**
	 * Deputado criacao do projeto.
	 */
	protected Pessoa deputado;
	/**
	 * Ano da criacao do projeto.
	 */
	protected int ano;
	/**
	 * Codigo do projeto.
	 */
	protected String codigo;
	/**
	 * Ementa do projeto.
	 */
	protected String ementa;
	/**
	 * Interesses que o projeto visa conquistar.
	 */
	protected String interessesRelacionados;
	/**
	 * Situacao atual do projeto.
	 */
	protected String situacao;
	/**
	 * Endereco web do projeto.
	 */
	protected String url;
	/**
	 * Tramitacoes do projeto(estados).
	 */
	protected ArrayList<String> tramitacao;
	
	/**
	 * Construtor da classe mae Projeto.
	 * @param deputado Objeto do tipo pessoa que representa um deputado.
	 * @param ano String que contem a data de criacao do projeto.
	 * @param codigo String que contem o codigo do projeto.
	 * @param ementa String que contem a ementa do projeto.
	 * @param interessesRelacionados String que possui os interesses do projeto.
	 * @param url String que contem o endereco web referente ao projeto.
	 */
	public Projeto(Pessoa deputado, int ano, String codigo, String ementa, String interessesRelacionados, String url, int numero) {
		Validacao.validarString(codigo, "Erro na criacao do projeto: codigo invalido");
		Validacao.validarString(ementa, "Erro na criacao do projeto: ementa invalida");
		Validacao.validarString(interessesRelacionados, "Erro na criacao do projeto: interesse invalido");
		Validacao.validarString(url, "Erro na criacao do projeto: url invalido");
		this.deputado = deputado;
		this.ano = ano;
		this.codigo = codigo;
		this.ementa = ementa;
		this.interessesRelacionados = interessesRelacionados;
		this.url = url;
		this.situacao = "EM VOTACAO (CCJC)";
		this.tramitacao = new ArrayList<>();
		this.numero = numero;
	}
	
	public int getNumero() {
		return numero;
	}

	/**
	 * Retorna os interesses do projeto.
	 * @return String contendo os interesses.
	 */
	public String getInteressesRelacionados() {
		return interessesRelacionados;
	}
	
	/**
	 * Retorna a situacao atual do projeto.
	 * @return String contendo a situacao atual do projeto.
	 */
	public String getSituacao() {
		return situacao;
	}
	
	/**
	 * Metodo abstrato, que retorna o local atual de votacao do projeto.
	 * @return String contendo o local atual de votacao do projeto.
	 */
	public abstract String getLocalVerificador();
	
	/**
	 * Metodo abstrato que retorna um valor booleano representando o resultado da votacao do projeto em uma comissao.
	 * @param numeroDeputados inteiro que representa o numero de deputados presentes na votacao do projeto.
	 * @param aprovacoes inteiro que representa a quantidade de deputados que votaram no projeto.
	 * @param proximoLocal String que representa o proximo local de votacao do projeto.
	 * @return booleano representando o resultado da votacao.
	 */
	public abstract boolean votarComissao(int numeroDeputados, int aprovacoes, String proximoLocal);
	
	/**
	 * Metodo abstrato que retorna um valor booleano representando o resultado da votacao do projeto no plenario.
	 * @param numeroDeputados inteiro que representa a quantidade de deputados na votacao.
	 * @param aprovacoes inteiro que representa a quantidade de deputados que votaram no projeto.
	 * @return booleano representando o resultado da votacao do projeto no plenario.
	 */
	public abstract boolean votarPlenario(int total, int numeroDeputados, int aprovacoes);
	
	/**
	 * Retorna uma String contendo as tramitacoes do projeto.
	 * @return String contendo as tramitacoes.
	 */
	public String exibirTramitacao() {
		if (!"APROVADOARQUIVADO".contains(this.situacao)) {
			if (this.tramitacao.stream().collect(Collectors.joining(", ")).equals("")){
				return this.situacao.replace("p", "P");
			}else{
				return this.tramitacao.stream().collect(Collectors.joining(", ")) + ", " + this.situacao.replace("p", "P");
			}
		}else{
			return this.tramitacao.stream().collect(Collectors.joining(", "));
		}
	}
	
	/**
	 * Valida uma tramitacao de um projeto.
	 */
	public void validaTramitacao() {
		if (this.situacao.equals("ARQUIVADO") || this.situacao.equals("APROVADO")) {
			throw new IllegalArgumentException("Erro ao votar proposta: tramitacao encerrada");
		}
	}
	
	/**
	 * Retorna o dni do deputado responsavel por criar o projeto. 
	 * @return String que representa o dni do deputado.
	 */
	public String getDni() {
		return this.deputado.getDni();
	}
	
	/** 
	 * Validar a quantidade de deputados presentes.
	 * @param total inteiro representando o total de deputados.
	 * @param presentes inteiro representando o total de deputados presentes.
	 */
	public abstract void validarQuorum(int total, int presentes);
	
	/**
	 * Gera um hashcode para o Objeto.
	 * @return inteiro representando o hashCode do objeto.
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((codigo == null) ? 0 : codigo.hashCode());
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
		Projeto other = (Projeto) obj;
		if (codigo == null) {
			if (other.codigo != null)
				return false;
		} else if (!codigo.equals(other.codigo))
			return false;
		return true;
	}
	
	/**
	 * Retorna a quantida de de interesses que um projeto possui em comum com os interesses de uma pessoa.
	 * @param interessesPessoa String que contem os interesses de uma pessoa.
	 * @return valor inteiro representando a quantidade de interesses em comum.
	 */
	public int retornaQuantidadeDeInteresses(String interessesPessoa) {
		int quant = 0;
		String[] interesses = interessesPessoa.split(",");
		for (String inter : interesses) {
			if (this.interessesRelacionados.contains(inter)) {
				quant ++;
			}
		}
		return quant;
	}
	
	/**
	 * Retorna a quantidade de tramitacoes de um projeto.
	 * @return valor inteiro representando a quantidade de tramitacoes do projeto.
	 */
	public int retornaQuantidadeDeComissoes() {
		return this.tramitacao.size();
	}
	
	/**
	 * Retorna a quantidade de aprovacoes de um projeto.
	 * @return valor inteiro representando a quantida de de aprovacoes do projeto.
	 */
	public int retornaQuantidadeAprovacoes() {
		int quant = 0;
		for (String e : this.tramitacao) {
			if (e.substring(0, 8).equals("APROVADO")) {
				quant ++;
			}
		}
		return quant;
	}
	
	/**
	 * Retorna o nome de um local com a primeira letra em maiusculo.
	 * @param local String que representa o nome do local.
	 * @return String modificada.
	 */
	protected String primeiraLetraMaiuscula(String local) {
		return Character.toUpperCase(local.charAt(0)) + local.substring(1);
	}
	
	/**
	 * Retorna o local de votacao atual do projeto.
	 * @return String que contem o local de votacao atual do projeto.
	 */
	protected String getLocal() {
		return this.situacao.substring(12, this.situacao.length() - 1);
	}
	
	/**
	 * Retorna o codigo do projeto.
	 * @return String que representa o codigo do projeto.
	 */
	public String getCodigo() {
		return codigo;
	}
}
