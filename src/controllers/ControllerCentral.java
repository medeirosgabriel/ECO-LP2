package controllers;

import java.util.HashMap;
import java.util.Set;

import entidades.Comissao;
import entidades.GerenciadorArquivos;
import entidades.Pessoa;
import projetos.Projeto;

/**
 * Essa classe permite criar um objeto do tipo ControllerCentral, que auxilia o gerenciamento do sistema ECO.
 * @author Edimar Junior, Gabriel Brandao, Gabriel Medeiros, Ruan Gomes.
 *
 */
public class ControllerCentral {

	/**
	 * Controller de pessoas.
	 */
	private ControllerPessoa contPessoas;
	
	/**
	 * Controller das comissoes.
	 */
	private ControllerComissao contComissao;
	
	/**
	 * Controller dos partidos.
	 */
	private ControllerPartidosGovernistas contPartidos;
	
	/**
	 * Controller dos projetos(PL, PLP, PEC).
	 */
	private ControllerProjetos contProjetos;
	
	/**
	 * Controller de votacao.
	 */
	private ControllerVotacao contVotacao;
	
	/**
	 * Construtor da classe controllerCentral. Inicia os outros controllers.
	 */
	public ControllerCentral() {
		this.contPessoas = new ControllerPessoa();
		this.contPartidos = new ControllerPartidosGovernistas();
		this.contComissao = new ControllerComissao();
		this.contProjetos = new ControllerProjetos();
		this.contVotacao = new ControllerVotacao(this.contComissao.getComissoes(), this.contPartidos.getPartidosGovernistas(), this.contProjetos.getProjetos());
	}
	
	/**
	 * Cadastra no sistema uma pessoa que nao possui partido.
	 * @param nome String que vai representar o nome da pessoa.
	 * @param dni String que vai representar o documento de identificacao da pessoa.
	 * @param estado String que vai representar a sigla do estado da pessoa.
	 * @param interesses String que representa os interesses da pessoa.
	 */
	public void cadastrarPessoa(String nome, String dni, String estado, String interesses) {
		Validacao.validarString(nome, "Erro ao cadastrar pessoa: nome nao pode ser vazio ou nulo");
		Validacao.validarString(dni, "Erro ao cadastrar pessoa: dni nao pode ser vazio ou nulo");
		Validacao.validarString(estado,"Erro ao cadastrar pessoa: estado nao pode ser vazio ou nulo");
		Validacao.validarDni(dni, "Erro ao cadastrar pessoa: dni invalido");
		this.contPessoas.cadastrarPessoa(nome, dni, estado, interesses);
	}
	
	/**
	 * Cadastra no sistema uma pessoa que possui partido.
	 * @param nome String que vai representar o nome da pessoa.
	 * @param dni String que vai representar o documento de identificacao da pessoa.
	 * @param estado String que vai representar a sigla do estado da pessoa.
	 * @param interesses String que vai representar os interesses da pessoa.
	 * @param partido String que vai representar a sigla do partido filiado a pessoa.
	 */
	public void cadastrarPessoa(String nome, String dni, String estado, String interesses, String partido) {
		Validacao.validarString(nome, "Erro ao cadastrar pessoa: nome nao pode ser vazio ou nulo");
		Validacao.validarString(dni, "Erro ao cadastrar pessoa: dni nao pode ser vazio ou nulo");
		Validacao.validarString(estado,"Erro ao cadastrar pessoa: estado nao pode ser vazio ou nulo");
		Validacao.validarDni(dni, "Erro ao cadastrar pessoa: dni invalido");
		this.contPessoas.cadastrarPessoa(nome, dni, estado, interesses, partido);
	}
	
