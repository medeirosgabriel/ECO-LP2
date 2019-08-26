package controllers;


import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Essa classe permite criar um objeto do tipo ControllerPartidosGovernistas, que auxilia o gerenciamento do sistema ECO.
 * @author Edimar Junior, Gabriel Brandao, Gabriel Medeiros, Ruan Gomes.
 *
 */
public class ControllerPartidosGovernistas {
	/**
	 * Conjunto das siglas dos partidos.
	 */
	private Set<String> partidosGovernistas;
	
	/**
	 * Construtor da classe ControllerPartidosGovernistas.
	 */
	public ControllerPartidosGovernistas() {
		this.partidosGovernistas = new HashSet<>();
	}
	
	/**
	 * Adiciona um partido no conjunto de partidos.
	 * @param partido String que representa a sigla de um partido.
	 */
	public void adicionarPartido(String partido) {
		this.partidosGovernistas.add(partido);
	}
	
	/**
	 * Retorna uma String com os partidos ate agora cadastrados, separados por virgula.
	 * @return String contendo os partidos cadastrados.
	 */
	public String exibirBase() {
		return partidosGovernistas.stream().collect(Collectors.joining(","));
	}
	
	/**
	 * Retorna o conjunto dos partidos ja cadastrados.
	 * @return Set com as siglas dos partidos ja cadastrados.
	 */
	public Set<String> getPartidosGovernistas() {
		return partidosGovernistas;
	}
	
	/**
	 * Atribui um novo conjunto de partidos ao conjunto de partidos.
	 * @param partidos Novo conjunto de arquivos.
	 */
	public void setPartidosGovernistas(Set<String> partidos) {
		this.partidosGovernistas = partidos;
	}
}