package testesEstrategias;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import controllers.ControllerCentral;

class EstrategiaConstitucionalTest {
	
	private ControllerCentral contCentral;

	@BeforeEach
	void setUp() {
		
		this.contCentral = new ControllerCentral();
		this.contCentral.cadastrarPessoa("Gabriel", "111111111-0", "PB", "saude,seguranca", "PSOL");
		this.contCentral.cadastrarPessoa("Tadeu", "222222222-0", "RO", "trabalho", "PT");
		this.contCentral.cadastrarPessoa("Daniel", "333333333-0", "AL", "educacao,seguranca", "PMDB");
		this.contCentral.cadastrarPessoa("Davi", "444444444-0", "PA", "transporte", "PTDB");
		this.contCentral.cadastrarDeputado("111111111-0", "23032000");
		this.contCentral.cadastrarDeputado("222222222-0", "25062015");
		this.contCentral.cadastrarDeputado("333333333-0", "23041993");
		this.contCentral.cadastrarDeputado("444444444-0", "24051997");
	}
	
	@Test
	void pegarPropostaRelacionadaNormal() {
		this.contCentral.cadastrarPL("111111111-0", 2016, "PL", "lazer,seguranca", "https://docs.google.com/document", true);
		this.contCentral.cadastrarPL("111111111-0", 2016, "PL", "educacao,trabalho", "https://docs.google.com/document", true);
		this.contCentral.cadastrarPL("111111111-0", 2016, "PLP", "saude", "https://docs.google.com/document", true);
		this.contCentral.cadastrarPEC("222222222-0", 2017, "PEC", "esporte", "https://docs.google.com/document", "6,3");
		assertEquals("PL 1/2016", this.contCentral.pegarPropostaRelacionada("111111111-0"));
		assertEquals("PL 2/2016", this.contCentral.pegarPropostaRelacionada("222222222-0"));
		assertEquals("PL 1/2016", this.contCentral.pegarPropostaRelacionada("333333333-0"));
		assertEquals("", this.contCentral.pegarPropostaRelacionada("444444444-0"));
		this.contCentral.cadastrarPL("333333333-0", 2013, "PL", "saude,seguranca", "https://docs.google.com/document", true);
		this.contCentral.cadastrarPLP("333333333-0", 2014, "PLP", "educacao,seguranca", "https://docs.google.com/document", "6,3");
		assertEquals("PL 1/2013", this.contCentral.pegarPropostaRelacionada("111111111-0"));
		assertEquals("PLP 1/2014", this.contCentral.pegarPropostaRelacionada("333333333-0"));
	}
	

	@Test
	void pegarPropostaRelacionadaCom1Empate() {
		this.contCentral.cadastrarPL("111111111-0", 2012, "PL", "lazer,seguranca", "https://docs.google.com/document", true);
		this.contCentral.cadastrarPL("111111111-0", 2013, "PL", "educacao,trabalho", "https://docs.google.com/document", true);
		this.contCentral.cadastrarPLP("111111111-0", 2014, "PLP", "saude,trabalho", "https://docs.google.com/document", "6,3");
		this.contCentral.cadastrarPLP("111111111-0", 2016, "PLP", "transporte,saude", "https://docs.google.com/document", "6,3");
		this.contCentral.cadastrarPEC("222222222-0", 2017, "PEC", "trabalho,esporte", "https://docs.google.com/document", "6,3");
		this.contCentral.cadastrarPEC("222222222-0", 2017, "PEC", "educacao,esporte", "https://docs.google.com/document", "6,3");
		assertEquals("PLP 1/2014", this.contCentral.pegarPropostaRelacionada("111111111-0"));
		assertEquals("PEC 1/2017", this.contCentral.pegarPropostaRelacionada("222222222-0"));
		assertEquals("PEC 2/2017", this.contCentral.pegarPropostaRelacionada("333333333-0"));
		assertEquals("PLP 1/2016", this.contCentral.pegarPropostaRelacionada("444444444-0"));
	}
	
	@Test
	void pegarPropostaRelacionadaCom2Empates() {
		this.contCentral.cadastrarPL("111111111-0", 2012, "PL", "saude,seguranca", "https://docs.google.com/document", true);
		this.contCentral.cadastrarPL("111111111-0", 2013, "PL", "trabalho", "https://docs.google.com/document", true);
		this.contCentral.cadastrarPLP("111111111-0", 2014, "PLP", "educacaco,seguranca", "https://docs.google.com/document", "6,3");
		this.contCentral.cadastrarPLP("111111111-0", 2016, "PLP", "transporte,saude", "https://docs.google.com/document", "6,3");
		this.contCentral.cadastrarPEC("111111111-0", 2016, "PLP", "transporte", "https://docs.google.com/document", "6,3");
		this.contCentral.cadastrarPEC("222222222-0", 2017, "PEC", "trabalho,esporte", "https://docs.google.com/document", "6,3");
		assertEquals("PL 1/2012", this.contCentral.pegarPropostaRelacionada("111111111-0"));
		assertEquals("PEC 1/2017", this.contCentral.pegarPropostaRelacionada("222222222-0"));
		assertEquals("PLP 1/2014", this.contCentral.pegarPropostaRelacionada("333333333-0"));
		assertEquals("PEC 1/2016", this.contCentral.pegarPropostaRelacionada("444444444-0"));
		this.contCentral.cadastrarPL("111111111-0", 2012, "PLP", "saude,seguranca", "https://docs.google.com/document", true);
		this.contCentral.cadastrarPLP("111111111-0", 2010, "PLP", "transporte,saude", "https://docs.google.com/document", "6,3");
		this.contCentral.cadastrarPEC("111111111-0", 2016, "PLP", "transporte", "https://docs.google.com/document", "6,3");
		assertEquals("PL 1/2012", this.contCentral.pegarPropostaRelacionada("111111111-0"));
		assertEquals("PEC 1/2017", this.contCentral.pegarPropostaRelacionada("222222222-0"));
		assertEquals("PLP 1/2014", this.contCentral.pegarPropostaRelacionada("333333333-0"));
		assertEquals("PEC 1/2016", this.contCentral.pegarPropostaRelacionada("444444444-0"));
	}
}