	/**
	 * Torna um deputado a pessoa referente ao dni em questao..
	 * @param dni String que representa o documento de identificacao da pessoa.
	 * @param dataDeInicio String que representa a data de iniciacao.
	 */
	public void cadastrarDeputado(String dni, String dataDeInicio) {
		Validacao.validarString(dni, "Erro ao cadastrar deputado: dni nao pode ser vazio ou nulo");
		Validacao.validarDni(dni, "Erro ao cadastrar deputado: dni invalido");
		if (!this.contPessoas.verificaExistenciaPessoa(dni)) {
			throw new IllegalArgumentException("Erro ao cadastrar deputado: pessoa nao encontrada");
		}
		Validacao.validarString(dataDeInicio, "Erro ao cadastrar deputado: data nao pode ser vazio ou nulo");
		Validacao.validarData(dataDeInicio, "Erro ao cadastrar deputado: data futura", "Erro ao cadastrar deputado: data invalida");
		this.contPessoas.cadastrarDeputado(dni, dataDeInicio);
		this.contVotacao.adicionaDeputado(dni, this.contPessoas.retornaPessoa(dni));
	}
	
	/**
	 * Exibe a representacao textual de uma pessoa.
	 * @param dni String que representa o documento de identificacao da pessoa que vai ser exibida.
	 * @return String contendo a representacao textual.
	 */
	public String exibirPessoa(String dni) {
		Validacao.validarString(dni, "Erro ao exibir pessoa: dni nao pode ser vazio ou nulo");
		Validacao.validarDni(dni, "Erro ao exibir pessoa: dni invalido");
		return this.contPessoas.exibirPessoa(dni);
	}
	
	/**
	 * Cadastra um partido a partir de sua sigla.
	 * @param partido String que representa a sigla do partido.
	 */
	public void cadastrarPartido(String partido) {
		Validacao.validarString(partido, "Erro ao cadastrar partido: partido nao pode ser vazio ou nulo");
		this.contPartidos.adicionarPartido(partido);
	}
	
	/**
	 * Retorna uma String com os partidos ja cadastrados.
	 * @return String contendo as siglas dos partidos ja cadastrados.
	 */
	public String exibirBase() {
		return this.contPartidos.exibirBase();
	}
	
	/**
	 * Cadastra uma comissao no sistema a partir do tema da comissao e dos politicos.
	 * @param tema String que representa o tema da comissao.
	 * @param politicos String que contem os dni's dos politicos, separados por virgula.
	 */
	public void cadastrarComissao(String tema, String politicos) {
		Validacao.validarString(tema, "Erro ao cadastrar comissao: tema nao pode ser vazio ou nulo");
		Validacao.validarString(politicos, "Erro ao cadastrar comissao: lista de politicos nao pode ser vazio ou nulo");
		this.contComissao.existeComissao(tema,"Erro ao cadastrar comissao: tema existente");
		this.contPessoas.validarPoliticos(politicos, "Erro ao cadastrar comissao: dni invalido", "Erro ao cadastrar comissao: pessoa inexistente", 
				"Erro ao cadastrar comissao: pessoa nao eh deputado");
		String[] arrayDeputados = politicos.split(",");
		this.contComissao.cadastrarComissao(tema, arrayDeputados);
	}
	
	/**
	 * Cadastra um projeto de lei(PL).
	 * @param dni String que representa o documento de identificacao do deputado que criou o PL.
	 * @param ano String que representa o ano de cadastro do PL.
	 * @param ementa String que repersenta a ementa do PL.
	 * @param interesses String que representa os interesses do PL.
	 * @param url String que contem o link para a pagina web do PL.
	 * @param conclusivo booleano indicando se o PL e conclusivo ou nao.
	 * @return String contendo o codigo do PL.
	 */
	public String cadastrarPL(String dni, int ano, String ementa, String interesses, String url, boolean conclusivo) {
		Validacao.validarString(dni, "Erro ao cadastrar projeto: autor nao pode ser vazio ou nulo");
		Validacao.validarDni(dni, "Erro ao cadastrar projeto: dni invalido");
		Validacao.validarDataProjeto(ano);
		Validacao.validarString(ementa, "Erro ao cadastrar projeto: ementa nao pode ser vazia ou nula");
		Validacao.validarString(interesses, "Erro ao cadastrar projeto: interesse nao pode ser vazio ou nulo");
		Validacao.validarString(url, "Erro ao cadastrar projeto: url nao pode ser vazio ou nulo");
		if (!this.contPessoas.verificaExistenciaPessoa(dni)) {
			throw new IllegalArgumentException("Erro ao cadastrar projeto: pessoa inexistente");
		}
		this.contPessoas.verificaPartidoPessoa(dni, "Erro ao cadastrar projeto: pessoa nao eh deputado");
		return this.contProjetos.cadastrarPL(this.contPessoas.retornaPessoa(dni), ano, ementa, interesses, url, conclusivo);
	}
	
