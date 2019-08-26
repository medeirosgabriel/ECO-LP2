package testeControllers;

import static org.junit.jupiter.api.Assertions.*;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import controllers.ControllerVotacao;
import entidades.Comissao;
import entidades.Deputado;
import entidades.Pessoa;
import projetos.Projeto;
import projetos.ProjetoEmentaConstitucional;
import projetos.ProjetoLei;
import projetos.ProjetoLeiComplementar;

class ControllerVotacaoTest {
	
	private HashMap<String, Comissao> comissoes;
	private Set<String> baseGoverno;
	private HashMap<String, Projeto> projetos;
	private ControllerVotacao contVot;

	@BeforeEach
	void setUp() {

		//DEPUTADOS
		Pessoa p1 = new Pessoa("Gabriel", "111111111-0", "PB", "saude, educacao", "PMDB");
		p1.setFuncao(new Deputado("23042005"));
		Pessoa p2 = new Pessoa("Daniel", "222222222-0", "RN", "saude, educacao", "PT");
		p2.setFuncao(new Deputado("25072011"));
		Pessoa p3 = new Pessoa("Tadeu", "333333333-0", "PI", "transporte", "PSL");
		p3.setFuncao(new Deputado("13102012"));
		Pessoa p4 = new Pessoa("Junior", "444444444-0", "SE", "saude, transporte, educacao", "PSOL");
		p4.setFuncao(new Deputado("23092018"));
		Pessoa p5 = new Pessoa("Lucas", "555555555-0", "RJ", "lazer, cultura", "PCB");
		p5.setFuncao(new Deputado("03042002"));
		Pessoa p6 = new Pessoa("Luiz", "666666666-0", "SP", "agropecuaria, cultura", "PTB");
		p6.setFuncao(new Deputado("23052001"));
		Pessoa p7 = new Pessoa("Luiza", "7777777777-0", "SP", "agropecuaria, cultura", "PLS");
		p6.setFuncao(new Deputado("22052011"));
		
		//COMISSOES
		this.comissoes = new HashMap<>();
		String[] ccjc = {"111111111-0", "222222222-0", "333333333-0", "444444444-0"};
		String[] ctf = {"111111111-0", "222222222-0", "333333333-0", "444444444-0"};
		String[] cfr = {"111111111-0", "222222222-0", "555555555-0", "666666666-0"};
		String[] cbr = {"333333333-0", "444444444-0", "555555555-0", "222222222-0"};
		this.comissoes.put("CCJC", new Comissao("CCJC", ccjc));
		this.comissoes.put("CTF", new Comissao("CTF", ctf));
		this.comissoes.put("CFR", new Comissao("CFR", cfr));
		this.comissoes.put("CBR", new Comissao("CBR", cbr));
		
		//BASE GOVERNISTA
		this.baseGoverno = new HashSet<>();
		this.baseGoverno.add("PMDB");
		this.baseGoverno.add("PT");
		this.baseGoverno.add("PSL");
		this.baseGoverno.add("PLS");
		
		//PROJETOS
		this.projetos = new HashMap<>();
		this.projetos.put("PL 1/2016", new ProjetoLei(p1, 2016, "PL 1/2016", "Ementa PL", "saude, transporte", "http://example.com/semana_saude",  true, 1));
		this.projetos.put("PL 2/2016", new ProjetoLei(p1, 2016, "PL 2/2016", "Ementa PL", "saude, educacao", "http://example.com/semana_saude",  false, 2));
		this.projetos.put("PEC 1/2016", new ProjetoEmentaConstitucional(p2, 2016, "PEC 1/2016", "Ementa PEC", "transporte, cultura", "http://example.com/semana_saude", "1,8", 3));
		this.projetos.put("PLC 1/2016", new ProjetoLeiComplementar(p3, 2016, "PLC 1/2016", "Ementa PLC", "lazer, agropecuaria", "http://example.com/semana_saude", "1,8", 4));
		
		//ADICIONANDO OS DEPUTADOS
		this.contVot = new ControllerVotacao(this.comissoes, this.baseGoverno, this.projetos);
		this.contVot.adicionaDeputado("111111111-0", p1);
		this.contVot.adicionaDeputado("222222222-0", p2);
		this.contVot.adicionaDeputado("333333333-0", p3);
		this.contVot.adicionaDeputado("444444444-0", p4);
		this.contVot.adicionaDeputado("555555555-0", p5);
		this.contVot.adicionaDeputado("666666666-0", p6);
		this.contVot.adicionaDeputado("777777777-0", p7);
	}
	
