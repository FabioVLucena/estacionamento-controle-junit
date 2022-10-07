package com.github.fabiovlucena;

public class EstacionamentoBO {

	private Vaga vaga;
	private Carro carro;
	
	public EstacionamentoBO() {
		vaga = new Vaga();
		carro = new Carro();
	}
	
	public void cadastrarVaga(String descricao) {
		vaga = new Vaga();
		vaga.setDescricao(descricao);
		vaga.setCarro(new Carro());
		EstacionamentoDAO.insertVaga(vaga);
	}

	public void cadastrarCarro(String placa) throws Exception {
		Carro carroTemp = EstacionamentoDAO.getCarroByPlaca(placa);
		if (carroTemp != null) {
			throw new Exception();
		}
		
		carro.setPlaca(placa);
		EstacionamentoDAO.insertCarro(carro);
	}

	public void ocuparVaga(Integer vagaId, Integer carroId) throws Exception {
		Vaga vagaTemp = EstacionamentoDAO.getVagaById(vagaId);
		if (vagaTemp.isEstaOcupada()) {
			throw new Exception();
		}
		
		Vaga vagaCarroTemp = EstacionamentoDAO.getVagaByCarroId(carroId);
		if (vagaCarroTemp != null) {
			throw new Exception();
		}
		
		vagaTemp.getCarro().setId(carroId);
		vagaTemp.setEstaOcupada(true);
		
		EstacionamentoDAO.updateVaga(vagaTemp);
	}

	public void desocuparVaga(Integer vagaId) {
		Vaga vagaTemp = EstacionamentoDAO.getVagaById(vagaId);
		vagaTemp.setCarro(new Carro());
		vagaTemp.setEstaOcupada(false);
		
		EstacionamentoDAO.updateVaga(vagaTemp);
	}
}
