package projetos;

import java.io.Serializable;

import controllers.Validacao;
import entidades.Pessoa;

/**
 * Classe filha de Projeto, permite criar um objeto do tipo Projeto de Ementa Constitucional.
 * @author Edimar Junior, Gabriel Brandao, Gabriel Medeiros, Ruan Gomes.
 *
 */
public class ProjetoEmentaConstitucional extends Projeto implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * Artigos do projeto de ementa constitucional.
	 */
	private String artigos;
	
	/**
	 * Construtor da classe filha ProjetoEmentaConstitucional.
	 * @param deputado Objeto do tipo pessoa que representa um deputado.
	 * @param ano String que contem a data de criacao do projeto.
	 * @param codigo String que contem o codigo do projeto.
	 * @param ementa String que contem a ementa do projeto.
	 * @param interessesRelacionados String que possui os interesses do projeto.
	 * @param url String que contem o endereco web referente ao projeto.
	 * @param artigos String que contem os artigos relacionados ao projeto.
	 */
	public ProjetoEmentaConstitucional(Pessoa deputado, int ano, String codigo, String ementa, String interessesRelacionados, String url, String artigos, int numero) {
		super(deputado, ano, codigo, ementa, interessesRelacionados, url, numero);
		Validacao.validarString(artigos, "Erro na criacao do projeto: artigo invalido");
		this.artigos = artigos;
	}
	
	/**
	 * Retorna uma String contendo a representacao textual do projeto de ementa constitucional.
	 * @return String contendo a representacao textual.
	 */
	@Override
	public String toString() {
		return "Projeto de Emenda Constitucional" + " - " + this.codigo + " - " +  this.deputado.getDni() + " - " + this.ementa + " - " + this.artigos.replace(",", ", ") + " - " + this.situacao;
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
		if (Math.floor(numeroDeputados/2.0 + 1) <= aprovacoes) {
			this.tramitacao.add(String.format("APROVADO (%s)", this.primeiraLetraMaiuscula(this.getLocal())));
			if (proximoLocal.equals("plenario")) {
				this.situacao = "EM VOTACAO (Plenario - 1o turno)";
			}else {
				this.situacao = String.format("EM VOTACAO (%s)", proximoLocal);
			}
			return true;
		}else {
			this.tramitacao.add(String.format("REJEITADO (%s)",this.primeiraLetraMaiuscula(this.getLocal())));
			if (proximoLocal.equals("plenario")) {
				this.situacao = "EM VOTACAO (Plenario - 1o turno)";
			}else {
				this.situacao = String.format("EM VOTACAO (%s)", proximoLocal);
			}
			return false;
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
		if ((Math.floor((3 * total)/5.0 + 1) <= aprovacoes)){
			this.tramitacao.add(String.format("APROVADO (%s)",this.primeiraLetraMaiuscula(this.getLocal())));
			if (this.situacao.equals("EM VOTACAO (Plenario - 1o turno)")) {
				this.situacao = "EM VOTACAO (Plenario - 2o turno)";
			}else {
				this.situacao = "APROVADO";
				this.deputado.fazerLei();
			}
			return true;
		}else {
			this.tramitacao.add(String.format("REJEITADO (%s)", this.primeiraLetraMaiuscula(this.getLocal())));
		}
		this.situacao = "ARQUIVADO";
		return false;
	}
	
	/** 
	 * Validar a quantidade de deputados presentes.
	 * @param total inteiro representando o total de deputados.
	 * @param presentes inteiro representando o total de deputados presentes.
	 */
	@Override
	public void validarQuorum(int total, int presentes) {
		if (presentes < Math.floor((3*total)/5.0)+ 1) {
			throw new IllegalArgumentException("Erro ao votar proposta: quorum invalido");
		}
	}
	
	/**
	 * Metodo abstrato, que retorna o local atual de votacao do projeto.
	 * @return String contendo o local atual de votacao do projeto.
	 */
	@Override
	public String getLocalVerificador() {
		if (!this.situacao.equals("EM VOTACAO (Plenario - 1o turno)") && !this.situacao.equals("EM VOTACAO (Plenario - 2o turno)")) {
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
