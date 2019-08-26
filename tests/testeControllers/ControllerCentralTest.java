package testeControllers;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import controllers.ControllerCentral;

class ControllerCentralTest {

	private ControllerCentral contCentral;
	
	@BeforeEach
	void constroiControllerCentral() {
		this.contCentral = new ControllerCentral();
		this.contCentral.cadastrarPessoa("Gabriel", "147523687-7", "PB", "Seguranca,Transporte", "PSL");
		this.contCentral.cadastrarDeputado("147523687-7", "13062019");
	}
	
	@Test
	void cadastraPessoa() {
		this.contCentral.cadastrarPessoa("Daniel", "223154546-9", "PA", "Saude,Educacao");
		this.contCentral.cadastrarPessoa("Tadeu", "254545465-3", "MG", "Transporte,Educacao");
	}
	
	@Test
	void cadastraPessoaDniInvalido() {
		try {
			this.contCentral.cadastrarPessoa("Gabriel", "32324343", "PB", "Saude");
		}catch(IllegalArgumentException iae) {
			assertEquals(iae.getMessage(),"Erro ao cadastrar pessoa: dni invalido");
		}
	}
	
	@Test
	void cadastraPessoaParametrosInvalidos() {
		
		try {
			this.contCentral.cadastrarPessoa("       ", "123123546-7", "PB", "Saude");
		}catch(IllegalArgumentException iae) {
			assertEquals(iae.getMessage(),"Erro ao cadastrar pessoa: nome nao pode ser vazio ou nulo");
		}
		
		try {
			this.contCentral.cadastrarPessoa("Gabriel", "123123546-7", "      ", "Saude");
		}catch(IllegalArgumentException iae) {
			assertEquals(iae.getMessage(),"Erro ao cadastrar pessoa: estado nao pode ser vazio ou nulo");
		}

		try {
			this.contCentral.cadastrarPessoa("Gabriel", "", "PB", "");
		}catch(IllegalArgumentException iae) {
			assertEquals(iae.getMessage(),"Erro ao cadastrar pessoa: dni nao pode ser vazio ou nulo");
		}
	
		try {
			this.contCentral.cadastrarPessoa(null, "123123546-7", "PB", "Saude");
		}catch(NullPointerException npe) {
			assertEquals(npe.getMessage(),"Erro ao cadastrar pessoa: nome nao pode ser vazio ou nulo");
		}
		
		try {
			this.contCentral.cadastrarPessoa("Gabriel", "123123546-7", null, "Saude");
		}catch(NullPointerException npe) {
			assertEquals(npe.getMessage(),"Erro ao cadastrar pessoa: estado nao pode ser vazio ou nulo");
		}
		
		try {
			this.contCentral.cadastrarPessoa("Gabriel", null, "PB", "Saude");
		}catch(NullPointerException npe) {
			assertEquals(npe.getMessage(),"Erro ao cadastrar pessoa: dni nao pode ser vazio ou nulo");
		}

	}
	
	@Test
	void cadastraDeputado() {
		this.contCentral.cadastrarPessoa("Gabriel", "123123546-7", "PB", "Saude", "PSDB");
		this.contCentral.cadastrarDeputado("123123546-7", "23032005");
	}
	
	@Test
	void cadastraDeputadoPessoaInexistente() {
		try {
			this.contCentral.cadastrarDeputado("123123546-7", "23032005");
		}catch(IllegalArgumentException iae) {
			assertEquals(iae.getMessage(),"Erro ao cadastrar deputado: pessoa nao encontrada");
		}
	}
	
	@Test
	void cadastraDeputadoDniInvalido() {
		this.contCentral.cadastrarPessoa("Gabriel", "123123546-7", "PB", "Saude", "PSDB");
		try {
			this.contCentral.cadastrarDeputado("12312346-7", "23032005");
		}catch(IllegalArgumentException iae) {
			assertEquals(iae.getMessage(),"Erro ao cadastrar deputado: dni invalido");
		}
	}
	
