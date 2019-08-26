package sistema;

import controllers.ControllerCentral;


import easyaccept.EasyAccept;
/**
 * Permite criar um objeto do tipo Facade, que faz a comunicacao de IO entre o usuario e o programa.
 * @author Edimar Junior, Gabriel Brandao, Gabriel Medeiros, Ruan Gomes.
 *
 */
public class Facade {
	/**
	 * Controller central sendo instanciado.
	 */
	private ControllerCentral contCentral;
	
	/**
	 * Metodo principal da Facade, onde os testes do acceptance_test sao chamados.
	 * @param args String que pode ser passada como linha de comando.
	 */
	public static void main(String[] args) {
		
		args = new String[] {"sistema.Facade",
				"acceptance_test/use_case_1.txt",
				"acceptance_test/use_case_2.txt",
				"acceptance_test/use_case_3.txt",
				"acceptance_test/use_case_4.txt",
				"acceptance_test/use_case_5.txt",
				"acceptance_test/use_case_6.txt",
				"acceptance_test/use_case_7.txt",
				"acceptance_test/use_case_8.txt",
				"acceptance_test/use_case_9.txt"};
		EasyAccept.main(args);
	}
	
	/**
	 * Construtor da classe Facade.
	 */
	public Facade() {
		this.contCentral = new ControllerCentral();
	}
	
	/**
	 * Cadastra no sistema uma pessoa que nao possui partido.
	 * @param nome String que vai representar o nome da pessoa.
	 * @param dni String que vai representar o dni da pessoa.
	 * @param estado String que vai representar a sigla do estado da pessoa.
	 * @param interesses String que contem os interesses da pessoa que ira ser criada.
	 */
	public void cadastrarPessoa(String nome, String dni, String estado, String interesses) {
		this.contCentral.cadastrarPessoa(nome, dni, estado, interesses);
	}
	
	/**
	 * Cadastra no sistema uma pessoa que possui partido.
	 * @param nome String que vai representar o nome da pessoa.
	 * @param dni String que vai representar o dni da pessoa.
	 * @param estado String que vai representar a sigla do estado da pessoa.
	 * @param interesses String que contem os interesses da pessoa que ira ser criada.
	 * @param partido String que representa o partido o qual a pessoa e filiada.
	 */
	public void cadastrarPessoa(String nome, String dni, String estado, String interesses, String partido) {
		this.contCentral.cadastrarPessoa(nome, dni, estado, interesses, partido);
	}
	
	/**
	 * Transforma uma pessoa ja cadastrada no sistema em deputado.
	 * @param dni String que contem o documento de identificacao da pessoa que vai ser modificada.
	 * @param dataDeInicio String que representa a data de iniciacao do deputado.
	 */
	public void cadastrarDeputado(String dni, String dataDeInicio) {
		this.contCentral.cadastrarDeputado(dni, dataDeInicio);
	}
	
	/**
	 * Exibe a representacao textual de uma pessoa.
	 * @param dni String que representa o documento de identificacao que vai ser exibido.
	 * @return String que contem a representacao textual de uma pessoa.
	 */
	public String exibirPessoa(String dni) {
		return this.contCentral.exibirPessoa(dni);
	}
	
	/**
	 * Cadastra um partido no sistema, a partir de sua sigla.
	 * @param partido String que contem a sigla do partido.
	 */
	public void cadastrarPartido(String partido) {
		this.contCentral.cadastrarPartido(partido);
	}
	
	/**
	 * Exibe os partidos da base do governo.
	 * @return String contendo as siglas dos partidos da base do governo separados por virgula.
	 */
	public String exibirBase() {
		return this.contCentral.exibirBase();
	}
	
	/**
	 * Cadastra uma comissao a partir do tema da mesma e dos politicos pertencentes.
	 * @param tema String que representa o tema da comissao.
	 * @param politicos String contendo os politicos pertencentes separados por virgula.
	 */
	public void cadastrarComissao(String tema, String politicos) {
		this.contCentral.cadastrarComissao(tema, politicos);
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
		return this.contCentral.cadastrarPL(dni, ano, ementa, interesses, url, conclusivo);
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
		return this.contCentral.cadastrarPLP(dni, ano, ementa, interesses, url, artigos);
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
		return this.contCentral.cadastrarPEC(dni, ano, ementa, interesses, url, artigos);
	}
	
	/**
	 * Exibe a representacao textual de um projeto a partir do seu codigo.
	 * @param codigo String que contem o codigo do projeto.
	 * @return String que contem a representacao textual.
	 */
	public String exibirProjeto(String codigo) {
		return this.contCentral.exibirProjeto(codigo);
	}
	
	/**
	 * Retorna o resultado de uma votacao de uma proposta em uma comissao.
	 * @param codigo String que representa o codigo da proposta.
	 * @param statusGovernista String que representa o "modo" de votacao.
	 * @param proximoLocal String que representa o proximo local de votacao.
	 * @return booleano representando o resultado da votacao.
	 */
	public boolean votarComissao(String codigo, String statusGovernista, String proximoLocal) {
		return this.contCentral.votarComissao(codigo, statusGovernista, proximoLocal);
	}
	
	/**
	 * Retorna o resultado de uma votacao no plenario.
	 * @param codigo String que representa o codigo da proposta.
	 * @param statusGovernista String que representa o "modo" de votacao.
	 * @param presentes String que contem os dnis dos deputados separados por virgula.
	 * @return booleano representando o resultado da votacao.
	 */
	public boolean votarPlenario(String codigo, String statusGovernista, String presentes) {
		return this.contCentral.votarPlenario(codigo, statusGovernista, presentes);
	}
	
	/**
	 * Exibe as tramitacoes de uma proposta em forma de string, concatenadas com virgulas.
	 * @param codigo String que representa o codigo da proposta.
	 * @return String contendo as tramitacoes;
	 */
	public String exibirTramitacao(String codigo) {
		return this.contCentral.exibirTramitacao(codigo);
	}
	
	/**
	 * Modifica a estrategia de melhor escolha de propostas mais relacionadas a uma pessoa.
	 * @param dni String que representa o dni da pessoa que vai ter a estrategia de proposta modificada.
	 * @param estrategia String que representa a nova estrategia que sera usada.
	 */
	public void configurarEstrategiaPropostaRelacionada(String dni, String estrategia) {
		this.contCentral.configurarEstrategiaPropostaRelacionada(dni, estrategia);
	}
	
	/**
	 * Retorna o codigo da proposta com maior quantida de interesses em comum com os interesses da pessoa.
	 * @param dni String que representa o dni da pessoa.
	 * @return String que contem o codigo da proposta mais relacionada.
	 */
	public String pegarPropostaRelacionada(String dni) {
		return this.contCentral.pegarPropostaRelacionada(dni);
	}
	
	/**
	 * Limpa os dados do sistema ECO.
	 */
	public void limparSistema() {
		this.contCentral.limparSistema();
	}
	
	/**
	 * Salva os dados do sistema ECO para uso posterior.
	 */
	public void salvarSistema() {
		this.contCentral.salvarSistema();
	}
	
	/**
	 * Usa dados ja salvos do sistema ECO.
	 */
	public void carregarSistema() {
		this.contCentral.carregarSistema();
	}
}