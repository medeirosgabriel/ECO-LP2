package testProjetos;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import entidades.Deputado;
import entidades.Pessoa;
import projetos.ProjetoLei;

class ProjetoLeiTest {
	
	private Pessoa p1;
	private ProjetoLei plConc;
	private ProjetoLei plNConc;
	private ProjetoLei plNConc2;

	@BeforeEach
	void setUp() {
		this.p1 = new Pessoa("Gabriel", "111111111-0", "PB", "saude, educacao", "PMDB");
		this.p1.setFuncao(new Deputado("23042005"));
		this.plConc = new ProjetoLei(p1, 2016, "PL 1/2016", "Ementa PL Conclusiva", "saude,transporte", "http://example.com/semana_saude",  true, 12);
		this.plNConc = new ProjetoLei(p1, 2017, "PL 1/2017", "Ementa PL N Conclusiva", "educacao,transporte", "http://example.com/semana_saude",  false, 14);
		this.plNConc2 = new ProjetoLei(p1, 2017, "PL 2/2017", "Ementa PL N Conclusiva", "educacao,lazer", "http://example.com/semana_saude",  false, 13);
	}
	
	@Test
	void votarComissao() {
		assertEquals("Projeto de Lei - PL 1/2016 - 111111111-0 - Ementa PL Conclusiva - Conclusiva - EM VOTACAO (CCJC)", this.plConc.toString());
		assertEquals("Projeto de Lei - PL 1/2017 - 111111111-0 - Ementa PL N Conclusiva - EM VOTACAO (CCJC)", this.plNConc.toString());
		assertTrue(this.plConc.votarComissao(7, 5, "CTF"));
		assertTrue(this.plNConc.votarComissao(7, 5, "plenario"));
		assertEquals("Projeto de Lei - PL 1/2016 - 111111111-0 - Ementa PL Conclusiva - Conclusiva - EM VOTACAO (CTF)", this.plConc.toString());
		assertEquals("Projeto de Lei - PL 1/2017 - 111111111-0 - Ementa PL N Conclusiva - EM VOTACAO (Plenario)", this.plNConc.toString());
		assertTrue(this.plConc.votarComissao(7, 5, "-"));
		assertEquals("Projeto de Lei - PL 1/2016 - 111111111-0 - Ementa PL Conclusiva - Conclusiva - APROVADO", this.plConc.toString());
	}
	
	@Test
	void votarComissaoPLConclusivaProximoLocalPlenario() {
		assertEquals("Projeto de Lei - PL 1/2016 - 111111111-0 - Ementa PL Conclusiva - Conclusiva - EM VOTACAO (CCJC)", this.plConc.toString());
		try {
			this.plConc.votarComissao(7, 5, "plenario");
		}catch(IllegalArgumentException iae) {
			assertEquals("Erro ao votar proposta: proposta encaminhada ao plenario", iae.getMessage());
		}
		
	}
	
	@Test
	void votarComissaoPLEncaminhadaAoPlenario() {
		assertEquals("Projeto de Lei - PL 1/2017 - 111111111-0 - Ementa PL N Conclusiva - EM VOTACAO (CCJC)", this.plNConc.toString());
		assertTrue(this.plNConc.votarComissao(7, 5, "plenario"));
		try {
			this.plNConc.votarComissao(7, 5, "CTF");
		}catch(IllegalArgumentException iae) {
			assertEquals("Erro ao votar proposta: proposta encaminhada ao plenario", iae.getMessage());
		}
	}
	