	@Test
	void cadastraDeputadoDataInvalida() {
		this.contCentral.cadastrarPessoa("Gabriel", "123123546-7", "PB", "Saude", "PSDB");
		try {
			this.contCentral.cadastrarDeputado("123123546-7", "23032405");
		}catch(IllegalArgumentException iae) {
			assertEquals(iae.getMessage(),"Erro ao cadastrar deputado: data futura");
		}
		
		try {
			this.contCentral.cadastrarDeputado("123123546-7", "23032076");
		}catch(IllegalArgumentException iae) {
			assertEquals(iae.getMessage(),"Erro ao cadastrar deputado: data futura");
		}
	}
	
	@Test
	void cadastraDeputadoParametrosInvalidos() {
		this.contCentral.cadastrarPessoa("Gabriel", "123123546-7", "PB", "Saude", "PSDB");
		try {
			this.contCentral.cadastrarDeputado("        ", "23032005");
		}catch(IllegalArgumentException iae) {
			assertEquals(iae.getMessage(),"Erro ao cadastrar deputado: dni nao pode ser vazio ou nulo");
		}
		
		try {
			this.contCentral.cadastrarDeputado("123123546-7", "          ");
		}catch(IllegalArgumentException iae) {
			assertEquals(iae.getMessage(),"Erro ao cadastrar deputado: data nao pode ser vazio ou nulo");
		}
		
		try {
			this.contCentral.cadastrarDeputado(null, "23032005");
		}catch(NullPointerException npe) {
			assertEquals(npe.getMessage(),"Erro ao cadastrar deputado: dni nao pode ser vazio ou nulo");
		}
		
		try {
			this.contCentral.cadastrarDeputado("123123546-7", null);
		}catch(NullPointerException npe) {
			assertEquals(npe.getMessage(),"Erro ao cadastrar deputado: data nao pode ser vazio ou nulo");
		}
	}
	
	@Test
	void exibePessoa() {
		this.contCentral.cadastrarPessoa("Gabriel", "123123546-7", "PB", "Saude", "PSDB");
		this.contCentral.cadastrarPessoa("Daniel", "417313233-7", "MG", "Transporte");
		this.contCentral.cadastrarDeputado("123123546-7", "23032005");
		assertEquals(this.contCentral.exibirPessoa("123123546-7"), "POL: Gabriel - 123123546-7 (PB) - PSDB - Interesses: Saude - 23/03/2005 - 0 Leis");
		assertEquals(this.contCentral.exibirPessoa("417313233-7"), "Daniel - 417313233-7 (MG) - Interesses: Transporte");
	}
	
	@Test
	void exibePessoaParametrosInvalidos() {
		this.contCentral.cadastrarPessoa("Gabriel", "123123546-7", "PB", "Saude", "PSDB");
		try {
			this.contCentral.exibirPessoa("");
		}catch(IllegalArgumentException iae) {
			assertEquals(iae.getMessage(),"Erro ao exibir pessoa: dni nao pode ser vazio ou nulo");
		}
	
		try {
			this.contCentral.exibirPessoa(null);
		}catch(NullPointerException npe) {
			assertEquals(npe.getMessage(),"Erro ao exibir pessoa: dni nao pode ser vazio ou nulo");
		}
	}
	
	@Test
	void cadastrarPartido() {
		this.contCentral.cadastrarPartido("PMDB");
	}
	
	@Test
	void cadastrarPartidoDniInvalido() {
		try {
			this.contCentral.cadastrarPartido("432423423342");;
		}catch(IllegalArgumentException iae) {
			assertEquals(iae.getMessage(),"Erro ao cadastrar partido: dni invalido");
		}
	}
	
	@Test
	void cadastrarPartidoParametroInvalido() {
		
		try {
			this.contCentral.cadastrarPartido("");;
		}catch(IllegalArgumentException iae) {
			assertEquals(iae.getMessage(),"Erro ao cadastrar partido: partido nao pode ser vazio ou nulo");
		}
		
		try {
			this.contCentral.cadastrarPartido(null);
		}catch(NullPointerException npe) {
			assertEquals(npe.getMessage(),"Erro ao cadastrar partido: partido nao pode ser vazio ou nulo");
		}
	}
	
	@Test
	void cadastrarPartidoExibirBase() {
		this.contCentral.cadastrarPartido("PMDB");
		this.contCentral.cadastrarPartido("PT");
		this.contCentral.cadastrarPartido("PSL");
		this.contCentral.cadastrarPartido("RER");
		assertEquals(this.contCentral.exibirBase(), "PT,PSL,PMDB,RER");
	}
	
