package controllers;


import java.util.HashMap;
import java.util.Set;
import entidades.Pessoa;
import entidades.Comissao;
import projetos.Projeto;
import projetos.ProjetoLei;

/**
 * Essa classe permite criar um objeto do tipo ControllerVotacao, que auxilia o sistema ECO.
 * @author Edimar Junior, Gabriel Brandao, Gabriel Medeiros, Ruan Gomes.
 *
 */
public class ControllerVotacao {
	
	/**
	 * Mapa de deputados.
	 */
	private HashMap<String, Pessoa> deputados;
	/**
	 * Mapa de comissoes.
	 */
	private HashMap<String, Comissao> comissoes;
	/**
	 * Conjunto dos partidos da base governista.
	 */
	private Set<String> baseGoverno;
	/**
	 * Mapa dos projetos cadastrados.
	 */
	private HashMap<String, Projeto> projetos;
	
	/**
	 * Construtor da classe ControllerVotacao.
	 * @param comissoes Mapa de comissoes.
	 * @param baseGoverno Conjunto dos partidos da base governista.
	 * @param projetos Mapa de projetos.
	 */
	public ControllerVotacao(HashMap<String, Comissao> comissoes, Set<String> baseGoverno,
		HashMap<String, Projeto> projetos) {
		this.comissoes = comissoes;
		this.baseGoverno = baseGoverno;
		this.projetos = projetos;
		this.deputados = new HashMap<>();
	}
	
	/**
	 * Adiciona um deputado ao mapa de deputados.
	 * @param dni String que representa o dni da pessoa.
	 * @param pessoa Objeto Pessoa que e passado como parametro.
	 */
	public void adicionaDeputado(String dni, Pessoa pessoa) {
		this.deputados.put(dni, pessoa);
	} //REVER ISSO AQUI.
	
	/**
	 * Realiza a votacao no plenario.
	 * @param codigo String que representa o codigo do projeto. 
	 * @param statusGovernista String que representa o indicativo do status de governo do projeto.
	 * @param presentes String que contem os dni's das pessoas presentes na votacao.
	 * @return booleano representando a aprovacao do projeto.
	 */
	public boolean votarPlenario(String codigo, String statusGovernista, String presentes) {
		int aprovacoes = 0;
		String[] deputados = presentes.split(",");
		this.validarStatus(statusGovernista, "Erro ao votar proposta: status invalido");
		this.procuraProjeto(codigo, "Erro ao votar proposta: projeto inexistente");
		if (statusGovernista.equals("GOVERNISTA")) {
			aprovacoes = (this.projetos.get(codigo) instanceof ProjetoLei) ? this.votarStatusGovernista(deputados) : this.votarStatusGovernista(this.deputados.keySet());
			return this.projetos.get(codigo).votarPlenario(this.deputados.size(), deputados.length, aprovacoes);
		}else if(statusGovernista.contentEquals("OPOSICAO")) {
			aprovacoes = (this.projetos.get(codigo) instanceof ProjetoLei) ? this.votarStatusOposicao(deputados) : this.votarStatusGovernista(this.deputados.keySet());
			return this.projetos.get(codigo).votarPlenario(this.deputados.size(), deputados.length, aprovacoes);
		}else {
			String interesses = this.projetos.get(codigo).getInteressesRelacionados();
			aprovacoes = (this.projetos.get(codigo) instanceof ProjetoLei) ? this.votarStatusLivre(deputados, interesses) : this.votarStatusLivre(this.deputados.keySet(), interesses);
			return this.projetos.get(codigo).votarPlenario(this.deputados.size(), deputados.length, aprovacoes);
		}
	}
	
	/**
	 * Retorna o mapa de deputados cadastrados ate o dado momento.
	 * @return Mapa de dni e Pessoa.
	 */
	public HashMap<String, Pessoa> getDeputados() {
		return this.deputados;
	}
	
	/**
	 * Retorna o resultado de uma votacao de uma proposta em uma comissao.
	 * @param codigo String que representa o codigo da proposta.
	 * @param statusGovernista String que representa o "modo" de votacao.
	 * @param proximoLocal String que representa o proximo local de votacao.
	 * @return booleano representando o resultado da votacao.
	 */
	public boolean votarComissao(String codigo, String statusGovernista, String proximoLocal) {
		this.validarStatus(statusGovernista, "Erro ao votar proposta: status invalido");
		this.procuraProjeto(codigo, "Erro ao votar proposta: projeto inexistente");
		String local = this.projetos.get(codigo).getLocalVerificador();
		Set<String> politicos = this.comissoes.get(local).getDeputados();
		int numeroDeputados = this.comissoes.get(local).getDeputados().size();
		int aprovacoes = 0;
		if (statusGovernista.equals("GOVERNISTA")) {
			aprovacoes =  this.votarStatusGovernista(politicos);
			return this.projetos.get(codigo).votarComissao(numeroDeputados, aprovacoes, proximoLocal);
		}else if(statusGovernista.equals("OPOSICAO")) {
			aprovacoes = this.votarStatusOposicao(politicos);
			return this.projetos.get(codigo).votarComissao(numeroDeputados, aprovacoes, proximoLocal);
		}else {
			String interesses = this.projetos.get(codigo).getInteressesRelacionados();
			aprovacoes = this.votarStatusLivre(politicos, interesses);
			return this.projetos.get(codigo).votarComissao(numeroDeputados, aprovacoes, proximoLocal);
		}
	}
	
