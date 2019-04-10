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

	private Long id;
	private String nome;
	private Integer idade;

	/**
	 * getters and setters
	 */
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
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