	@Test
	void testCadastraComissao() {
		this.contCentral.cadastrarComissao("Seguranca publica", "147523687-7");
	}
	
	@Test
	void testCadastraComissaoParametrosInvalidos() {
		this.contCentral.cadastrarComissao("Saude mental", "147523687-7");
		try {
			this.contCentral.cadastrarComissao("", "147523687-7");
		}catch(IllegalArgumentException iae) {
			assertEquals("Erro ao cadastrar comissao: tema nao pode ser vazio ou nulo", iae.getMessage());
		}
		try {
			this.contCentral.cadastrarComissao(null, "147523687-7");
		}catch (NullPointerException npe) {
			assertEquals("Erro ao cadastrar comissao: tema nao pode ser vazio ou nulo", npe.getMessage());
		}
		try {
			this.contCentral.cadastrarComissao("Seguranca publica", "");
		}catch (IllegalArgumentException iae) {
			assertEquals("Erro ao cadastrar comissao: lista de politicos nao pode ser vazio ou nulo", iae.getMessage());
		}
		try {
			this.contCentral.cadastrarComissao("Seguranca publica", null);
		}catch (NullPointerException npe) {
			assertEquals("Erro ao cadastrar comissao: lista de politicos nao pode ser vazio ou nulo", npe.getMessage());
		}
		try {
			this.contCentral.cadastrarComissao("Saude mental", "147523687-7");
		}catch(IllegalArgumentException iae) {
			assertEquals("Erro ao cadastrar comissao: tema existente", iae.getMessage());
		}
		try {
			this.contCentral.cadastrarComissao("Seguranca publica", "123445-2");
		}catch(IllegalArgumentException iae) {
			assertEquals("Erro ao cadastrar comissao: dni invalido", iae.getMessage());
		}
		try {
			this.contCentral.cadastrarComissao("Seguranca publica", "568237854-7");
		}catch(IllegalArgumentException iae) {
			assertEquals("Erro ao cadastrar comissao: pessoa inexistente", iae.getMessage());
		}
	}
	
	@Test
	void testCadastrarPL() {
		assertEquals("PL 1/2018", this.contCentral.cadastrarPL("147523687-7", 2018, "Ementa PL Conclusiva", "saude", "http://gov.br/saude", true));
	}
	
	@Test
	void testCadastrarPlparametrosInvalidos() {
		try {
			this.contCentral.cadastrarPL("", 2018, "Ementa PL Conclusiva", "saude", "http://gov.br/saude", true);
		}catch(IllegalArgumentException iae) {
			assertEquals("Erro ao cadastrar projeto: autor nao pode ser vazio ou nulo", iae.getMessage());
		}
		try {
			this.contCentral.cadastrarPL(null, 2018, "Ementa PL Conclusiva", "saude", "http://gov.br/saude", true);
		}catch(NullPointerException npe) {
			assertEquals("Erro ao cadastrar projeto: autor nao pode ser vazio ou nulo", npe.getMessage());
		}
		try {
			this.contCentral.cadastrarPL("12345678-1", 2018, "Ementa PL Conclusiva", "saude", "http://gov.br/saude", true);
		}catch(IllegalArgumentException iae) {
			assertEquals("Erro ao cadastrar projeto: dni invalido", iae.getMessage());
		}
		try {
			this.contCentral.cadastrarPL("147523687-7", 2018, "", "saude", "http://gov.br/saude", true);
		}catch(IllegalArgumentException iae) {
			assertEquals("Erro ao cadastrar projeto: ementa nao pode ser vazia ou nula", iae.getMessage());
		}
		try {
			this.contCentral.cadastrarPL("147523687-7", 2018, null, "saude", "http://gov.br/saude", true);
		}catch(NullPointerException npe) {
			assertEquals("Erro ao cadastrar projeto: ementa nao pode ser vazia ou nula", npe.getMessage());
		}
		try {
			this.contCentral.cadastrarPL("147523687-7", 2018, "Ementa PL Conclusiva", "", "http://gov.br/saude", true);
		}catch(IllegalArgumentException iae) {
			assertEquals("Erro ao cadastrar projeto: interesse nao pode ser vazio ou nulo", iae.getMessage());
		}
		try {
			this.contCentral.cadastrarPL("147523687-7", 2018, "Ementa PL Conclusiva", null, "http://gov.br/saude", true);
		}catch(NullPointerException npe) {
			assertEquals("Erro ao cadastrar projeto: interesse nao pode ser vazio ou nulo", npe.getMessage());
		}
		try {
			this.contCentral.cadastrarPL("147523687-7", 2018, "Ementa PL Conclusiva", "saude", "", true);
		}catch(IllegalArgumentException iae) {
			assertEquals("Erro ao cadastrar projeto: url nao pode ser vazio ou nulo", iae.getMessage());
		}
		try {
			this.contCentral.cadastrarPL("147523687-7", 2018, "Ementa PL Conclusiva", "saude", null, true);
		}catch(NullPointerException npe) {
			assertEquals("Erro ao cadastrar projeto: url nao pode ser vazio ou nulo", npe.getMessage());
		}
		try {
			this.contCentral.cadastrarPL("147523687-6", 2018, "Ementa PL Conclusiva", "saude", "http://gov.br/saude", true);
		}catch(IllegalArgumentException iae) {
			assertEquals("Erro ao cadastrar projeto: pessoa inexistente", iae.getMessage());
		}
		try {
			this.contCentral.cadastrarPessoa("Max", "123764828-0", "PB", "Educacao");
			this.contCentral.cadastrarPL("123764828-0", 2018, "Ementa PL Conclusiva", "saude", "http://gov.br/saude", true);
		}catch(IllegalArgumentException iae) {
			assertEquals("Erro ao cadastrar projeto: pessoa nao eh deputado", iae.getMessage());
		}
		
	}
	
