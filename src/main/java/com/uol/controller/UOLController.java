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
	
	/**
	 * CRUD - POST (salvar)
	 */
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
		logger.info("--- uol - client: nome = " +clientFinal.getNome() + ", idade =  " + clientFinal.getIdade() + " ---");
		
		this.mongoJdbc.save(clientFinal);
		//this.serviceApp.saveConta();
        return ResponseEntity.ok("save ok!");
    }

	/**
	 * CRUD - GET (get all clients)
	 */
	@RequestMapping(value = "/teste-uol/allclients", method = RequestMethod.GET)
	public ResponseEntity<List<ClienteVO>> getAllClients() {
		logger.info("--- metodo listar todos os clientes cadastrados ---");
		List<ClienteVO> body = this.mongoJdbc.getAllClients();
		logger.info("--- uol - lista: " + body.toString() + " ---");
		HttpStatus status = HttpStatus.ACCEPTED;
		return new ResponseEntity<List<ClienteVO>>(body, status);
	}
	
//	@RequestMapping(value = "/teste_ucb/alljuridicas", method = RequestMethod.GET)
//	public ResponseEntity<List<PessoaJuridica>> listarPjuridicas() {
//		LOG.info("[--- metodo listar pessoas juridicas ---]");
//		List<PessoaJuridica> body = this.personDAO.getAllPJuridicas();// HelperUtils.getAllContas();
//		HttpStatus status = HttpStatus.ACCEPTED;
//		return new ResponseEntity<List<PessoaJuridica>>(body, status);
//	}
	
	
//	@RequestMapping(method = RequestMethod.PUT, value="/teste_ucb/update",consumes=MediaType.APPLICATION_JSON_VALUE)
//    public ResponseEntity<String> updateData(@RequestBody ContaVO conta) {
//		LOG.info("[--- metodo update ---]");
//		this.serviceApp.updateConta();
//        return ResponseEntity.ok("update ok!");
//    }
//	
//	@DeleteMapping(value = "/teste_ucb/delete/{id}")
//	public void delete(@PathVariable("id") Long id) {
//		LOG.info("[--- metodo delete ---]");
//		this.serviceApp.deleteConta(id);
//	}
//	
//	@GetMapping("/teste_ucb/get/{id}")
//	public ContaVO getContaById(@PathVariable Long id) {
//		LOG.info("[--- metodo get by id ---]");
//		return this.serviceApp.findById(id);
//			//.orElseThrow(() -> new EmployeeNotFoundException(id));
//	}
//
//	@RequestMapping(value = "/teste_ucb/allcontas", method = RequestMethod.GET)
//	public ResponseEntity<List<ContaVO>> listar() {
//		LOG.info("[--- metodo listar ---]");
//		List<ContaVO> body = this.serviceApp.getAllContas();// HelperUtils.getAllContas();
//		HttpStatus status = HttpStatus.ACCEPTED;;
//		return new ResponseEntity<List<ContaVO>>(body, status);
//	}
//	
//	@RequestMapping(value = "/teste_ucb/alljuridicas", method = RequestMethod.GET)
//	public ResponseEntity<List<PessoaJuridica>> listarPjuridicas() {
//		LOG.info("[--- metodo listar pessoas juridicas ---]");
//		List<PessoaJuridica> body = this.personDAO.getAllPJuridicas();// HelperUtils.getAllContas();
//		HttpStatus status = HttpStatus.ACCEPTED;
//		return new ResponseEntity<List<PessoaJuridica>>(body, status);
//	}

}
