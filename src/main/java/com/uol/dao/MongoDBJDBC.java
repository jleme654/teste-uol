package com.uol.dao;

import com.mongodb.BasicDBObject;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoDatabase;
import com.uol.model.ClienteVO;

public class MongoDBJDBC {
	public static void main(String args[]) {
		try {
			// Conectando ao servidor mongodb
			MongoClient mongoClient = new MongoClient("localhost", 27017);
			// Conecta ao banco de dados test
			MongoDatabase db = mongoClient.getDatabase("test");
			System.out.println("Banco de dados conectado com sucesso");
//			boolean auth = db. //("julio", "master");
//			System.out.println("Autenticacao: " + auth);
		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
		}
	}
	
	public void save(ClienteVO cliente) {
		BasicDBObject pessoa= new BasicDBObject();
		pessoa.put("nome", cliente.getNome());
		pessoa.put("idade", cliente.getIdade());
		pessoa.put("ipOrigem", cliente.getIpOrigem());
		pessoa.put("geoLocalizacao", cliente.getGeoLocalizacao());
		pessoa.put("tempMin", cliente.getTempMin());
		pessoa.put("tempMax", cliente.getTempMax());
		pessoa.put("dataCadastro", cliente.getDataCadastro());
	}
}