	@Test
	void testCadastraPLP() {
		assertEquals("PLP 1/2016", this.contCentral.cadastrarPLP("147523687-7", 2016, "Ementa PLP", "saude,transporte", "http://gov.br/saudeetransporte", "3,5"));
	}
	
	@Test
	void testCadastraPLPparametrosInvalidos() {
		try {
			this.contCentral.cadastrarPLP("", 2018, "Ementa PLP ", "saude", "http://gov.br/saude", "3,5");
		}catch(IllegalArgumentException iae) {
			assertEquals("Erro ao cadastrar projeto: autor nao pode ser vazio ou nulo", iae.getMessage());
		}
		try {
			this.contCentral.cadastrarPLP(null, 2018, "Ementa PLP ", "saude", "http://gov.br/saude", "3,5");
		}catch(NullPointerException npe) {
			assertEquals("Erro ao cadastrar projeto: autor nao pode ser vazio ou nulo", npe.getMessage());
		}
		try {
			this.contCentral.cadastrarPLP("12345678-1", 2018, "Ementa PLP", "saude", "http://gov.br/saude", "3,5");
		}catch(IllegalArgumentException iae) {
			assertEquals("Erro ao cadastrar projeto: dni invalido", iae.getMessage());
		}
		try {
			this.contCentral.cadastrarPLP("147523687-7", 2018, "", "saude", "http://gov.br/saude", "3,5");
		}catch(IllegalArgumentException iae) {
			assertEquals("Erro ao cadastrar projeto: ementa nao pode ser vazia ou nula", iae.getMessage());
		}
		try {
			this.contCentral.cadastrarPLP("147523687-7", 2018, null, "saude", "http://gov.br/saude", "3,5");
		}catch(NullPointerException npe) {
			assertEquals("Erro ao cadastrar projeto: ementa nao pode ser vazia ou nula", npe.getMessage());
		}
		try {
			this.contCentral.cadastrarPLP("147523687-7", 2018, "Ementa PLP", "", "http://gov.br/saude", "3,5");
		}catch(IllegalArgumentException iae) {
			assertEquals("Erro ao cadastrar projeto: interesse nao pode ser vazio ou nulo", iae.getMessage());
		}
		try {
			this.contCentral.cadastrarPLP("147523687-7", 2018, "Ementa PLP", null, "http://gov.br/saude", "3,5");
		}catch(NullPointerException npe) {
			assertEquals("Erro ao cadastrar projeto: interesse nao pode ser vazio ou nulo", npe.getMessage());
		}
		try {
			this.contCentral.cadastrarPLP("147523687-7", 2018, "Ementa PLP", "saude", "", "3,5");
		}catch(IllegalArgumentException iae) {
			assertEquals("Erro ao cadastrar projeto: url nao pode ser vazio ou nulo", iae.getMessage());
		}
		try {
			this.contCentral.cadastrarPLP("147523687-7", 2018, "Ementa PLP", "saude", null, "3,5");
		}catch(NullPointerException npe) {
			assertEquals("Erro ao cadastrar projeto: url nao pode ser vazio ou nulo", npe.getMessage());
		}
		try {
			this.contCentral.cadastrarPLP("147523687-7", 2016, "Ementa PLP", "saude,transporte", "http://gov.br/saudeetransporte", "");
		}catch(IllegalArgumentException iae) {
			assertEquals("Erro ao cadastrar projeto: artigo nao pode ser vazio ou nulo", iae.getMessage());
		}
		try {
			this.contCentral.cadastrarPLP("147523687-7", 2016, "Ementa PLP", "saude,transporte", "http://gov.br/saudeetransporte", null);
		}catch(NullPointerException npe) {
			assertEquals("Erro ao cadastrar projeto: artigo nao pode ser vazio ou nulo", npe.getMessage());
		}
		try {
			this.contCentral.cadastrarPLP("147523687-6", 2018, "Ementa PLP", "saude", "http://gov.br/saude", "3,5");
		}catch(IllegalArgumentException iae) {
			assertEquals("Erro ao cadastrar projeto: pessoa inexistente", iae.getMessage());
		}
		try {
			this.contCentral.cadastrarPessoa("Max", "123764828-0", "PB", "Educacao");
			this.contCentral.cadastrarPLP("123764828-0", 2018, "Ementa PLP", "saude", "http://gov.br/saude", "3,5");
		}catch(IllegalArgumentException iae) {
			assertEquals("Erro ao cadastrar projeto: pessoa nao eh deputado", iae.getMessage());
		}
	}
	
