package testeControllers;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import controllers.ControllerProjetos;
import entidades.Deputado;
import entidades.Pessoa;
import estrategias.EstrategiaConclusao;
import projetos.ProjetoLei;

class ControllerProjetosTest {
	
	private ControllerProjetos contproj;
	private Pessoa p1;
	private Pessoa p2;
	
	@BeforeEach
	void setUp() {
		this.p1 = new Pessoa("Gabriel", "111111111-0", "PB", "saude,educacao", "PMDB");
		this.p1.setFuncao(new Deputado("23042005"));
		this.p2 = new Pessoa("Daniel", "222222222-0", "RN", "saude,educacao", "PT");
		this.p2.setFuncao(new Deputado("25072011"));
		this.contproj = new ControllerProjetos();
	}

	@Test
	void cadastrarPL() {
		assertEquals("PL 1/2018", this.contproj.cadastrarPL(p1, 2018, "Ementa PL", "saude,educacao, lazer", "http://example.com/semana_saude", true));
		assertEquals("PL 1/2016", this.contproj.cadastrarPL(p2, 2016, "Ementa PL", "saude, lazer, cultura", "http://example.com/semana_saude", false));
	}
	
	@Test
	void cadastrarPLP() {
		assertEquals("PLP 1/2017", this.contproj.cadastrarPLP(p1, 2017, "Ementa PLP", "transporte,lazer", "http://example.com/semana_saude", "8,5"));
		assertEquals("PLP 2/2017", this.contproj.cadastrarPLP(p2, 2017, "Ementa PLP", "saude,lazer", "http://example.com/semana_saude", "6,7"));
	}
	
	@Test
	void cadastrarPEC() {
		assertEquals("PEC 1/2006", this.contproj.cadastrarPEC(p1, 2006, "Ementa PEC", "cultura,transporte", "http://example.com/semana_saude", "13,5"));
		assertEquals("PEC 1/2005", this.contproj.cadastrarPEC(p2, 2005, "Ementa PEC", "transporte,educacao", "http://example.com/semana_saude", "45,8"));
	}
	
	@Test
	void procuraDniDeputadoProjeto() {
		assertEquals("PEC 1/2006", this.contproj.cadastrarPEC(p1, 2006, "Ementa PEC", "cultura,transporte", "http://example.com/semana_saude", "13,5"));
		assertEquals("111111111-0", this.contproj.procuraDniDeputadoProjeto("PEC 1/2006"));
	}
	
	@Test
	void exibirProjetoETramitacao() {
		assertEquals("PL 1/2018", this.contproj.cadastrarPL(p1, 2018, "Ementa PL", "saude,educacao, lazer", "http://example.com/semana_saude", true));
		assertEquals("Projeto de Lei - PL 1/2018 - 111111111-0 - Ementa PL - Conclusiva - EM VOTACAO (CCJC)", this.contproj.exibirProjeto("PL 1/2018"));
		assertEquals("PL 1/2016", this.contproj.cadastrarPL(p2, 2016, "Ementa PL", "saude, lazer, cultura", "http://example.com/semana_saude", false));
		assertEquals("Projeto de Lei - PL 1/2016 - 222222222-0 - Ementa PL - EM VOTACAO (CCJC)", this.contproj.exibirProjeto("PL 1/2016"));
		assertEquals("PLP 1/2017", this.contproj.cadastrarPLP(p1, 2017, "Ementa PLP", "transporte,lazer", "http://example.com/semana_saude", "8,5"));
		assertEquals("Projeto de Lei Complementar - PLP 1/2017 - 111111111-0 - Ementa PLP - 8, 5 - EM VOTACAO (CCJC)", this.contproj.exibirProjeto("PLP 1/2017"));
		assertEquals("PEC 1/2006", this.contproj.cadastrarPEC(p1, 2006, "Ementa PEC", "cultura,transporte", "http://example.com/semana_saude", "13,5"));
		assertEquals("Projeto de Emenda Constitucional - PEC 1/2006 - 111111111-0 - Ementa PEC - 13, 5 - EM VOTACAO (CCJC)", this.contproj.exibirProjeto("PEC 1/2006"));
		assertEquals("EM VOTACAO (CCJC)", this.contproj.exibirTramitacao("PL 1/2018"));
		assertEquals("EM VOTACAO (CCJC)", this.contproj.exibirTramitacao("PLP 1/2017"));
		assertEquals("EM VOTACAO (CCJC)", this.contproj.exibirTramitacao("PEC 1/2006"));
	}
	
	@Test
	void testExibirTramitacaoInexistente() {
		try {
			this.contproj.exibirTramitacao("PLP 1/2005");
		}catch (IllegalArgumentException iae){
			assertEquals("Erro ao exibir tramitacao: projeto inexistente", iae.getMessage());
		}
	}
	
	@Test
	void retornaProjeto() {
		ProjetoLei proj1 = new ProjetoLei(p1, 2018, "PL 1/2018", "Ementa PL", "saude,educacao, lazer", "http://example.com/semana_saude", true, 1);
		assertEquals("PL 1/2018", this.contproj.cadastrarPL(p1, 2018, "Ementa PL", "saude,educacao, lazer", "http://example.com/semana_saude", true));
		assertEquals(proj1.toString(), this.contproj.retornaProjeto("PL 1/2018").toString());
	}
	
	@Test
	void pegarPropostaRelacionada() {
		assertEquals("PL 1/2018", this.contproj.cadastrarPL(p1, 2018, "Ementa PL", "saude,educacao,lazer", "http://example.com/semana_saude", true));
		assertEquals("PL 1/2016", this.contproj.cadastrarPL(p2, 2016, "Ementa PL", "saude,educacao,cultura", "http://example.com/semana_saude", false));
		assertEquals("PLP 1/2017", this.contproj.cadastrarPLP(p1, 2017, "Ementa PLP", "saude,educacao,lazer", "http://example.com/semana_saude", "8,5"));
		assertEquals("PLP 2/2017", this.contproj.cadastrarPLP(p2, 2017, "Ementa PLP", "saude,educacao,transporte", "http://example.com/semana_saude", "6,7"));
		assertEquals("PLP 1/2017", this.contproj.pegarPropostaRelacionada(p1));
		p1.setEstrategia(new EstrategiaConclusao());
		assertEquals("PL 1/2016", this.contproj.pegarPropostaRelacionada(p1));
	}
}
