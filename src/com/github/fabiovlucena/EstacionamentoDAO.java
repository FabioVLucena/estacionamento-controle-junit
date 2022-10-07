package com.github.fabiovlucena;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class EstacionamentoDAO {

	private static Integer vagaId = 1;
	private static List<Vaga> vagaList = new ArrayList<Vaga>();

	private static Integer carroId = 1;
	private static List<Carro> carroList = new ArrayList<Carro>();
	
	public static Vaga getVagaById(Integer id) {
		return vagaList.stream()
				.filter(v -> v.getId() == id)
				.findFirst()
				.orElse(null);
	}

	public static void insertVaga(Vaga vaga) {
		vaga.setId(vagaId++);
		vagaList.add(vaga);		
	}

	public static void insertCarro(Carro carro) {
		carro.setId(carroId);
		carroList.add(carro);		
	}

	public static List<Vaga> selectVagaByEstaOcupado(boolean estaOcupado) {
		List<Vaga> vagaListTemp = vagaList.stream()
				.filter(v -> v.isEstaOcupada() == estaOcupado)
				.collect(Collectors.toList());
		
		return vagaListTemp;
	}

	public static List<Carro> selectCarro() {
		return carroList;
	}

	public static Carro getCarroByPlaca(String placa) {
		return carroList.stream()
				.filter(c -> c.getPlaca() == placa)
				.findFirst()
				.orElse(null);
	}

	public static void updateVaga(Vaga vagaTemp) {
		vagaList.removeIf(v -> v.getId() == vagaTemp.getId());
		vagaList.add(vagaTemp);	
	}

	public static Vaga getVagaByCarroId(Integer carroId) {
		return vagaList.stream()
				.filter(v -> v.getCarro().getId() == carroId)
				.findFirst()
				.orElse(null);
	}
}
