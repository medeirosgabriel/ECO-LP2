package testeControllers;

import static org.junit.Assert.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import controllers.ControllerPartidosGovernistas;

class ControllerPartidosGovernistasTest {

	ControllerPartidosGovernistas cpg;
	
	@BeforeEach
	void setUp() {
		this.cpg = new ControllerPartidosGovernistas();
		this.cpg.adicionarPartido("PT");
		this.cpg.adicionarPartido("PSL");
		this.cpg.adicionarPartido("PDT");
	}

	@Test
	void testAdicionaPartido() {
		this.cpg.adicionarPartido("PMDB");
		this.cpg.adicionarPartido("DEM");
	}
	
	@Test
	void testExibeBase() {
		assertEquals("PDT,PT,PSL", this.cpg.exibirBase());
	}
	
}
