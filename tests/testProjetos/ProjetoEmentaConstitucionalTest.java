package testProjetos;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import entidades.Deputado;
import entidades.Pessoa;
import projetos.ProjetoEmentaConstitucional;

class ProjetoEmentaConstitucionalTest {

	ProjetoEmentaConstitucional pec;
	Pessoa deputado;
	
	
	@BeforeEach
	void setUp() {
		this.deputado = new Pessoa("Gabriel", "321312312-4", "PB", "Saude", "PT");
		this.deputado.setFuncao(new Deputado("23032004"));
		this.pec = new ProjetoEmentaConstitucional(deputado, 2019, "PEC 1/2019", "Altera investimentos na area da saude", 
				"saude", "www.gov.br/brasil/saude.html", "189,193", 1);
	}
	
	@Test
	void testToString() {
		assertEquals("Projeto de Emenda Constitucional - PEC 1/2019 - 321312312-4 - "
				+ "Altera investimentos na area da saude - 189, 193 - EM VOTACAO (CCJC)", this.pec.toString());
	}
	
	@Test
	void testVotaComissao() {
		assertFalse(this.pec.votarComissao(10, 4, "plenario"));
		assertTrue(this.pec.votarComissao(10, 6, "plenario"));
		assertEquals("EM VOTACAO (Plenario - 1o turno)", this.pec.getSituacao());
	}
	
	@Test
	void testVotaPlenario() {
		this.pec.votarComissao(20, 15, "plenario");
		assertTrue(this.pec.votarPlenario(16, 15, 10));
		assertEquals("EM VOTACAO (Plenario - 2o turno)", this.pec.getSituacao());
		assertTrue(this.pec.votarPlenario(18, 15, 15));
		assertEquals("APROVADO", this.pec.getSituacao());
	}
	
	@Test
	void testVotaPlenarioProjetoArquivado() {
		this.pec.votarComissao(20, 15, "plenario");
		assertFalse(this.pec.votarPlenario(23, 20, 5));
		assertEquals("ARQUIVADO", this.pec.getSituacao());
	}
	
	@Test
	void testGetLocal() {
		assertEquals("CCJC", this.pec.getLocalVerificador());
	}
	
	@Test
	void testValidaQuorum() {
		try {
			this.pec.validarQuorum(20, 4);
		}catch(IllegalArgumentException iae) {
			assertEquals("Erro ao votar proposta: quorum invalido" ,iae.getMessage());
		}
	}
	
	@Test
	void testGetLocalExcessao() {
		try {
			this.pec.votarComissao(10, 6, "plenario");
			this.pec.getLocalVerificador();
		}catch(IllegalArgumentException iae) {
			assertEquals("Erro ao votar proposta: proposta encaminhada ao plenario", iae.getMessage());
		}
		try {
			this.pec.votarPlenario(24, 20, 5);
			this.pec.getLocalVerificador();
		}catch (IllegalArgumentException iae) {
			assertEquals("Erro ao votar proposta: tramitacao encerrada", iae.getMessage());
		}
	}
}
