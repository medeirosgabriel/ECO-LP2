package testProjetos;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import entidades.Deputado;
import entidades.Pessoa;
import projetos.ProjetoLeiComplementar;

class ProjetoLeiComplementarTest {

	private Pessoa p1;
	private ProjetoLeiComplementar plc;
	private ProjetoLeiComplementar plc2;
	
	@BeforeEach
	void setUp() {
		this.p1 = new Pessoa("Gabriel", "111111111-0", "PB", "saude, educacao", "PMDB");
		this.p1.setFuncao(new Deputado("23042005"));
		this.plc = new ProjetoLeiComplementar(p1, 2016, "PLP 1/2016", "Ementa PLP", "saude, transporte", "http://example.com/semana_saude",  "5,6", 1);
		this.plc2 = new ProjetoLeiComplementar(p1, 2016, "PLP 2/2016", "Ementa PLP", "educacao, transporte", "http://example.com/semana_saude",  "9,6", 1);
	}
	
	@Test
	void votarComissao() {
		assertEquals("Projeto de Lei Complementar - PLP 1/2016 - 111111111-0 - Ementa PLP - 5, 6 - EM VOTACAO (CCJC)", this.plc.toString());
		assertTrue(this.plc.votarComissao(7, 5, "CTF"));
		assertEquals("Projeto de Lei Complementar - PLP 1/2016 - 111111111-0 - Ementa PLP - 5, 6 - EM VOTACAO (CTF)", this.plc.toString());
		assertTrue(this.plc.votarComissao(7, 5, "plenario"));
		assertEquals("Projeto de Lei Complementar - PLP 1/2016 - 111111111-0 - Ementa PLP - 5, 6 - EM VOTACAO (Plenario - 1o turno)", this.plc.toString());
	}
	
	@Test
	void votarPlenario() {
		assertEquals("Projeto de Lei Complementar - PLP 1/2016 - 111111111-0 - Ementa PLP - 5, 6 - EM VOTACAO (CCJC)", this.plc.toString());
		assertEquals("Projeto de Lei Complementar - PLP 2/2016 - 111111111-0 - Ementa PLP - 9, 6 - EM VOTACAO (CCJC)", this.plc2.toString());
		assertTrue(this.plc.votarComissao(7, 5, "CTF"));
		assertTrue(this.plc2.votarComissao(7, 5, "CTF"));
		assertEquals("Projeto de Lei Complementar - PLP 1/2016 - 111111111-0 - Ementa PLP - 5, 6 - EM VOTACAO (CTF)", this.plc.toString());
		assertEquals("Projeto de Lei Complementar - PLP 2/2016 - 111111111-0 - Ementa PLP - 9, 6 - EM VOTACAO (CTF)", this.plc2.toString());
		assertTrue(this.plc.votarComissao(7, 5, "plenario"));
		assertTrue(this.plc2.votarComissao(7, 5, "plenario"));
		assertEquals("Projeto de Lei Complementar - PLP 1/2016 - 111111111-0 - Ementa PLP - 5, 6 - EM VOTACAO (Plenario - 1o turno)", this.plc.toString());
		assertEquals("Projeto de Lei Complementar - PLP 2/2016 - 111111111-0 - Ementa PLP - 9, 6 - EM VOTACAO (Plenario - 1o turno)", this.plc2.toString());
		assertTrue(this.plc.votarPlenario(9, 7, 5));
		assertTrue(this.plc2.votarPlenario(9, 7, 5));
		assertEquals("Projeto de Lei Complementar - PLP 1/2016 - 111111111-0 - Ementa PLP - 5, 6 - EM VOTACAO (Plenario - 2o turno)", this.plc.toString());
		assertEquals("Projeto de Lei Complementar - PLP 2/2016 - 111111111-0 - Ementa PLP - 9, 6 - EM VOTACAO (Plenario - 2o turno)", this.plc2.toString());
		assertTrue(this.plc.votarPlenario(12, 10, 9));
		assertFalse(this.plc2.votarPlenario(12, 10, 3));
		assertEquals("Projeto de Lei Complementar - PLP 1/2016 - 111111111-0 - Ementa PLP - 5, 6 - APROVADO", this.plc.toString());
		assertEquals("Projeto de Lei Complementar - PLP 2/2016 - 111111111-0 - Ementa PLP - 9, 6 - ARQUIVADO", this.plc2.toString());
	}
	
	@Test
	void validarQuorum() {
		this.plc.validarQuorum(10, 6);
		try {
			this.plc.validarQuorum(10, 4);
		}catch(IllegalArgumentException iae) {
			assertEquals("Erro ao votar proposta: quorum invalido", iae.getMessage());
		}
	}
	
	@Test
	void getLocal() {
		assertEquals("Projeto de Lei Complementar - PLP 1/2016 - 111111111-0 - Ementa PLP - 5, 6 - EM VOTACAO (CCJC)", this.plc.toString());
		assertTrue(this.plc.votarComissao(7, 5, "CTF"));
		assertEquals("CTF", this.plc.getLocalVerificador());
	}
	
	@Test
	void getLocalPropostaEncaminhadaPlenario() {
		assertEquals("Projeto de Lei Complementar - PLP 1/2016 - 111111111-0 - Ementa PLP - 5, 6 - EM VOTACAO (CCJC)", this.plc.toString());
		assertTrue(this.plc.votarComissao(7, 5, "CTF"));
		assertEquals("CTF", this.plc.getLocalVerificador());
		assertEquals("Projeto de Lei Complementar - PLP 1/2016 - 111111111-0 - Ementa PLP - 5, 6 - EM VOTACAO (CTF)", this.plc.toString());
		assertTrue(this.plc.votarComissao(7, 5, "plenario"));
		assertEquals("Projeto de Lei Complementar - PLP 1/2016 - 111111111-0 - Ementa PLP - 5, 6 - EM VOTACAO (Plenario - 1o turno)", this.plc.toString());
		try {
			this.plc.getLocalVerificador();
		}catch(IllegalArgumentException iae) {
			assertEquals("Erro ao votar proposta: proposta encaminhada ao plenario", iae.getMessage());
		}
	}
	
	@Test
	void getLocalTramitacaoEncerrada() {
		assertEquals("Projeto de Lei Complementar - PLP 1/2016 - 111111111-0 - Ementa PLP - 5, 6 - EM VOTACAO (CCJC)", this.plc.toString());
		assertTrue(this.plc.votarComissao(7, 5, "CTF"));
		assertEquals("Projeto de Lei Complementar - PLP 1/2016 - 111111111-0 - Ementa PLP - 5, 6 - EM VOTACAO (CTF)", this.plc.toString());
		assertTrue(this.plc.votarComissao(7, 5, "plenario"));
		assertEquals("Projeto de Lei Complementar - PLP 1/2016 - 111111111-0 - Ementa PLP - 5, 6 - EM VOTACAO (Plenario - 1o turno)", this.plc.toString());
		assertTrue(this.plc.votarPlenario(9, 7, 5));
		assertEquals("Projeto de Lei Complementar - PLP 1/2016 - 111111111-0 - Ementa PLP - 5, 6 - EM VOTACAO (Plenario - 2o turno)", this.plc.toString());
		assertTrue(this.plc.votarPlenario(12, 10, 9));
		assertEquals("Projeto de Lei Complementar - PLP 1/2016 - 111111111-0 - Ementa PLP - 5, 6 - APROVADO", this.plc.toString());	
		try {
			this.plc.getLocalVerificador();
		}catch(IllegalArgumentException iae) {
			assertEquals("Erro ao votar proposta: tramitacao encerrada", iae.getMessage());
		}
	}
}
