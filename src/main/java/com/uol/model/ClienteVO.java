package com.uol.model;

import java.io.Serializable;

/**
 * 
 * @author julio
 * @since 2019-04-10
 * @version 1.0.0
 * 
 */
public class ClienteVO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String nome;
	private Integer idade;
	// apos execução das regras de negocio
	private String ipOrigem;
	private String geoLocalizacao;
	private String dataCadastro;
	private Double tempMax;
	private Double tempMin;

	/**
	 * getters and setters
	 */
	public String getNome() {
		return nome;
	}

	public Double getTempMax() {
		return tempMax;
	}

	public void setTempMax(Double tempMax) {
		this.tempMax = tempMax;
	}

	public Double getTempMin() {
		return tempMin;
	}

	public void setTempMin(Double tempMin) {
		this.tempMin = tempMin;
	}

	public String getIpOrigem() {
		return ipOrigem;
	}

	public void setIpOrigem(String ipOrigem) {
		this.ipOrigem = ipOrigem;
	}

	public String getGeoLocalizacao() {
		return geoLocalizacao;
	}

	public void setGeoLocalizacao(String geoLocalizacao) {
		this.geoLocalizacao = geoLocalizacao;
	}

	public String getDataCadastro() {
		return dataCadastro;
	}

	public void setDataCadastro(String dataCadastro) {
		this.dataCadastro = dataCadastro;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Integer getIdade() {
		return idade;
	}

	public void setIdade(Integer idade) {
		this.idade = idade;
	}

}