	/**
	 * Cadastra um Projeto de Lei Complementar(PLP).
	 * @param dni String que representa o documento de identificacao do deputado que criou o PLP.
	 * @param ano String que representa o ano de cadastro do PLP.
	 * @param ementa String que repersenta a ementa do PLP.
	 * @param interesses String que representa os interesses do PLP.
	 * @param url String que contem o link para a pagina web do PLP.
	 * @param artigos String que contem os artigos referentes ao Projeto de Lei Complementar.
	 * @return String contendo o codigo do projeto.
	 */
	public String cadastrarPLP(String dni, int ano, String ementa, String interesses, String url, String artigos) {
		Validacao.validarString(dni, "Erro ao cadastrar projeto: autor nao pode ser vazio ou nulo");
		Validacao.validarDni(dni, "Erro ao cadastrar projeto: dni invalido");
		Validacao.validarDataProjeto(ano);
		Validacao.validarString(ementa, "Erro ao cadastrar projeto: ementa nao pode ser vazia ou nula");
		Validacao.validarString(interesses, "Erro ao cadastrar projeto: interesse nao pode ser vazio ou nulo");
		Validacao.validarString(url, "Erro ao cadastrar projeto: url nao pode ser vazio ou nulo");
		Validacao.validarString(artigos, "Erro ao cadastrar projeto: artigo nao pode ser vazio ou nulo");
		if (!this.contPessoas.verificaExistenciaPessoa(dni)) {
			throw new IllegalArgumentException("Erro ao cadastrar projeto: pessoa inexistente");
		}
		this.contPessoas.verificaPartidoPessoa(dni, "Erro ao cadastrar projeto: pessoa nao eh deputado");
		return this.contProjetos.cadastrarPLP(this.contPessoas.retornaPessoa(dni), ano, ementa, interesses, url, artigos);
	}
	
	/**
	 * Cadastra um Proposta de Emenda Constitucional(PEC).
	 * @param dni String que representa o documento de identificacao do deputado que criou o PEC.
	 * @param ano String que representa o ano de cadastro do PEC.
	 * @param ementa String que repersenta a ementa do PEC.
	 * @param interesses String que representa os interesses do PEC.
	 * @param url String que contem o link para a pagina web do PEC.
	 * @param artigos String que contem os artigos referentes a proposta de emenda constitucional.
	 * @return String contendo o codigo da proposta.
	 */
	public String cadastrarPEC(String dni, int ano, String ementa, String interesses, String url, String artigos) {
		Validacao.validarString(dni, "Erro ao cadastrar projeto: autor nao pode ser vazio ou nulo");
		Validacao.validarDni(dni, "Erro ao cadastrar projeto: dni invalido");
		Validacao.validarDataProjeto(ano);
		Validacao.validarString(ementa, "Erro ao cadastrar projeto: ementa nao pode ser vazia ou nula");
		Validacao.validarString(interesses, "Erro ao cadastrar projeto: interesse nao pode ser vazio ou nulo");
		Validacao.validarString(url, "Erro ao cadastrar projeto: url nao pode ser vazio ou nulo");
		Validacao.validarString(artigos, "Erro ao cadastrar projeto: artigo nao pode ser vazio ou nulo");
		if (!this.contPessoas.verificaExistenciaPessoa(dni)) {
			throw new IllegalArgumentException("Erro ao cadastrar projeto: pessoa inexistente");
		}
		this.contPessoas.verificaPartidoPessoa(dni, "Erro ao cadastrar projeto: pessoa nao eh deputado");
		return this.contProjetos.cadastrarPEC(this.contPessoas.retornaPessoa(dni), ano, ementa, interesses, url, artigos);
	}
	
