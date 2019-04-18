package com.uol.controller;

import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;
import com.uol.consumer.EndpointConsumer;
import com.uol.dao.MongoDBJDBC;
import com.uol.model.ArrayConsolidatedVO;
import com.uol.model.ClienteVO;
import com.uol.model.ConsolidatedcWeatherVO;
import com.uol.model.IpVigilanteVO;
import com.uol.model.LocationVO;
import com.uol.utils.HelperUtils;

/**
 * 
 * @author julio
 * @since 2019-04-10
 * @version 1.0.0
 * 
 */
@RestController
public class UOLController {

	private static final Logger logger = Logger.getLogger(UOLController.class.getName());

	@Autowired
	EndpointConsumer serviceApp;

	@Autowired
	MongoDBJDBC mongoJdbc;
	
	@RequestMapping("/teste-uol")
	public String index() {
		return "Greetings from Spring Boot!";
	}

	@RequestMapping("/teste-uol/executa")
	public ResponseEntity<ClienteVO> getTemperatureDay() {// @RequestParam("latt") String latt,	// @RequestParam("long") String long) {
		logger.info("--- uol - solucao teste ---");
	
		HttpStatus status = HttpStatus.ACCEPTED;
		ClienteVO resultCliente = getTemperatureGeoCliente();
		return new ResponseEntity<ClienteVO>(resultCliente, status);
	}
	
	private ClienteVO getTemperatureGeoCliente() {
		String jsonString = this.serviceApp.getItemsByEndpoint();
		Gson gson = new Gson();
		IpVigilanteVO ipvigilante = gson.fromJson(jsonString, IpVigilanteVO.class);
		double latitude = ipvigilante.getData().getLatitude();
		double longitude = ipvigilante.getData().getLongitude();
		logger.info("--- step 1 complete: ipvigilante result: " + ipvigilante.toString());

		/**
		 * passo 1:
		 */
		String jsonStringLocation = this.serviceApp.getLocationByParams(latitude, longitude);
		Gson gsonLocation = new Gson();
		LocationVO[] arrayLocation = gsonLocation.fromJson(jsonStringLocation, LocationVO[].class);
		LocationVO location = arrayLocation[0];
		long idWoeid = location.getWoeid();
		logger.info("--- step 2 complete: location result: " + ipvigilante.toString());

		/**
		 * passo 2:
		 */
		String jsonTemperature = this.serviceApp.getTemperatureByWoeid(idWoeid);
		Gson gsonTemperature = new Gson();
		ArrayConsolidatedVO arrayTemperature = gsonTemperature.fromJson(jsonTemperature, ArrayConsolidatedVO.class);
		ConsolidatedcWeatherVO[] consolidateWeather = arrayTemperature.getConsolidated_weather();
		ConsolidatedcWeatherVO consolidate = consolidateWeather[0];
		logger.info("--- step 3 complete: temperature result: " + consolidate.toString());

		/**
		 * passo 3
		 */
		ClienteVO resultCliente = new ClienteVO();
		resultCliente.setIpOrigem(ipvigilante.getData().getIpv4());
		/*** 
		 * este é um atributo para usuario, para a execução das regras de negocio 
				são utilizadas as coordenadas de latitude e longitude
		*/
		resultCliente.setGeoLocalizacao(ipvigilante.getData().getCity_name());  
		resultCliente.setTempMax(consolidate.getMax_temp());
		resultCliente.setTempMin(consolidate.getMin_temp());
		resultCliente.setNome("");
		resultCliente.setIdade(0);
		logger.info("--- step 4 complete: client result: " + resultCliente.toString());
		
		return resultCliente;
	}
	
	//CRUD - POST (salvar)
	@RequestMapping(method = RequestMethod.POST, value="/teste-uol/save/{nome}/{idade}")
    public ResponseEntity<String> saveData(@PathVariable("nome") String nome, 
    		                               @PathVariable("idade") String idade) {
		logger.info("--- uol - metodo save ---");
		ClienteVO clienteExecuta = getTemperatureGeoCliente();
		
		ClienteVO clientFinal = new ClienteVO();
		clientFinal.setDataCadastro(HelperUtils.convertDateToString(new Date()));
		clientFinal.setNome(nome);
		clientFinal.setIdade(Integer.parseInt(idade));
		clientFinal.setIpOrigem(clienteExecuta.getIpOrigem());
		clientFinal.setGeoLocalizacao(clienteExecuta.getGeoLocalizacao());
		clientFinal.setTempMax(clienteExecuta.getTempMax());
		clientFinal.setTempMin(clienteExecuta.getTempMin());
		logger.info("--- uol - save client: nome = " +clientFinal.getNome() + ", idade =  " + clientFinal.getIdade() + " ---");
		
		this.mongoJdbc.save(clientFinal);
		//this.serviceApp.saveConta();
        return ResponseEntity.ok("save ok!");
    }
	
	//CRUD - PUT (update)
	@RequestMapping(method = RequestMethod.PUT, value="/teste-uol/save/{nome}/{idade}")
	public ResponseEntity<String> upateData(@PathVariable("nome") String nome, 
	   		                                @PathVariable("idade") String idade) {
		logger.info("--- uol - metodo save ---");
		ClienteVO clienteExecuta = getTemperatureGeoCliente();
			
		ClienteVO clientFinal = new ClienteVO();
		clientFinal.setDataCadastro(HelperUtils.convertDateToString(new Date()));
		clientFinal.setNome(nome);
		clientFinal.setIdade(Integer.parseInt(idade));
		clientFinal.setIpOrigem(clienteExecuta.getIpOrigem());
		clientFinal.setGeoLocalizacao(clienteExecuta.getGeoLocalizacao());
		clientFinal.setTempMax(clienteExecuta.getTempMax());
		clientFinal.setTempMin(clienteExecuta.getTempMin());
		logger.info("--- uol - update client: nome = " +clientFinal.getNome() + ", idade =  " + clientFinal.getIdade() + " ---");
			
		this.mongoJdbc.update(clientFinal);
	    return ResponseEntity.ok("update ok!");
	}

    // CRUD - GET (get all clients)
	@RequestMapping(value = "/teste-uol/allclients", method = RequestMethod.GET)
	public ResponseEntity<List<ClienteVO>> getAllClients() {
		logger.info("--- metodo listar todos os clientes cadastrados ---");
		List<ClienteVO> body = this.mongoJdbc.getAllClients();
		logger.info("--- uol - lista: " + body.toString() + " ---");
		HttpStatus status = HttpStatus.ACCEPTED;
		return new ResponseEntity<List<ClienteVO>>(body, status);
	}
	
	// GET  by ID(get clients by id)
	@RequestMapping(value = "/teste-uol/getclient/{id}", method = RequestMethod.GET)
	public ResponseEntity<ClienteVO>  getClientById(@PathVariable Long id) {
		logger.info("--- metodo listar todos os clientes cadastrados ---");
		ClienteVO body = this.mongoJdbc.getClientById(String.valueOf(id));
		logger.info("--- uol - lista: " + body.toString() + " ---");
		HttpStatus status = HttpStatus.ACCEPTED;
		return new ResponseEntity<ClienteVO>(body, status);
	}
	
	// DELETE  by ID(get clients by id)
	@RequestMapping(value = "/teste-uol/delete/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<String>  deleteClientById(@PathVariable Long id) {
		logger.info("--- metodo deletar cliente by id ---");
		this.mongoJdbc.deleteClientById(id);
		return ResponseEntity.ok("delete ok!");
	}	

}
