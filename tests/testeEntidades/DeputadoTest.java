package testeEntidades;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import entidades.Deputado;
import entidades.Pessoa;

class DeputadoTest {
	
	private Pessoa dep1;
	private Pessoa dep2;
	private Pessoa dep3;
	private Pessoa dep4;
	
	@BeforeEach
	void constroiDeputados() {
		this.dep1 = new Pessoa("Gabriel", "321312312-4", "PB", "Saude", "PT");
		dep1.setFuncao(new Deputado("23032004"));
		this.dep2 = new Pessoa("Tadeu", "245436712-4", "MG", "Transporte,Educacao", "PSL");
		dep2.setFuncao(new Deputado("24052009"));
		this.dep3 = new Pessoa("Miguel", "675345312-4", "RR", "IDH", "PMDB");
		dep3.setFuncao(new Deputado("27082013"));
		this.dep4 = new Pessoa("Joaozinho", "123456789-1", "PB", "", "PSOL");
		dep4.setFuncao(new Deputado("13062019"));
	}
	
	@Test
	void testeRepresentacao() {
		assertEquals("POL: Gabriel - 321312312-4 (PB) - PT - Interesses: Saude - 23/03/2004 - 0 Leis", this.dep1.representacao());
		assertEquals("POL: Tadeu - 245436712-4 (MG) - PSL - Interesses: Transporte,Educacao - 24/05/2009 - 0 Leis", this.dep2.representacao());
		assertEquals("POL: Miguel - 675345312-4 (RR) - PMDB - Interesses: IDH - 27/08/2013 - 0 Leis", this.dep3.representacao());
		assertEquals("POL: Joaozinho - 123456789-1 (PB) - PSOL - 13/06/2019 - 0 Leis", this.dep4.representacao());
	}
	
	@Test
	void testRepresentacaoComLeisAprovadas() {
		this.dep1.fazerLei();
		assertEquals("POL: Gabriel - 321312312-4 (PB) - PT - Interesses: Saude - 23/03/2004 - 1 Leis", this.dep1.representacao());
	}
}
	