	@Test
	void testCadastraPEC() {
		assertEquals("PEC 1/2019", this.contCentral.cadastrarPEC("147523687-7", 2019, "Altera investimentos na area da saude", "saude", "www.gov.br/brasil/saude.html", "189,193"));
	}
	
	@Test
	void testCadastraPECparametrosInvalidos() {
		try {
			this.contCentral.cadastrarPEC("", 2018, "Altera investimentos na area da saude", "saude", "www.gov.br/brasil/saude.html", "189,193");
		}catch(IllegalArgumentException iae) {
			assertEquals("Erro ao cadastrar projeto: autor nao pode ser vazio ou nulo", iae.getMessage());
		}
		try {
			this.contCentral.cadastrarPEC(null, 2018, "Altera investimentos na area da saude", "saude", "www.gov.br/brasil/saude.html", "189,193");
		}catch(NullPointerException npe) {
			assertEquals("Erro ao cadastrar projeto: autor nao pode ser vazio ou nulo", npe.getMessage());
		}
		try {
			this.contCentral.cadastrarPEC("12345678-1", 2018, "Altera investimentos na area da saude", "saude", "www.gov.br/brasil/saude.html", "189,193");
		}catch(IllegalArgumentException iae) {
			assertEquals("Erro ao cadastrar projeto: dni invalido", iae.getMessage());
		}
		try {
			this.contCentral.cadastrarPEC("147523687-7", 2018, "", "saude", "www.gov.br/brasil/saude.html", "189,193");
		}catch(IllegalArgumentException iae) {
			assertEquals("Erro ao cadastrar projeto: ementa nao pode ser vazia ou nula", iae.getMessage());
		}
		try {
			this.contCentral.cadastrarPEC("147523687-7", 2018, null, "saude", "www.gov.br/brasil/saude.html", "189,193");
		}catch(NullPointerException npe) {
			assertEquals("Erro ao cadastrar projeto: ementa nao pode ser vazia ou nula", npe.getMessage());
		}
		try {
			this.contCentral.cadastrarPEC("147523687-7", 2018, "Altera investimentos na area da saude", "", "www.gov.br/brasil/saude.html", "189,193");
		}catch(IllegalArgumentException iae) {
			assertEquals("Erro ao cadastrar projeto: interesse nao pode ser vazio ou nulo", iae.getMessage());
		}
		try {
			this.contCentral.cadastrarPEC("147523687-7", 2018, "Altera investimentos na area da saude", null, "www.gov.br/brasil/saude.html", "189,193");
		}catch(NullPointerException npe) {
			assertEquals("Erro ao cadastrar projeto: interesse nao pode ser vazio ou nulo", npe.getMessage());
		}
		try {
			this.contCentral.cadastrarPEC("147523687-7", 2018, "Altera investimentos na area da saude", "saude", "", "189,193");
		}catch(IllegalArgumentException iae) {
			assertEquals("Erro ao cadastrar projeto: url nao pode ser vazio ou nulo", iae.getMessage());
		}
		try {
			this.contCentral.cadastrarPEC("147523687-7", 2018, "Altera investimentos na area da saude", "saude", null, "189,193");
		}catch(NullPointerException npe) {
			assertEquals("Erro ao cadastrar projeto: url nao pode ser vazio ou nulo", npe.getMessage());
		}
		try {
			this.contCentral.cadastrarPEC("147523687-7", 2016, "Altera investimentos na area da saude", "saude", "www.gov.br/brasil/saude.html", "");
		}catch(IllegalArgumentException iae) {
			assertEquals("Erro ao cadastrar projeto: artigo nao pode ser vazio ou nulo", iae.getMessage());
		}
		try {
			this.contCentral.cadastrarPEC("147523687-7", 2016, "Altera investimentos na area da saude", "saude", "www.gov.br/brasil/saude.html", null);
		}catch(NullPointerException npe) {
			assertEquals("Erro ao cadastrar projeto: artigo nao pode ser vazio ou nulo", npe.getMessage());
		}
		try {
			this.contCentral.cadastrarPEC("147523687-6", 2018, "Altera investimentos na area da saude", "saude", "www.gov.br/brasil/saude.html", "189,193");
		}catch(IllegalArgumentException iae) {
			assertEquals("Erro ao cadastrar projeto: pessoa inexistente", iae.getMessage());
		}
		try {
			this.contCentral.cadastrarPessoa("Max", "123764828-0", "PB", "Educacao");
			this.contCentral.cadastrarPLP("123764828-0", 2018,  "Altera investimentos na area da saude", "saude", "www.gov.br/brasil/saude.html", "189,193");
		}catch(IllegalArgumentException iae) {
			assertEquals("Erro ao cadastrar projeto: pessoa nao eh deputado", iae.getMessage());
		}
	}
	
