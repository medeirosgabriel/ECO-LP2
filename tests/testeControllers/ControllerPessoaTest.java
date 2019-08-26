package testeControllers;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import controllers.ControllerPessoa;
import estrategias.EstrategiaAprovacao;
import estrategias.EstrategiaConclusao;
import estrategias.EstrategiaConstitucional;

class ControllerPessoaTest {
	
	private ControllerPessoa contPessoa;
	
	@BeforeEach
	void constroiControllerPessoa() {
		this.contPessoa = new ControllerPessoa();
	}
	
	@Test
	void cadastraPessoa() {
		this.contPessoa.cadastrarPessoa("Gabriel", "543534543-8", "MG", "Saude");
		assertTrue(this.contPessoa.verificaExistenciaPessoa("543534543-8"));
	}
	
	@Test
	void cadastraPessoaExistente() {
		this.contPessoa.cadastrarPessoa("Gabriel", "543534543-8", "MG", "Saude");
		try {
			this.contPessoa.cadastrarPessoa("Gabriel", "543534543-8", "MG", "Saude");
		}catch(IllegalArgumentException iae) {
			assertEquals("Erro ao cadastrar pessoa: dni ja cadastrado", iae.getMessage());
		}
	}
	
	@Test
	void verificaPartido() {
		this.contPessoa.cadastrarPessoa("Gabriel", "543534543-8", "MG", "Saude", "PSDB");
		this.contPessoa.verificaPartidoPessoa("543534543-8", "Possui Partido");
	}
	
	@Test
	void verificaPartidoPessoaSemPartido() {
		this.contPessoa.cadastrarPessoa("Tadeu", "543432434-8", "MG", "Saude");
		try {
			this.contPessoa.verificaPartidoPessoa("543432434-8", "Nao Possui Partido");
		}catch(IllegalArgumentException iae) {
			assertEquals("Nao Possui Partido", iae.getMessage());
		}
	}
	
	@Test
	void exibePessoa() {
		this.contPessoa.cadastrarPessoa("Gabriel", "543534543-8", "MG", "Saude", "PSDB");
		assertEquals("Gabriel - 543534543-8 (MG) - PSDB - Interesses: Saude", this.contPessoa.exibirPessoa("543534543-8"));
		this.contPessoa.cadastrarDeputado("543534543-8", "23032005");
		assertEquals("POL: Gabriel - 543534543-8 (MG) - PSDB - Interesses: Saude - 23/03/2005 - 0 Leis", this.contPessoa.exibirPessoa("543534543-8"));
		this.contPessoa.fazerLei("543534543-8");
		assertEquals("POL: Gabriel - 543534543-8 (MG) - PSDB - Interesses: Saude - 23/03/2005 - 1 Leis", this.contPessoa.exibirPessoa("543534543-8"));	
	}
	
	@Test
	void testExibePessoaInexistente() {
		try {
			this.contPessoa.exibirPessoa("123456789-4");
		}catch (IllegalArgumentException iae) {
			assertEquals("Erro ao exibir pessoa: pessoa nao encontrada", iae.getMessage());
		}
	}
	
	@Test
	void cadastraDeputado() {
		this.contPessoa.cadastrarPessoa("Gabriel", "543534543-8", "MG", "Saude", "PSDB");
		this.contPessoa.cadastrarDeputado("543534543-8", "23032005");
	}
	
	@Test
	void cadastraDeputadoPessoaSemPartido() {
		this.contPessoa.cadastrarPessoa("Gabriel", "543534543-8", "MG", "Saude");
		try {
			this.contPessoa.cadastrarDeputado("543534543-8", "23032005");
		}catch(IllegalArgumentException iae) {
			assertEquals("Erro ao cadastrar deputado: pessoa sem partido", iae.getMessage());
		}
	}
	
	@Test
	void configurarEstrategiaProposta() {
		this.contPessoa.cadastrarPessoa("Gabriel", "543534543-8", "MG", "Saude");
		assertTrue(this.contPessoa.retornaPessoa("543534543-8").getEstrategia() instanceof EstrategiaConstitucional);
		this.contPessoa.configurarEstrategiaPropostaRelacionada("543534543-8", "CONCLUSAO");
		assertTrue(this.contPessoa.retornaPessoa("543534543-8").getEstrategia() instanceof EstrategiaConclusao);
		this.contPessoa.configurarEstrategiaPropostaRelacionada("543534543-8", "APROVACAO");
		assertTrue(this.contPessoa.retornaPessoa("543534543-8").getEstrategia() instanceof EstrategiaAprovacao);
		this.contPessoa.configurarEstrategiaPropostaRelacionada("543534543-8", "CONSTITUCIONAL");
		assertTrue(this.contPessoa.retornaPessoa("543534543-8").getEstrategia() instanceof EstrategiaConstitucional);
	}
	
	@Test
	void testConfiguraEstrategiaPessoaInexistente() {
		try {
			this.contPessoa.configurarEstrategiaPropostaRelacionada("543534543-8", "APROVACAO");
		}catch (IllegalArgumentException iae) {
			assertEquals("Pessoa nao inexistente", iae.getMessage());
		}
	}
}