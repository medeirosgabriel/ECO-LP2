package testeEntidades;

import static org.junit.Assert.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import entidades.Comissao;
import entidades.Deputado;
import entidades.Pessoa;

class ComissaoTest {

	
	Comissao c1;
	Comissao c2;
	Pessoa dep1;
	Pessoa dep2;
	Pessoa dep3;
	
	@BeforeEach
	void setUp() {
		this.dep1 = new Pessoa("Gabriel", "321312312-4", "PB", "Direitos", "PT");
		dep1.setFuncao(new Deputado("23032004"));
		this.dep2 = new Pessoa("Tadeu", "245436712-4", "MG", "Transporte,Educacao", "PSL");
		dep2.setFuncao(new Deputado("24052009"));
		this.dep3 = new Pessoa("Miguel", "675345312-4", "RR", "Desenvolvimento Rural", "PMDB");
		dep3.setFuncao(new Deputado("27082013"));
		this.c1 = new Comissao("CCJC", new String[] {this.dep1.getDni(), this.dep2.getDni(), this.dep3.getDni()});
		this.c2 = new Comissao("CDEIS", new String[] {this.dep1.getDni(), this.dep2.getDni(), this.dep3.getDni()});
	}

	@Test
	void testGetTema() {
		assertEquals("CCJC", this.c1.getTema());
		assertEquals("CDEIS", this.c2.getTema());
	}
}