	/**
	 * Retorna o resultado de uma votacao de uma proposta em uma comissao.
	 * @param codigo String que representa o codigo da proposta.
	 * @param statusGovernista String que representa o "modo" de votacao.
	 * @param proximoLocal String que representa o proximo local de votacao.
	 * @return booleano representando o resultado da votacao.
	 */
	public boolean votarComissao(String codigo, String statusGovernista, String proximoLocal) {
		Validacao.validarString(codigo, "Codigo nulo ou vazio");
		Validacao.validarString(statusGovernista, "Status vazio ou nulo");
		Validacao.validarString(proximoLocal, "Erro ao votar proposta: proximo local vazio");
		if (!proximoLocal.equals("-")  && !proximoLocal.equals("plenario") && !this.contComissao.existeComissao(proximoLocal)) {
			throw new IllegalArgumentException("Erro ao votar proposta: CCJC nao cadastrada");
		}
		boolean aprovada = this.contVotacao.votarComissao(codigo, statusGovernista, proximoLocal);
		return aprovada;
	}
	
	/**
	 * Retorna o resultado de uma votacao no plenario.
	 * @param codigo String que representa o codigo da proposta.
	 * @param statusGovernista String que representa o "modo" de votacao.
	 * @param presentes String que contem os dnis dos deputados separados por virgula.
	 * @return booleano representando o resultado da votacao.
	 */
	public boolean votarPlenario(String codigo, String statusGovernista, String presentes) {
		Validacao.validarString(codigo, "Codigo nulo ou vazio");
		Validacao.validarString(statusGovernista, "Status vazio ou nulo");
		Validacao.validarString(presentes, "Presentes vazio ou nulo");
		boolean aprovada = this.contVotacao.votarPlenario(codigo, statusGovernista, presentes);
		return aprovada;
	}
	
	/**
	 * Exibe a representacao textual de um projeto a partir do seu codigo.
	 * @param codigo String que contem o codigo do projeto.
	 * @return String que contem a representacao textual.
	 */
	public String exibirProjeto(String codigo) {
		Validacao.validarString(codigo,"Codigo nao pode ser vazio ou  nulo.");
		return this.contProjetos.exibirProjeto(codigo);
	}
	
	/**
	 * Exibe as tramitacoes de uma proposta, separadas por virgula.
	 * @param codigo String que representa o codigo da proposta a ser pesquisada.
	 * @return String contendo as tramitacoes.
	 */
	public String exibirTramitacao(String codigo) {
		Validacao.validarString(codigo,"Codigo nao pode ser vazio ou  nulo.");
		return this.contProjetos.exibirTramitacao(codigo);
	}
	
	/**
	 * Configura o metodo de desempate de duas propostas com interesses semelhantes a uma pessoa.
	 * @param dni String que representa o dni da pessoa a ser pesquisada.
	 * @param estrategia String que representa a nova estrategia que sera usada.
	 */
	public void configurarEstrategiaPropostaRelacionada(String dni, String estrategia) {
		Validacao.validarString(dni, "Erro ao configurar estrategia: pessoa nao pode ser vazia ou nula");
		Validacao.validarDni(dni, "Erro ao configurar estrategia: dni invalido");
		Validacao.validarString(estrategia, "Erro ao configurar estrategia: estrategia vazia");
		if (!"CONCLUSAOCONSTITUCIONALAPROVACAO".contains(estrategia)) {
			throw new IllegalArgumentException("Erro ao configurar estrategia: estrategia invalida");
		}
		this.contPessoas.configurarEstrategiaPropostaRelacionada(dni, estrategia);
	}
	
