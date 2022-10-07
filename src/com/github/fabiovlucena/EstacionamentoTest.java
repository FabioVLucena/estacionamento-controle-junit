package com.github.fabiovlucena;

import static org.junit.Assert.fail;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.TestMethodOrder;

@TestInstance(Lifecycle.PER_CLASS)
@TestMethodOrder(OrderAnnotation.class)
public class EstacionamentoTest {

	private EstacionamentoBO estacionamentoBO;
	
	@BeforeAll
	void antesDeTudo() {
		estacionamentoBO = new EstacionamentoBO();
	}
	
	@Test
	@Order(1)
	void deveriaCadastrarUmaVagaNaoOcupada() {
		int quantidadeEsperada = 1;
		String descricao = "A-1";    
		
		estacionamentoBO.cadastrarVaga(descricao);
		
		List<Vaga> vagaList = EstacionamentoDAO.selectVagaByEstaOcupado(false);
		
		assertEquals(vagaList.size(), quantidadeEsperada);
	}

	@Test
	@Order(2)
	void deveriaCadastrarUmCarro() throws Exception {
		int quantidadeEsperada = 1;
		String placa = "MIAMI-0001";
		
		estacionamentoBO.cadastrarCarro(placa);

		List<Carro> carroList = EstacionamentoDAO.selectCarro();
		
		assertEquals(carroList.size(), quantidadeEsperada);
	}

	@Test
	@Order(3)
	void naoDeveriaCadastrarUmCarroComPlacaRepetida() {
		String placa = "MIAMI-0001";

		try {
			estacionamentoBO.cadastrarCarro(placa);
			fail("Não deveria ser possivel cadastrar um carro com placa repetida.");
		} catch (Exception e) {
			assertEquals(e.getClass(), new Exception().getClass());
		}
	}
	
	@Test
	@Order(4)
	void deveriaOcuparUmaVaga() throws Exception {
		int quantidadeEsperada = 1;
		Integer vagaId = 1;
		Integer carroId = 1;
 		
		estacionamentoBO.ocuparVaga(vagaId, carroId);
		
		List<Vaga> vagaList = EstacionamentoDAO.selectVagaByEstaOcupado(true);
		
		assertEquals(vagaList.size(), quantidadeEsperada);
	}
	
	@Test
	@Order(5)
	void naoDeveriaPreencherUmaVagaOcupada() {
		Integer vagaId = 1;
		Integer carroId = 1;

		try {
			estacionamentoBO.ocuparVaga(vagaId, carroId);
		} catch (Exception e) {
			assertEquals(e.getClass(), new Exception().getClass());
		}
	}
	
	@Test
	@Order(6)
	void naoDeveriaOcuparUmaVagaComUmCarroJaEmUso() {
		Integer vagaId = 2;
		Integer carroId = 1;
 		
		String descricao = "A-2";    
		estacionamentoBO.cadastrarVaga(descricao);
		
		try {
			estacionamentoBO.ocuparVaga(vagaId, carroId);
		} catch (Exception e) {
			assertEquals(e.getClass(), new Exception().getClass());
		}
	}
	
	@Test
	@Order(7)
	void deveriaDesocuparVaga() {
		Integer vagaId = 1;
		
		estacionamentoBO.desocuparVaga(vagaId);
		Vaga vaga = EstacionamentoDAO.getVagaById(vagaId); 
		
		assertEquals(vaga.getCarro().getId(), null);
		assertEquals(vaga.isEstaOcupada(), false);
	}
	
	@AfterAll
	void depoisDeTudo() {
		//rollback
		//ou
		//delete
	}
}
