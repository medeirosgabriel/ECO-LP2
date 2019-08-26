package controllers;

import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;
import entidades.Pessoa;
import projetos.ProjetoEmentaConstitucional;
import projetos.ProjetoLei;
import projetos.ProjetoLeiComplementar;
import projetos.Projeto;

/**
 * Essa classe permite criar um objeto do tipo ControllerProjetos, que auxilia o sistema ECO.
 * @author Edimar Junior, Gabriel Brandao, Gabriel Medeiros, Ruan Gomes.
 *
 */
public class ControllerProjetos {
	
	/**
	 * Mapa de projetos.
	 */
	private HashMap<String, Projeto> projetos;
	
	private int quantProjetos;
	
	/**
	 * Mapa dos anos dos projetos.
	 */
	private HashMap<Integer, Integer[]> anoProjeto; // Primeira posicao - PL. Segunda Posicao - PLP. Terceira Posicao - PEC
	
	/**
	 * Construtor da classe ControllerProjetos.
	 */
	public ControllerProjetos() {
		this.projetos = new HashMap<>();
		this.anoProjeto = new HashMap<>();
	}
	
	/**
	 * Cadastra um projeto de lei(PL).
	 * @param pessoa Objeto Pessoa que vai ser recebido como parametro.
	 * @param ano String que representa o ano de cadastro do PL.
	 * @param ementa String que repersenta a ementa do PL.
	 * @param interesses String que representa os interesses do PL.
	 * @param url String que contem o link para a pagina web do PL.
	 * @param conclusivo booleano indicando se o PL e conclusivo ou nao.
	 * @return String contendo o codigo do PL.
	 */
	public String cadastrarPL(Pessoa pessoa, int ano, String ementa, String interesses, String url, boolean conclusivo) {
		String codigo = "PL " + this.geraCodigo(ano, 0);
		this.quantProjetos ++;
		ProjetoLei pl = new ProjetoLei(pessoa, ano, codigo, ementa, interesses, url, conclusivo, this.quantProjetos);
		this.projetos.put(codigo, pl);
		return codigo;
	}
	
	/**
	 * Cadastra um Projeto de Lei Complementar(PLP).
	 * @param deputado Objeto Deputado que vai ser recebido como parametro.
	 * @param ano String que representa o ano de cadastro do PLP.
	 * @param ementa String que repersenta a ementa do PLP.
	 * @param interesses String que representa os interesses do PLP.
	 * @param url String que contem o link para a pagina web do PLP.
	 * @param artigos String que contem os artigos referentes ao Projeto de Lei Complementar.
	 * @return String contendo o codigo do projeto.
	 */
	public String cadastrarPLP(Pessoa deputado, int ano, String ementa, String interesses, String url, String artigos) {
		String codigo = "PLP " + this.geraCodigo(ano, 1);
		this.quantProjetos ++;
		ProjetoLeiComplementar plp = new ProjetoLeiComplementar(deputado, ano, codigo, ementa, interesses, url, artigos,this.quantProjetos );
		this.projetos.put(codigo, plp);
		return codigo;
	}
	
	/**
	 * Cadastra um Proposta de Emenda Constitucional(PEC).
	 * @param deputado Objeto Deputado que vai ser recebido como parametro.
	 * @param ano String que representa o ano de cadastro do PEC.
	 * @param ementa String que repersenta a ementa do PEC.
	 * @param interesses String que representa os interesses do PEC.
	 * @param url String que contem o link para a pagina web do PEC.
	 * @param artigos String que contem os artigos referentes a proposta de emenda constitucional.
	 * @return String contendo o codigo da proposta.
	 */
	public String cadastrarPEC(Pessoa deputado, int ano, String ementa, String interesses, String url, String artigos) {
		String codigo = "PEC " + this.geraCodigo(ano, 2);
		this.quantProjetos ++;
		ProjetoEmentaConstitucional pec = new ProjetoEmentaConstitucional(deputado, ano, codigo, ementa, interesses, url, artigos,this.quantProjetos);
		this.projetos.put(codigo, pec);
		return codigo;
	}
	