	@Test
	void votarComissao() {
		assertTrue(this.contVot.votarComissao("PL 1/2016", "GOVERNISTA", "CFR"));
		assertEquals("Projeto de Lei - PL 1/2016 - 111111111-0 - Ementa PL - Conclusiva - EM VOTACAO (CFR)", this.projetos.get("PL 1/2016").toString());
		assertFalse(this.contVot.votarComissao("PL 1/2016", "OPOSICAO", "CBR"));
		assertEquals("Projeto de Lei - PL 1/2016 - 111111111-0 - Ementa PL - Conclusiva - ARQUIVADO", this.projetos.get("PL 1/2016").toString());
		assertTrue(this.contVot.votarComissao("PL 2/2016", "LIVRE", "CTF"));
		assertEquals("Projeto de Lei - PL 2/2016 - 111111111-0 - Ementa PL - EM VOTACAO (CTF)", this.projetos.get("PL 2/2016").toString());
		assertFalse(this.contVot.votarComissao("PL 2/2016", "OPOSICAO", "CFR"));
		assertEquals("Projeto de Lei - PL 2/2016 - 111111111-0 - Ementa PL - EM VOTACAO (CFR)", this.projetos.get("PL 2/2016").toString());
		assertTrue(this.contVot.votarComissao("PEC 1/2016", "GOVERNISTA", "CBR"));
		assertEquals("Projeto de Emenda Constitucional - PEC 1/2016 - 222222222-0 - Ementa PEC - 1, 8 - EM VOTACAO (CBR)", this.projetos.get("PEC 1/2016").toString());
		assertFalse(this.contVot.votarComissao("PEC 1/2016", "OPOSICAO", "CTF"));
		assertEquals("Projeto de Emenda Constitucional - PEC 1/2016 - 222222222-0 - Ementa PEC - 1, 8 - EM VOTACAO (CTF)", this.projetos.get("PEC 1/2016").toString());
		assertTrue(this.contVot.votarComissao("PLC 1/2016", "GOVERNISTA", "CBR"));
		assertEquals("Projeto de Lei Complementar - PLC 1/2016 - 333333333-0 - Ementa PLC - 1, 8 - EM VOTACAO (CBR)", this.projetos.get("PLC 1/2016").toString());
		assertFalse(this.contVot.votarComissao("PLC 1/2016", "OPOSICAO", "CBR"));
		assertEquals("Projeto de Lei Complementar - PLC 1/2016 - 333333333-0 - Ementa PLC - 1, 8 - EM VOTACAO (CBR)", this.projetos.get("PLC 1/2016").toString());
	}
	
	@Test
	void votarComissaoPLConclusivaPlenarioComoDestino() {
		assertTrue(this.contVot.votarComissao("PL 1/2016", "GOVERNISTA", "CFR"));
		assertEquals("Projeto de Lei - PL 1/2016 - 111111111-0 - Ementa PL - Conclusiva - EM VOTACAO (CFR)", this.projetos.get("PL 1/2016").toString());
		try {
			this.contVot.votarComissao("PL 1/2016", "LIVRE", "plenario");
		}catch(IllegalArgumentException iae) {
			assertEquals("Erro ao votar proposta: proposta encaminhada ao plenario", iae.getMessage());
		}
	}
	
	@Test
	void votarComissaoTramitacaoEncerrada() {
		assertTrue(this.contVot.votarComissao("PL 1/2016", "GOVERNISTA", "CFR"));
		assertEquals("Projeto de Lei - PL 1/2016 - 111111111-0 - Ementa PL - Conclusiva - EM VOTACAO (CFR)", this.projetos.get("PL 1/2016").toString());
		assertFalse(this.contVot.votarComissao("PL 1/2016", "OPOSICAO", "CBR"));
		assertEquals("Projeto de Lei - PL 1/2016 - 111111111-0 - Ementa PL - Conclusiva - ARQUIVADO", this.projetos.get("PL 1/2016").toString());
		try {
			this.contVot.votarComissao("PL 1/2016", "LIVRE", "CTF");
		}catch(IllegalArgumentException iae) {
			assertEquals("Erro ao votar proposta: tramitacao encerrada", iae.getMessage());
		}
	}
	
