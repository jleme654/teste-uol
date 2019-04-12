package com.uol.dao;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.uol.model.ClienteVO;

@Service
public class MongoDBJDBC {

	private static DB db;
	static String name_collection = "collection-clients";
	static String dbMongo;
	static DBObject coll;

	public MongoDBJDBC() {
		createDB();
		createCollection(name_collection);
	}

	static void createDB() {
		MongoClient mongoClient = new MongoClient("localhost", 27017);
		dbMongo = "db-collection-clients";
		db = mongoClient.getDB(dbMongo);
		System.out.println("Banco de dados [" + dbMongo + "] conectado com sucesso");
	}

	static boolean createCollection(String namecoll) {
		boolean existsCollection = db.collectionExists(namecoll);
		if (!existsCollection) {
			db.getCollection(namecoll);
			return true;
		}
		System.out.println("collection [" + dbMongo + "] conectado com sucesso");
		return true;
	}

	public List<ClienteVO> getAllClients() {
		List<ClienteVO> lsClients = new ArrayList<>();
		DBCollection coll = db.getCollection(name_collection);
		DBCursor cursor = ((DBCollection) coll).find();
		int i = 1;
		while (cursor.hasNext()) {
			System.out.println("Documento inserido: " + i);
			System.out.println(cursor.next());
			i++;
		}
		return lsClients;
	}


	public void save(ClienteVO cliente) {
//		BasicDBObject doc = new BasicDBObject("title", "MongoDB").append("description", "database").append("likes", 100)
//				.append("url", "http://www.tutorialspoint.com/mongodb/").append("by", "tutorials point");
		DBCollection coll = db.getCollection(name_collection);
		BasicDBObject pessoa = new BasicDBObject();
		pessoa.put("nome", cliente.getNome());
		pessoa.put("idade", cliente.getIdade());
		pessoa.put("ipOrigem", cliente.getIpOrigem());
		pessoa.put("geoLocalizacao", cliente.getGeoLocalizacao());
		pessoa.put("tempMin", cliente.getTempMin());
		pessoa.put("tempMax", cliente.getTempMax());
		pessoa.put("dataCadastro", cliente.getDataCadastro());
		coll.insert(pessoa);//doc
	}
	
	static ClienteVO getClienteTeste() {
		ClienteVO cliente = new ClienteVO();
		cliente.setDataCadastro("10-04-2019");
		cliente.setGeoLocalizacao("Diadema");
		cliente.setIdade(45);
		cliente.setIpOrigem("888.777.966.555");
		cliente.setNome("arlete teste");
		cliente.setTempMax(18.0);
		cliente.setTempMin(19.0);
		return cliente;
	}

	public static void main(String args[]) {
		try {
            MongoDBJDBC mongojdbc = new MongoDBJDBC();
			// teste para salvar cliente
			mongojdbc.save(getClienteTeste());
//			System.out.println("save ok");

		new MongoDBJDBC().getAllClients();

		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
		}
	}
}