	/**
	 * Gera um codigo do projeto de acordo com o ano do mesmo.
	 * @param ano inteiro que representa o ano do projeto a ser criado.
	 * @param posicao inteiro que representa o tipo do projeto.
	 * @return String que representa o codigo ja gerado.
	 */
	private String geraCodigo(int ano, int posicao) {
		if(this.anoProjeto.containsKey(ano)) {
			this.anoProjeto.get(ano)[posicao] ++;
		}else {
			Integer[] quantidades = {0, 0, 0};
			this.anoProjeto.put(ano, quantidades);
			this.anoProjeto.get(ano)[posicao] ++;
		}
		return String.format("%d/%d", this.anoProjeto.get(ano)[posicao], ano);
	}
	
	/**
	 * Verifica o dni do deputado que criou o projeto.
	 * @param codigo String que representa o codigo do projeto.
	 * @return String que representa o dni do deputado criador.
	 */
	public String procuraDniDeputadoProjeto(String codigo) {
		return this.projetos.get(codigo).getDni();
	}
	
	/**
	 * Retorna a representacao textual do projeto.
	 * @param codigo String que representa o codigo do projeto que vai ser buscado.
	 * @return String contendo a representacao textual do projeto.
	 */
	public String exibirProjeto(String codigo) {
		return this.projetos.get(codigo).toString();
	}
	
	/**
	 * Retorna o projeto a partir do seu identificador.
	 * @param codigo String que representa o codigo do projeto que vai ser buscado.
	 * @return Objeto do tipo Projeto que vai ser retornado.
	 */
	public Projeto retornaProjeto(String codigo) {
		return this.projetos.get(codigo);
	}
	
	public String pegarPropostaRelacionada(Pessoa pessoa) {
		List<Projeto> projetosFiltrados = this.projetos.values().stream()
				.filter(p -> !p.getSituacao().equals("APROVADO") 
						&& !p.getSituacao().equals("ARQUIVADO") && p.retornaQuantidadeDeInteresses(pessoa.getInteresses()) > 0).collect(Collectors.toList());
		return pessoa.getEstrategia().ordenar(projetosFiltrados, pessoa.getInteresses());
	}
	
	public String exibirTramitacao(String codigo) {
		if (!this.projetos.containsKey(codigo)) {
			throw new IllegalArgumentException("Erro ao exibir tramitacao: projeto inexistente");
		}
		return this.projetos.get(codigo).exibirTramitacao();
	}
	
	/**
	 * Retorna um mapa de projetos.
	 * @return Mapa de projetos que vai ser retornado.
	 */
	public HashMap<String, Projeto> getProjetos() {
		return this.projetos;
	}
	
	/**
	 * Retorna o mapa dos anos dos projetos.
	 * @return Mapa dos anos dos projetos.
	 */
	public HashMap<Integer, Integer[]> getAnoProjeto() {
		return this.anoProjeto;
	}
	
	/**
	 * Atribui um novo mapa ao mapa de projetos.
	 * @param projetos Novo mapa de projetos.
	 */
	public void setProjetos(HashMap<String, Projeto> projetos) {
		this.projetos = projetos;
		
	}
	
	/**
	 * Atribui um novo mapa de anos dos projetos ao mapa anoProjeto.
	 * @param anoProjetos Novo mapa de anos dos projetos.
	 */
	public void setAnoProjetos(HashMap<Integer, Integer[]> anoProjetos) {
		this.anoProjeto = anoProjetos;
		
	}
	
	/**
	 * Retorna o mapa dos anos dos projetos.
	 * @return Mapa dos anos dos projetos.
	 */
	public HashMap<Integer, Integer[]> getAnoProjetos() {
		return this.anoProjeto;
	}
}