	@Test
	void votarPlenario() {
		assertEquals("Projeto de Lei - PL 1/2017 - 111111111-0 - Ementa PL N Conclusiva - EM VOTACAO (CCJC)", this.plNConc.toString());
		assertEquals("Projeto de Lei - PL 2/2017 - 111111111-0 - Ementa PL N Conclusiva - EM VOTACAO (CCJC)", this.plNConc2.toString());
		assertTrue(this.plNConc.votarComissao(7, 5, "plenario"));
		assertTrue(this.plNConc2.votarComissao(6, 4, "plenario"));
		assertEquals("Projeto de Lei - PL 1/2017 - 111111111-0 - Ementa PL N Conclusiva - EM VOTACAO (Plenario)", this.plNConc.toString());
		assertEquals("Projeto de Lei - PL 2/2017 - 111111111-0 - Ementa PL N Conclusiva - EM VOTACAO (Plenario)", this.plNConc2.toString());
		assertTrue(this.plNConc.votarPlenario(10, 7, 5));
		assertFalse(this.plNConc2.votarPlenario(10, 7, 3));
		assertEquals("Projeto de Lei - PL 1/2017 - 111111111-0 - Ementa PL N Conclusiva - APROVADO", this.plNConc.toString());
		assertEquals("Projeto de Lei - PL 2/2017 - 111111111-0 - Ementa PL N Conclusiva - ARQUIVADO", this.plNConc2.toString());
		
	}
	
	@Test
	void votarPlenarioTramitacaoEmComissao() {
		assertEquals("Projeto de Lei - PL 1/2017 - 111111111-0 - Ementa PL N Conclusiva - EM VOTACAO (CCJC)", this.plNConc.toString());
		assertTrue(this.plNConc.votarComissao(7, 5, "CTF"));
		assertEquals("Projeto de Lei - PL 1/2017 - 111111111-0 - Ementa PL N Conclusiva - EM VOTACAO (CTF)", this.plNConc.toString());
		try {
			this.plNConc.votarPlenario(10, 7, 5);
		}catch(IllegalArgumentException iae) {
			assertEquals("Erro ao votar proposta: tramitacao em comissao", iae.getMessage());
		}
	}
	
	@Test
	void validarQuorum() {
		this.plConc.validarQuorum(32, 20);
		try {
			this.plConc.validarQuorum(31, 9);
		}catch (IllegalArgumentException iae){
			assertEquals("Erro ao votar proposta: quorum invalido", iae.getMessage());
		}
	}
	
	@Test
	void getLocal() {
		assertEquals("Projeto de Lei - PL 1/2017 - 111111111-0 - Ementa PL N Conclusiva - EM VOTACAO (CCJC)", this.plNConc.toString());
		assertTrue(this.plNConc.votarComissao(7, 5, "CTF"));
	}
	
	@Test
	void getLocalNoPlenario() {
		assertEquals("Projeto de Lei - PL 1/2017 - 111111111-0 - Ementa PL N Conclusiva - EM VOTACAO (CCJC)", this.plNConc.toString());
		assertTrue(this.plNConc.votarComissao(7, 5, "plenario"));
		assertEquals("Projeto de Lei - PL 1/2017 - 111111111-0 - Ementa PL N Conclusiva - EM VOTACAO (Plenario)", this.plNConc.toString());
		try {
			this.plNConc.getLocalVerificador();
		}catch(IllegalArgumentException iae) {
			assertEquals("Erro ao votar proposta: proposta encaminhada ao plenario", iae.getMessage());
		}
	}
	
	@Test
	void getLocalTramitacaoEncerrada() {
		assertEquals("Projeto de Lei - PL 1/2017 - 111111111-0 - Ementa PL N Conclusiva - EM VOTACAO (CCJC)", this.plNConc.toString());
		assertTrue(this.plNConc.votarComissao(7, 5, "plenario"));
		assertEquals("Projeto de Lei - PL 1/2017 - 111111111-0 - Ementa PL N Conclusiva - EM VOTACAO (Plenario)", this.plNConc.toString());
		assertTrue(this.plNConc.votarPlenario(10, 7, 5));
		try {
			this.plNConc.getLocalVerificador();
		}catch(IllegalArgumentException iae) {
			assertEquals("Erro ao votar proposta: tramitacao encerrada", iae.getMessage());
		}
	}
}
