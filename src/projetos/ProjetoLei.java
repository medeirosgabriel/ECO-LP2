package projetos;

import java.io.Serializable;

import entidades.Pessoa;

/**
 * Classe filha de Projeto, permite criar um objeto do tipo Projeto de Lei.
 * @author Edimar Junior, Gabriel Brandao, Gabriel Medeiros, Ruan Gomes.
 *
 */
public class ProjetoLei extends Projeto implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * Valor booleano indicativo se o projeto de lei e conclusivo ou nao.
	 */
	private boolean conclusivo;
	
	/**
	 * Construtor da classe filhaProjetoLei.
	 * @param pessoa Objeto do tipo pessoa que representa um deputado.
	 * @param ano String que contem a data de criacao do projeto.
	 * @param codigo String que contem o codigo do projeto.
	 * @param ementa String que contem a ementa do projeto.
	 * @param interessesRelacionados String que possui os interesses do projeto.
	 * @param url String que contem o endereco web referente ao projeto.
	 * @param conclusivo booleano indicando se o projeto de lei e conclusivo ou nao.
	 */
	public ProjetoLei(Pessoa pessoa, int ano, String codigo, String ementa, String interessesRelacionados, String url, boolean conclusivo, int numero) {
		super(pessoa, ano, codigo, ementa, interessesRelacionados, url, numero);
		this.conclusivo = conclusivo;
	}
	
	/**
	 * Retorna uma String contendo a representacao textual do projeto de lei.
	 * @return String contendo a representacao textual.
	 */
	@Override
	public String toString() {
		if (this.conclusivo) {
			return "Projeto de Lei" + " - " + this.codigo + " - " +  this.deputado.getDni() + " - " + this.ementa + " - " + "Conclusiva"  + " - " + this.situacao;
		}else {
			return "Projeto de Lei" + " - " + this.codigo + " - " +  this.deputado.getDni() + " - " + this.ementa + " - " + this.situacao;
		}
	}
	
	/**
	 * Metodo abstrato que retorna um valor booleano representando o resultado da votacao do projeto em uma comissao.
	 * @param numeroDeputados inteiro que representa o numero de deputados presentes na votacao do projeto.
	 * @param aprovacoes inteiro que representa a quantidade de deputados que votaram no projeto.
	 * @param proximoLocal String que representa o proximo local de votacao do projeto.
	 * @return booleano representando o resultado da votacao.
	 */
	@Override
	public boolean votarComissao(int numeroDeputados, int aprovacoes, String proximoLocal) {
		this.validaTramitacao();
		if (this.conclusivo) {
			if (!proximoLocal.equals("plenario")) {
				return this.calcula(numeroDeputados, aprovacoes, proximoLocal);
			}else {
				throw new IllegalArgumentException("Erro ao votar proposta: proposta encaminhada ao plenario");
			}
		}else {
			return this.calcula(numeroDeputados, aprovacoes, proximoLocal);
		}
	}
	
	/**
	 * Metodo abstrato que retorna um valor booleano representando o resultado da votacao do projeto no plenario.
	 * @param total inteiro que representa a quantidade total de deputados.
	 * @param numeroDeputados inteiro que representa a quantidade de deputados na votacao.
	 * @param aprovacoes inteiro que representa a quantidade de deputados que votaram no projeto.
	 * @return booleano representando o resultado da votacao do projeto no plenario.
	 */
	@Override
	public boolean votarPlenario(int total, int numeroDeputados, int aprovacoes) {
		this.validaTramitacao();
		this.validarQuorum(total, numeroDeputados);
		if (this.situacao.equals("EM VOTACAO (Plenario)")) {
			if ((Math.floor(((1/2.0) * numeroDeputados) + 1) <= aprovacoes)){
				this.tramitacao.add(String.format("APROVADO (%s)", this.primeiraLetraMaiuscula(this.getLocal())));
				this.situacao = "APROVADO";
				this.deputado.fazerLei();
				return true;
			}else {
				this.tramitacao.add(String.format("REJEITADO (%s)", this.primeiraLetraMaiuscula(this.getLocal())));
				this.situacao = "ARQUIVADO";
				return false;
			}
		}else {
			throw new IllegalArgumentException("Erro ao votar proposta: tramitacao em comissao");
		}
	}
	
	/**
	 * Verifica a aprovacao e modifica a situacao do projeto.
	 * @param numeroDeputados inteiro que representa a quantidade de deputados.
	 * @param aprovacoes inteiro que representa a quantidade de deputados que votaram a favor.
	 * @param proximoLocal String que representa o proximo local de votacao.
	 * @return booleano contendo o resultado a votacao.
	 */
	public boolean calcula(int numeroDeputados, int aprovacoes, String proximoLocal) {
		if (Math.floor(numeroDeputados/2 + 1) <= aprovacoes) {
			this.tramitacao.add(String.format("APROVADO (%s)",this.primeiraLetraMaiuscula(this.getLocal())));
			if (!proximoLocal.equals("-")) {
				this.situacao = String.format("EM VOTACAO (%s)", this.primeiraLetraMaiuscula(proximoLocal));
			}else{
				this.situacao = "APROVADO";
				this.deputado.fazerLei();
			}
			return true;
		}else {
			this.tramitacao.add(String.format("REJEITADO (%s)",this.primeiraLetraMaiuscula(this.getLocal())));
			if (this.conclusivo) {
				this.situacao = "ARQUIVADO";
			}else {
				this.situacao = String.format("EM VOTACAO (%s)", this.primeiraLetraMaiuscula(proximoLocal));
			}
			return false;
		}
	}
	
	/** 
	 * Validar a quantidade de deputados presentes.
	 * @param total inteiro representando o total de deputados.
	 * @param presentes inteiro representando o total de deputados presentes.
	 */
	@Override
	public void validarQuorum(int total, int presentes) {
		if (presentes < Math.floor(total/2.0 + 1)) {
			throw new IllegalArgumentException("Erro ao votar proposta: quorum invalido");
		}
	}
	
	/**
	 * Metodo abstrato, que retorna o local atual de votacao do projeto.
	 * @return String contendo o local atual de votacao do projeto.
	 */
	@Override
	public String getLocalVerificador() {
		if (!this.situacao.equals("EM VOTACAO (Plenario)")) {
			if (!this.situacao.equals("APROVADO") && !this.situacao.equals("ARQUIVADO")) {
				return this.situacao.substring(12, this.situacao.length() - 1);
			}else {
				throw new IllegalArgumentException("Erro ao votar proposta: tramitacao encerrada");
			}
		}else {
			throw new IllegalArgumentException("Erro ao votar proposta: proposta encaminhada ao plenario");
		}
	}
}