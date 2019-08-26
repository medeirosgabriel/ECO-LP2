package entidades;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.Set;

import controllers.ControllerCentral;
import projetos.Projeto;

/**
 * Classe responsavel pela persistencia de dados do sistema ECO.
 * @author Edimar Junior, Gabriel Brandao, Gabriel Medeiros, Ruan Gomes.
 *
 */
public class GerenciadorArquivos {
	
	/**
	 * Salva o estado atual do sistema ECO em arquivos para uso posterior.
	 * @param pessoas Mapa de pessoas do sistema ECO.
	 * @param comissoes Mapa das comissoes do sistema ECO.
	 * @param projetos Mapa dos projetos do sistema ECO.
	 * @param anoProjetos Mapa dos anos do projetos do sistema ECO.
	 * @param partidos Conjuntos das siglas dos partidos cadastrados no sistema ECO.
	 * @param deputados Mapa dos deputados cadastrados no sistema ECO.
	 */
	public static void salvarSistema(HashMap<String, Pessoa> pessoas, HashMap<String, Comissao> comissoes,
			HashMap<String, Projeto> projetos, HashMap<Integer, Integer[]> anoProjetos, Set<String> partidos, HashMap<String, Pessoa> deputados) {
		try {
			GerenciadorArquivos.salvarObjeto(pessoas, "pessoas.bin");
			GerenciadorArquivos.salvarObjeto(projetos, "projetos.bin");
			GerenciadorArquivos.salvarObjeto(anoProjetos, "anoProjetos.bin");
			GerenciadorArquivos.salvarObjeto(partidos, "partidos.bin");
			GerenciadorArquivos.salvarObjeto(comissoes, "comissoes.bin");
			GerenciadorArquivos.salvarObjeto(deputados, "deputados.bin");
			 
		} catch (IOException ioe) {
			System.out.println("Deu ruim");
			ioe.printStackTrace();
		}
	}
	
	/**
	 * Carrega o ultimo estado salvo do sistema ECO.
	 * @param contCent Controller Central do sistema ECO.
	 */
	@SuppressWarnings("unchecked")
	public static void carregarSistema(ControllerCentral contCent) {
		try {
			HashMap<String, Pessoa> pessoas = (HashMap<String, Pessoa>) lerObjeto("pessoas.bin");
			HashMap<String, Comissao> comissoes = (HashMap<String, Comissao>) lerObjeto("comissoes.bin");
			HashMap<String, Projeto> projetos = (HashMap<String, Projeto>) lerObjeto("projetos.bin");
			HashMap<Integer, Integer[]> anoProjetos = (HashMap<Integer, Integer[]>) lerObjeto("anoProjetos.bin");
			Set<String> partidos = (Set<String>) lerObjeto("partidos.bin");
			HashMap<String, Pessoa> deputados = (HashMap<String, Pessoa>)lerObjeto("deputados.bin");

			contCent.setParam(pessoas, comissoes, projetos, anoProjetos, partidos, deputados);

		} catch (IOException ioe) {
		} catch (ClassCastException cce) {
		} catch (ClassNotFoundException cnfe) {
		}
	}
	
	/**
	 * Salva um objeto passado como parametro em um arquivo, com o nome tambem passado como parametro.
	 * @param objeto Objeto a ser salvo.
	 * @param nomeArquivo String que representa o nome do arquivo.
	 * @throws FileNotFoundException Excecao que sera lancada caso o arquivo nao seja encontrado no caminho indicado.
	 * @throws IOException Excecao que sera lancada em caso de erro na gravacao do objeto.
	 */
	private static void salvarObjeto(Object objeto, String nomeArquivo) throws FileNotFoundException, IOException {
		ObjectOutputStream arqObjetos = null;
		try {
			arqObjetos = new ObjectOutputStream(new FileOutputStream("data" + File.separator + nomeArquivo));
			arqObjetos.writeObject(objeto);
		} finally {
			if (arqObjetos != null)
				arqObjetos.close();
		}
	}
	
	/**
	 * Le um arquivo, tranformando-o em objeto, e retorna o mesmo.
	 * @param nomeArquivo String que representa o nome do arquivo a ser convertido;
	 * @return Objeto convertido a ser retornado.
	 * @throws FileNotFoundException Excecao que sera lancada em caso do arquivo em questao nao ser encontrado.
	 * @throws IOException Excecao que sera lancada em caso de erro na conversao de arquivo.
	 * @throws ClassNotFoundException Excecao que sera lancada em caso da classe de cast nao existir.
	 */
	private static Object lerObjeto(String nomeArquivo)
			throws FileNotFoundException, IOException, ClassNotFoundException {
		ObjectInputStream arqObjetos = null;
		try {
			arqObjetos = new ObjectInputStream(new FileInputStream("data" + File.separator + nomeArquivo));
			return arqObjetos.readObject();
		} finally {
			if (arqObjetos != null)
				arqObjetos.close();
		}
	}
}