	@Test
	void votarPlenario() {
		assertTrue(this.contVot.votarComissao("PL 2/2016", "LIVRE", "plenario"));
		assertEquals("Projeto de Lei - PL 2/2016 - 111111111-0 - Ementa PL - EM VOTACAO (Plenario)", this.projetos.get("PL 2/2016").toString());
		assertTrue(this.contVot.votarPlenario("PL 2/2016", "LIVRE", "111111111-0,222222222-0,333333333-0,444444444-0"));
		assertEquals("Projeto de Lei - PL 2/2016 - 111111111-0 - Ementa PL - APROVADO", this.projetos.get("PL 2/2016").toString());
		assertTrue(this.contVot.votarComissao("PEC 1/2016", "GOVERNISTA", "plenario"));
		assertEquals("Projeto de Emenda Constitucional - PEC 1/2016 - 222222222-0 - Ementa PEC - 1, 8 - EM VOTACAO (Plenario - 1o turno)", this.projetos.get("PEC 1/2016").toString());
		assertFalse(this.contVot.votarPlenario("PEC 1/2016", "GOVERNISTA", "111111111-0,222222222-0,333333333-0,444444444-0,555555555-0"));
		assertEquals("Projeto de Emenda Constitucional - PEC 1/2016 - 222222222-0 - Ementa PEC - 1, 8 - ARQUIVADO", this.projetos.get("PEC 1/2016").toString());
		assertTrue(this.contVot.votarComissao("PLC 1/2016", "GOVERNISTA", "plenario"));
		assertEquals("Projeto de Lei Complementar - PLC 1/2016 - 333333333-0 - Ementa PLC - 1, 8 - EM VOTACAO (Plenario - 1o turno)", this.projetos.get("PLC 1/2016").toString());
		assertTrue(this.contVot.votarPlenario("PLC 1/2016", "GOVERNISTA", "111111111-0,222222222-0,333333333-0,777777777-0"));
		assertEquals("Projeto de Lei Complementar - PLC 1/2016 - 333333333-0 - Ementa PLC - 1, 8 - EM VOTACAO (Plenario - 2o turno)", this.projetos.get("PLC 1/2016").toString());
		assertTrue(this.contVot.votarPlenario("PLC 1/2016", "GOVERNISTA", "111111111-0,222222222-0,333333333-0,777777777-0"));
		assertEquals("Projeto de Lei Complementar - PLC 1/2016 - 333333333-0 - Ementa PLC - 1, 8 - APROVADO", this.projetos.get("PLC 1/2016").toString());
	}
	
	@Test
	void votarPlenarioProjetoEmComissao() {
		try {
			this.contVot.votarPlenario("PL 2/2016", "LIVRE", "111111111-0,222222222-0,333333333-0,444444444-0");
		}catch(IllegalArgumentException iae) {
			assertEquals("Erro ao votar proposta: tramitacao em comissao", iae.getMessage());
		}
	}
	
	@Test
	void votarPlenarioTramitacaoEncerrada() {
		assertTrue(this.contVot.votarComissao("PLC 1/2016", "GOVERNISTA", "plenario"));
		assertEquals("Projeto de Lei Complementar - PLC 1/2016 - 333333333-0 - Ementa PLC - 1, 8 - EM VOTACAO (Plenario - 1o turno)", this.projetos.get("PLC 1/2016").toString());
		assertTrue(this.contVot.votarPlenario("PLC 1/2016", "GOVERNISTA", "111111111-0,222222222-0,333333333-0,777777777-0"));
		assertEquals("Projeto de Lei Complementar - PLC 1/2016 - 333333333-0 - Ementa PLC - 1, 8 - EM VOTACAO (Plenario - 2o turno)", this.projetos.get("PLC 1/2016").toString());
		assertTrue(this.contVot.votarPlenario("PLC 1/2016", "GOVERNISTA", "111111111-0,222222222-0,333333333-0,777777777-0"));
		assertEquals("Projeto de Lei Complementar - PLC 1/2016 - 333333333-0 - Ementa PLC - 1, 8 - APROVADO", this.projetos.get("PLC 1/2016").toString());
		try {
			this.contVot.votarPlenario("PLC 1/2016", "GOVERNISTA", "111111111-0,222222222-0,333333333-0,777777777-0");
		}catch(IllegalArgumentException iae) {
			assertEquals("Erro ao votar proposta: tramitacao encerrada", iae.getMessage());
		}
	}
	
	@Test
	void votarPlenarioQuorumInvalido() {
		try {
			this.contVot.votarPlenario("PLC 1/2016", "GOVERNISTA", "111111111-0,222222222-0,333333333-0,777777777-0");
		}catch(IllegalArgumentException iae) {
			assertEquals("Erro ao votar proposta: tramitacao encerrada", iae.getMessage());
		}
	}
	
	@Test
	void procuraProjeto() {
		this.contVot.procuraProjeto("PLC 1/2016", "Projeto Nao Encontrado");
		try {
			this.contVot.procuraProjeto("PLC 5/2016", "Projeto Nao Encontrado");
		}catch(IllegalArgumentException iae) {
			assertEquals("Projeto Nao Encontrado", iae.getMessage());
		}
	}
	
	@Test
	void validarStatus() {
		this.contVot.validarStatus("GOVERNISTA", "Status Invalido");
		try {
			this.contVot.validarStatus("A FAVOR", "Status Invalido");
		}catch(IllegalArgumentException iae) {
			assertEquals("Status Invalido", iae.getMessage());
		}
	}
}