	/**
	 * Exibe a proposta mais semelhante aos interesses de uma pessoa.
	 * @param dni String que representar o dni da pessoa a ser comparada.
	 * @return String contendo o codigo da proposta.
	 */
	public String pegarPropostaRelacionada(String dni) {
		Validacao.validarString(dni, "Erro ao pegar proposta relacionada: pessoa nao pode ser vazia ou nula");
		Validacao.validarDni(dni, "Erro ao pegar proposta relacionada: dni invalido");
		return this.contProjetos.pegarPropostaRelacionada(this.contPessoas.retornaPessoa(dni));
	}
	
	/**
	 * Salva os dados do sistema ECO para uso posterior.
	 */
	public void salvarSistema() {
		GerenciadorArquivos.salvarSistema(this.contPessoas.getPessoas(), this.contComissao.getComissoes(), 
				this.contProjetos.getProjetos(), this.contProjetos.getAnoProjetos(), this.contPartidos.getPartidosGovernistas(), this.contVotacao.getDeputados());
	}
	
	/**
	 * Usa dados ja salvos do sistema ECO.
	 */
	public void carregarSistema() {
		GerenciadorArquivos.carregarSistema(this);
	}

	/**
	 * Limpa os dados do sistema ECO.
	 */
	public void limparSistema() {
		this.contComissao = new ControllerComissao();
		this.contPartidos = new ControllerPartidosGovernistas();
		this.contPessoas = new ControllerPessoa();
		this.contProjetos = new ControllerProjetos();
		this.contVotacao = new ControllerVotacao(this.contComissao.getComissoes(), this.contPartidos.getPartidosGovernistas(), this.contProjetos.getProjetos());
		this.salvarSistema();
	}
	
	/**
	 * Recria o controller de votacao a partir dos objetos(utilizados pelo mesmo)
	 * passados como parametro pelo gerenciador de arquivos.
	 * @param comissoes Mapa de comissoes ja cadastradas no sistema ECO.
	 * @param baseGoverno Conjunto de Strings com as siglas dos partidos ja cadastrados no sistema ECO.
	 * @param projetos Mapa de projetos cadastrados no sistema ECO.
	 * @param pessoas Mapa de pessoas ja cadastradas no sistema ECO.
	 * @param deputados Mapa de deputados cadastrados no sistema ECO.
	 */
	public void setControleVotacao(HashMap<String, Comissao> comissoes, Set<String> baseGoverno,
		HashMap<String, Projeto> projetos, HashMap<String, Pessoa> pessoas, HashMap<String, Pessoa> deputados) {
		this.contVotacao = new ControllerVotacao(comissoes, baseGoverno, projetos);
		this.contVotacao.setDeputados(deputados);
	}
	
	/**
	 * Seta todos as colecoes mais importantes referentes ao sistema ECO.
	 * @param pessoas Mapa de pessoas ja cadastradas no sistema ECO.
	 * @param comissoes Mapa de comissoes ja cadastradas no sistema ECO.
	 * @param projetos Mapa de projetos cadastrados no sistema ECO.
	 * @param anoProjetos Mapa dos anos dos projetos cadastrados ate o momento no sistema ECO.
	 * @param partidos Conjunto de Strings com as siglas dos partidos ja cadastrados no sistema ECO.
	 * @param deputados Mapa de deputados cadastrados no sistema ECO.
	 */
	public void setParam(HashMap<String, Pessoa> pessoas, HashMap<String, Comissao> comissoes, HashMap<String, Projeto> projetos, 
			HashMap<Integer, Integer[]> anoProjetos, Set<String> partidos, HashMap<String, Pessoa> deputados) {
		this.contPessoas.setMapaPessoas(pessoas);
		this.contComissao.setMapaComissoes(comissoes);
		this.contProjetos.setProjetos(projetos);
		this.contProjetos.setAnoProjetos(anoProjetos);
		this.contPartidos.setPartidosGovernistas(partidos);
		this.setControleVotacao(comissoes, partidos, projetos, pessoas, deputados);
	}
}