	/**
	 * Realiza uma votacao que possui LIVRE como status.
	 * @param politicos Conjunto de politicos que votarao.
	 * @param interesses String que possui os interesses do projeto.
	 * @return inteiro que representa quantas aprovacoes o projeto teve.
	 */
	private int votarStatusLivre(Set<String> politicos, String interesses) {
		int aprovacoes = 0;
		for (String deputado : politicos) {
			String[] arrayInteresses = this.deputados.get(deputado).getInteresses().split(",");
			for (String interesse : arrayInteresses) {
				if (interesses.contains(interesse)) {
					aprovacoes ++;
					break;
				}
			}
		}
		return aprovacoes;
	}
	
	/**
	 * Realiza uma votacao que possui LIVRE como status.
	 * @param politicos Conjunto de politicos que votarao.
	 * @param interesses String que possui os interesses do projeto.
	 * @return inteiro que representa quantas aprovacoes o projeto teve.
	 */
	private int votarStatusLivre(String[] politicos, String interesses) {
		int aprovacoes = 0;
		for (String deputado : politicos) {
			String[] arrayInteresses = this.deputados.get(deputado).getInteresses().split(",");
			for (String interesse : arrayInteresses) {
				if (interesses.contains(interesse)) {
					aprovacoes ++;
					break;
				}
			}
		}
		return aprovacoes;
	}
	
	/**
	 * Realiza uma votacao que possui OPOSICAO como status.
	 * @param politicos Conjunto de politicos que votarao.
	 * @return inteiro que representa quantas aprovacoes o projeto teve. 
	 */
	private int votarStatusOposicao(String[] politicos) {
		int aprovacoes = 0;
		for (String deputado : politicos) {
			if (!this.baseGoverno.contains(this.deputados.get(deputado).getPartido())) {
				aprovacoes ++;
			}
		}
		return aprovacoes;
	}
	
	/**
	 * Realiza uma votacao que possui OPOSICAO como status.
	 * @param politicos Conjunto de politicos que votarao.
	 * @return inteiro que representa quantas aprovacoes o projeto teve. 
	 */
	private int votarStatusOposicao(Set<String> politicos) {
		int aprovacoes = 0;
		for (String deputado : politicos) {
			if (!this.baseGoverno.contains(this.deputados.get(deputado).getPartido())) {
				aprovacoes ++;
			}
		}
		return aprovacoes;
	}
	
	/**
	 * Realiza uma votacao que possui GOVERNISTA como status.
	 * @param politicos Array que contem os dni's dos politicos da base.
	 * @return inteiro que representa quantas aprovacoes o projeto teve.
	 */
	private int votarStatusGovernista(String[] politicos) {
		int aprovacoes = 0;
		for (String deputado : politicos) {
			if (this.baseGoverno.contains(this.deputados.get(deputado).getPartido())) {
				aprovacoes ++;
			}
		}
		return aprovacoes;
	}
	
	/**
	 * Realiza uma votacao que possui GOVERNISTA como status.
	 * @param politicos Conjunto de dni's dos politicos da base.
	 * @return inteiro que representa quantas aprovacoes o projeto teve.
	 */
	private int votarStatusGovernista(Set<String> politicos) {
		int aprovacoes = 0;
		for (String deputado : politicos) {
			if (this.baseGoverno.contains(this.deputados.get(deputado).getPartido())) {
				aprovacoes ++;
			}
		}
		return aprovacoes;
	}
	
	/**
	 * Adiciona uma lei no contador de leis aprovadas de um deputado.
	 * @param dni String que representa o dni do deputado que vai ter a lei aprovada.
	 */
	public void fazerLei(String dni) {
		this.deputados.get(dni).fazerLei();
	}
	
	/**
	 * Valida um projeto.
	 * @param codigo String que representa o codigo do projeto que vai ser validado.
	 * @param mensagem String que representa uma messagem que pode ser exibida como uma excecao.
 	 */
	public void procuraProjeto(String codigo, String mensagem) {
		if (!this.projetos.containsKey(codigo)){
			throw new IllegalArgumentException(mensagem);
		}
	}
	
	/**
	 * Verifica o status de um projeto.
	 * @param status String que representa o status do projeto.
	 * @param mensagem String que contem uma mensagem que pode ser exibida como uma excecao.
	 */
	public void validarStatus(String status, String mensagem) {
		if (!"GOVERNISTAOPOSICAOLIVRE".contains(status)){
			throw new IllegalArgumentException(mensagem);
		}
	}
	
	/**
	 * Atribui um novo valor ao mapa de deputados.
	 * @param deputados Novo mapa de deputados.
	 */
	public void setDeputados(HashMap<String, Pessoa> deputados) {
		this.deputados = deputados;
	}
}