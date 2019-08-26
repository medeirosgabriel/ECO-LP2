package testeControllers;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import controllers.ControllerComissao;

class ControllerComissaoTest {

	ControllerComissao cc;
	
	@BeforeEach
	void setUp() {
		this.cc = new ControllerComissao();
		this.cc.cadastrarComissao("Seguranca publica", new String[] {"951247863-7", "741852963-4"});
	}

	@Test
	void testCadastraComissao() {
		this.cc.cadastrarComissao("Aborto", new String[] {"123456789-0", "987654321-2"});
	}
	
	@Test
	void testExisteComissao() {
		assertTrue(this.cc.existeComissao("Seguranca publica"));
		assertFalse(this.cc.existeComissao("Aborto"));
		
		try {
			this.cc.cadastrarComissao("Seguranca publica", new String[] {"346789326-3", "025789634-5"});
		}catch(IllegalArgumentException iae) {
			assertEquals("essa comissao ja existe", iae.getMessage());
		}
	}
		
}
