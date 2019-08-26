package testeEntidades;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import entidades.Civil;
import entidades.Deputado;
import entidades.Pessoa;

class PessoaTest {
	
	private Pessoa pessoa1;
	private Pessoa pessoa2;

	@BeforeEach
	void constroiPessoa() {
		this.pessoa1 = new Pessoa("Gabriel", "321312312-4", "PB", "Saude", "PMDB");
		this.pessoa2 = new Pessoa("Tadeu", "245436712-4", "MG", "Transporte,Educacao");
	}
	
	@Test
	void testeRepresentacao() {
		this.pessoa1.setFuncao(new Deputado("24061990"));
		assertEquals("POL: Gabriel - 321312312-4 (PB) - PMDB - Interesses: Saude - 24/06/1990 - 0 Leis", this.pessoa1.representacao());
		assertEquals("Tadeu - 245436712-4 (MG) - Interesses: Transporte,Educacao", this.pessoa2.representacao());
	}
	
	@Test
	void testeGetPartido() {
		assertEquals("PMDB", this.pessoa1.getPartido());
	}
	
	@Test
	void testeSetFuncao() {
		assertTrue(this.pessoa2.getFuncao() instanceof Civil);
		this.pessoa2.setFuncao(new Deputado("23092018"));
		assertTrue(this.pessoa2.getFuncao() instanceof Deputado);
	}
	
	@Test
	void testeEquals() {
		Pessoa pessoa3 = new Pessoa("Tadeu", "321312312-4", "PB", "Saude", "PMDB");
		Pessoa pessoa4 = new Pessoa("Gabriel", "343243412-4", "PB", "Saude", "PMDB");
		assertTrue(this.pessoa1.equals(pessoa3));
		assertFalse(this.pessoa1.equals(pessoa4));
	}
	
	@Test
	void testGetDni() {
		assertEquals("321312312-4", this.pessoa1.getDni());
		assertEquals("245436712-4", this.pessoa2.getDni());
	}
	
	@Test
	void testGetInteresses() {
		assertEquals("Saude", this.pessoa1.getInteresses());
		assertEquals("Transporte,Educacao", this.pessoa2.getInteresses());
	}
	
}