	@Test
	void testExibirProjeto() {
		this.contCentral.cadastrarPL("147523687-7", 2018, "Ementa PL Conclusiva", "saude", "http://gov.br/saude", true);
		assertEquals("Projeto de Lei - PL 1/2018 - 147523687-7 - Ementa PL Conclusiva - Conclusiva - EM VOTACAO (CCJC)", this.contCentral.exibirProjeto("PL 1/2018"));
	}
	
	@Test
	void testExibirProjetoParametroInvalido() {
		this.contCentral.cadastrarPL("147523687-7", 2018, "Ementa PL Conclusiva", "saude", "http://gov.br/saude", true);
		try {
			this.contCentral.exibirProjeto("");
		}catch(IllegalArgumentException iae) {
			assertEquals("Codigo nao pode ser vazio ou  nulo.", iae.getMessage());
		}
		try {
			this.contCentral.exibirProjeto(null);
		}catch(NullPointerException npe) {
			assertEquals("Codigo nao pode ser vazio ou  nulo.", npe.getMessage());
		}
	}
	
	@Test
	void testExibirTramitacao() {
		this.contCentral.cadastrarPL("147523687-7", 2018, "Ementa PL Conclusiva", "saude", "http://gov.br/saude", true);
		assertEquals("EM VOTACAO (CCJC)", this.contCentral.exibirTramitacao("PL 1/2018"));
	}
	
	@Test
	void testExibirTramitacaoParametrosInvalidos() {
		this.contCentral.cadastrarPL("147523687-7", 2018, "Ementa PL Conclusiva", "saude", "http://gov.br/saude", true);
		try {
			this.contCentral.exibirTramitacao("");
		}catch(IllegalArgumentException iae) {
			assertEquals("Codigo nao pode ser vazio ou  nulo.", iae.getMessage());
		}
		try {
			this.contCentral.exibirTramitacao(null);
		}catch(NullPointerException npe) {
			assertEquals("Codigo nao pode ser vazio ou  nulo.", npe.getMessage());
		}
	}
	
