package entidades;

import java.io.Serializable;

import controllers.Validacao;
import estrategias.EstrategiaConstitucional;
import estrategias.EstrategiaOrdenacao;

/**
 * Essa classe permite criar um objeto do tipo Pessoa.
 * @author Edimar Junior, Gabriel Brandao, Gabriel Medeiros, Ruan Gomes.
 *
 */
public class Pessoa implements Serializable{
	
	private static final long serialVersionUID = 1L;
	/**
	 * Nome da pessoa.
	 */
	private String nome;
	/**
	 * Documento de identificacao de uma Pessoa.
	 */
	private String dni;
	/**
	 * Sigla do estado de uma Pessoa.
	 */
	private String estado;
	/**
	 * Interesses de uma Pessoa.
	 */
	private String interesses;
	/**
	 * Partido de uma Pessoa.
	 */
	private String partido;
	/**
	 * Funcao de uma pessoa(Deputado ou Civil).
	 */
	private Funcao funcao;
	
	private EstrategiaOrdenacao estrategia;
	
	/**
	 * Construtor da classe Pessoa.
	 * @param nome String que representara o nome da pessoa.
	 * @param dni String que representara o documento de identificacao da pessoa.
	 * @param estado String que representara a sigla do estado da pessoa.
	 * @param interesses String que representara os interesses da pessoa.
	 */
	public Pessoa(String nome, String dni, String estado, String interesses) {
		Validacao.validarString(nome, "Erro na criacao da Pessoa: nome invalido");
		Validacao.validarString(dni, "Erro na criacao da Pessoa: dni invalido");
		Validacao.validarString(estado, "Erro na criacao da Pessoa: estado invalido");
		this.estrategia = new EstrategiaConstitucional();
		this.nome = nome;
		this.dni = dni;
		this.estado = estado;
		this.interesses = interesses;
		this.funcao = new Civil();
		this.estrategia = new EstrategiaConstitucional();
	}
	
	/**
	 * Segundo construtor da classe Pessoa, utilizado quando a Pessoa e filiada a um partido.
	 * @param nome String que representara o nome da pessoa.
	 * @param dni String que representara o documento de identificacao da pessoa.
	 * @param estado String que representara a sigla do estado da pessoa.
	 * @param interesses String que representara os interesses da pessoa.
	 * @param partido String que representa a sigla do partido o qual a pessoa e filiada.
	 */
	public Pessoa(String nome, String dni, String estado, String interesses, String partido) {
		Validacao.validarString(nome, "Erro na criacao da Pessoa");
		Validacao.validarString(dni, "Erro na criacao da Pessoa");
		Validacao.validarString(estado, "Erro na criacao da Pessoa");
		this.nome = nome;
		this.dni = dni;
		this.estado = estado;
		this.interesses = interesses;
		this.partido = partido;
		this.funcao = new Civil();
		this.estrategia = new EstrategiaConstitucional();
	}
	
	/**
	 * Retorna a representacao textual de uma pessoa.
	 * @return String contendo a representacao textual do objeto Pessoa em questao.
	 */
	public String representacao() {
		return this.funcao.representacao(this.nome, this.dni, this.estado, this.interesses, this.partido);
	}
	
	/**
	 * Retorna o partido o qual a pessoa e filiada.
	 * @return String contendo a sigla do partido.
	 */
	public String getPartido() {
		return partido;
	}
	
	/**
	 * Retorna a funcao da pessoa.
	 * @return Objeto do tipo Funcao.
	 */
	public Funcao getFuncao() {
		return funcao;
	}
	
	/**
	 * Define uma funcao para a pessoa.
	 * @param funcao Funcao que sera passada como parametro.
	 */
	public void setFuncao(Funcao funcao) {
		this.funcao = funcao;
	}
	
	/**
	 * Retorna um inteiro a partir de uma operacao de um numero primo e do
	 * atributo dni.
	 * @return valor inteiro que representa o objeto.
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((dni == null) ? 0 : dni.hashCode());
		return result;
	}
	
	/**
	 * Retorna um valor booleano a partir de uma comparacao de objetos.
	 * @param obj objeto do tipo object que sera passado como parametro.
	 * @return resultado da comparacao entre os objetos.
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Pessoa other = (Pessoa) obj;
		if (dni == null) {
			if (other.dni != null)
				return false;
		} else if (!dni.equals(other.dni))
			return false;
		return true;
	}
	
	/**
	 * Retorna os interesses da pessoa.
	 * @return String contendo os interesses separados por virgula.
	 */
	public String getInteresses() {
		return interesses;
	}
	
	/**
	 * Chama o metodo fazerLei de Deputado.
	 */
	public void fazerLei() {
		((Deputado) this.funcao).fazerLei();
	}

	/**
	 * Retorna uma String contendo o dni da pessoa.
	 * @return String que representa o dni da pessoa.
	 */
	public String getDni() {
		return this.dni;
	}
	
	/**
	 * Retorna a estrategia de Ordenacao para desempate de projetos semelhantes.
	 * @return Estrategia de desempate que a pessoa prefere.
	 */
	public EstrategiaOrdenacao getEstrategia() {
		return estrategia;
	}
	
	/**
	 * Modifica a estrategia de desempate de uma pessoa.
	 * @param estrategia Nova estrategia de desempate.
	 */
	public void setEstrategia(EstrategiaOrdenacao estrategia) {
		this.estrategia = estrategia;
	}
}