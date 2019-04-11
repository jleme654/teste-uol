package com.uol.model;

public class ResultClienteVO {

	/**
	 *  O ID DO CLIENTE É O PROPRIO IP DE QUEM CADASTROU 
	 */
	private String id;
	private String nome;
	private double tempMin;
	private double tempMax;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public double getTempMin() {
		return tempMin;
	}

	public void setTempMin(double tempMin) {
		this.tempMin = tempMin;
	}

	public double getTempMax() {
		return tempMax;
	}

	public void setTempMax(double tempMax) {
		this.tempMax = tempMax;
	}

}
