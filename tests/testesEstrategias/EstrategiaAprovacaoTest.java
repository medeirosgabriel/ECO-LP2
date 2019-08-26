package testesEstrategias;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import controllers.ControllerCentral;

class EstrategiaAprovacaoTest {

	private ControllerCentral contCentral;

	@BeforeEach
	void setUp() {
		
		this.contCentral = new ControllerCentral();
		this.contCentral.cadastrarPessoa("Gabriel", "111111111-0", "PB", "saude,trabalho", "PSOL");
		this.contCentral.cadastrarPessoa("Tadeu", "222222222-0", "RO", "saude,seguranca", "PT");
		this.contCentral.cadastrarPessoa("Daniel", "333333333-0", "AL", "educacao,seguranca", "PMDB");
		this.contCentral.cadastrarPessoa("Mauricio", "444444444-0", "SE", "educacao,trabalho", "PSL");
		this.contCentral.cadastrarPessoa("Helena", "555555555-0", "RR", "saude,educacao", "PSDB");
		this.contCentral.cadastrarPessoa("Bruna", "666666666-0", "PI", "trabalho,saude", "PSL");
		this.contCentral.cadastrarPessoa("Brenda", "777777777-0", "PI", "trabalho,lazer", "PMDB");
		this.contCentral.cadastrarDeputado("111111111-0", "23032000");
		this.contCentral.cadastrarDeputado("222222222-0", "25062015");
		this.contCentral.cadastrarDeputado("333333333-0", "23041993");
		this.contCentral.cadastrarDeputado("444444444-0", "31032000");
		this.contCentral.cadastrarDeputado("555555555-0", "25072000");
		this.contCentral.cadastrarDeputado("666666666-0", "24052002");
		this.contCentral.cadastrarDeputado("777777777-0", "24052002");
		this.contCentral.cadastrarComissao("CCJC", "111111111-0,222222222-0,555555555-0"); // 2 a favor 1 oposicao
		this.contCentral.cadastrarComissao("CTF", "111111111-0,555555555-0,666666666-0"); // 1 a favor 2 oposicao
		this.contCentral.cadastrarComissao("CET", "111111111-0,222222222-0,333333333-0"); // todos a favor
		this.contCentral.cadastrarComissao("COE", "444444444-0,555555555-0,666666666-0"); //todos da oposicao
		this.contCentral.cadastrarPartido("PSOL");
		this.contCentral.cadastrarPartido("PT");
		this.contCentral.cadastrarPartido("PMDB");
		this.contCentral.cadastrarPL("111111111-0", 2016, "PL", "saude,trabalho", "https://docs.google.com/document", true);
		this.contCentral.cadastrarPL("111111111-0", 2016, "PL", "saude,seguranca", "https://docs.google.com/document", true);
		this.contCentral.cadastrarPL("222222222-0", 2013, "PL", "educacao,seguranca", "https://docs.google.com/document", false);
		this.contCentral.cadastrarPLP("333333333-0", 2013, "PLP", "educacao,trabalho", "https://docs.google.com/document", "1,4"); //
		this.contCentral.cadastrarPLP("222222222-0", 2016, "PLP", "educacao,trabalho", "https://docs.google.com/document", "3,5"); //
		this.contCentral.cadastrarPEC("555555555-0", 2017, "PEC", "educacao,trabalho", "https://docs.google.com/document", "7,8"); 
		this.contCentral.configurarEstrategiaPropostaRelacionada("111111111-0", "APROVACAO");
		this.contCentral.configurarEstrategiaPropostaRelacionada("222222222-0", "APROVACAO");
		this.contCentral.configurarEstrategiaPropostaRelacionada("333333333-0", "APROVACAO");
		this.contCentral.configurarEstrategiaPropostaRelacionada("444444444-0", "APROVACAO");
		
	}
	
	@Test
	void pegarPropostaRelacionada() {
		assertEquals("PL 1/2016", this.contCentral.pegarPropostaRelacionada("111111111-0"));
		assertEquals("PL 2/2016", this.contCentral.pegarPropostaRelacionada("222222222-0"));
		assertEquals("PL 1/2013", this.contCentral.pegarPropostaRelacionada("333333333-0"));
	}
	

	@Test
	void pegarPropostaRelacionadaCom1Empate() {
		assertTrue(this.contCentral.votarComissao("PLP 1/2013","GOVERNISTA", "CTF"));
		assertEquals("PLP 1/2013", this.contCentral.pegarPropostaRelacionada("444444444-0"));
		assertTrue(this.contCentral.votarComissao("PLP 1/2016", "GOVERNISTA", "CET"));
		assertTrue(this.contCentral.votarComissao("PLP 1/2016", "GOVERNISTA", "plenario"));
		assertEquals("PLP 1/2016", this.contCentral.pegarPropostaRelacionada("444444444-0"));
		assertTrue(this.contCentral.votarComissao("PLP 1/2013","OPOSICAO", "plenario"));
		assertTrue(this.contCentral.votarPlenario("PLP 1/2013","GOVERNISTA", "111111111-0,222222222-0,333333333-0,777777777-0"));
		assertEquals("PLP 1/2013", this.contCentral.pegarPropostaRelacionada("444444444-0"));
	}
	
	@Test
	void pegarPropostaRelacionadaCom2Empates() {
		this.contCentral.votarComissao("PLP 1/2013","GOVERNISTA", "CTF");
		this.contCentral.votarComissao("PLP 1/2016","GOVERNISTA", "CTF");
		assertEquals("PLP 1/2013", this.contCentral.pegarPropostaRelacionada("444444444-0"));
	}
}