	@Test
	void exibirTramitacaoParametrosInvalidos() {
		try {
			this.contCentral.exibirTramitacao("");
		}catch(IllegalArgumentException iae) {
			assertEquals("Codigo nao pode ser vazio ou  nulo.", iae.getMessage());
		}
		
		try {
			this.contCentral.exibirTramitacao(null);
		}catch(NullPointerException npe) {
			assertEquals("Codigo nao pode ser vazio ou  nulo.", npe.getMessage());
		}
	}
	
	@Test
	void testPropostaRelaciona() {
		this.contCentral.cadastrarPEC("147523687-7", 2019, "Altera investimentos na area da seguranca", "Seguranca,transporte", "www.gov.br/brasil/seguranca.html", "189,193");
		this.contCentral.cadastrarPessoa("Joaozinho", "123456789-3", "SP", "Seguranca");
		assertEquals("PEC 1/2019", this.contCentral.pegarPropostaRelacionada("123456789-3"));
	}
	
	@Test
	void testPropostaRelacionaParametrosInvalidos() {
		try {
			this.contCentral.pegarPropostaRelacionada("");
		}catch(IllegalArgumentException iae) {
			assertEquals("Erro ao pegar proposta relacionada: pessoa nao pode ser vazia ou nula", iae.getMessage());
		}
		try {
			this.contCentral.pegarPropostaRelacionada(null);
		}catch(NullPointerException npe) {
			assertEquals("Erro ao pegar proposta relacionada: pessoa nao pode ser vazia ou nula", npe.getMessage());
		}
		try {
			this.contCentral.pegarPropostaRelacionada("123456-2");
		}catch(IllegalArgumentException iae) {
			assertEquals("Erro ao pegar proposta relacionada: dni invalido", iae.getMessage());
		}
	}
	
	@Test
	void configurarEstrategia() {
		try {
			this.contCentral.configurarEstrategiaPropostaRelacionada("   ", "CONCLUSAO");
		}catch(IllegalArgumentException iae) {
			assertEquals("Erro ao configurar estrategia: pessoa nao pode ser vazia ou nula", iae.getMessage());
		}
		
		try {
			this.contCentral.configurarEstrategiaPropostaRelacionada("432432432-A", "APROVACAO");
		}catch(IllegalArgumentException iae) {
			assertEquals("Erro ao configurar estrategia: dni invalido", iae.getMessage());
		}

		try {
			this.contCentral.configurarEstrategiaPropostaRelacionada(null, "CONSTITUCIONAL");
		}catch(NullPointerException npe) {
			assertEquals("Erro ao configurar estrategia: pessoa nao pode ser vazia ou nula", npe.getMessage());
		}
		
		try {
			this.contCentral.configurarEstrategiaPropostaRelacionada("432432432-0", "PROPOSTA");
		}catch(IllegalArgumentException iae) {
			assertEquals("Erro ao configurar estrategia: estrategia invalida", iae.getMessage());
		}
		
		try {
			this.contCentral.configurarEstrategiaPropostaRelacionada("432432432-0", "      ");
		}catch(IllegalArgumentException iae) {
			assertEquals("Erro ao configurar estrategia: estrategia vazia", iae.getMessage());
		}

		try {
			this.contCentral.configurarEstrategiaPropostaRelacionada("432432432-0", null);
		}catch(NullPointerException npe) {
			assertEquals("Erro ao configurar estrategia: estrategia vazia", npe.getMessage());
		}
	}
	
	@Test
	void pegarPropostaRelacionada() {
		try {
			this.contCentral.pegarPropostaRelacionada("    ");
		}catch(IllegalArgumentException iae) {
			assertEquals("Erro ao pegar proposta relacionada: pessoa nao pode ser vazia ou nula", iae.getMessage());
		}
		
		try {
			this.contCentral.pegarPropostaRelacionada(null);
		}catch(NullPointerException npe) {
			assertEquals("Erro ao pegar proposta relacionada: pessoa nao pode ser vazia ou nula", npe.getMessage());
		}
	}
}
