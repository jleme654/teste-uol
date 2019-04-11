package com.uol.controller;

import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;
import com.uol.consumer.EndpointConsumer;
import com.uol.model.ArrayConsolidatedVO;
import com.uol.model.ConsolidatedcWeatherVO;
import com.uol.model.IpVigilanteVO;
import com.uol.model.LocationVO;
import com.uol.model.ResultClienteVO;


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
	
	@RequestMapping("/teste-uol")
	public String index() {
		return "Greetings from Spring Boot!";
	}
	
@RequestMapping("/teste-uol/executa")
public ResponseEntity<ResultClienteVO> getItemsByRangeDatas() {//@RequestParam("latt") String latt,
			                       //@RequestParam("long") String long) {
		logger.info("--- uol - solucao teste ---");
		
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
		LocationVO[] arrayLocation = gsonLocation.fromJson(jsonStringLocation, LocationVO[].class);//json, classOfT) Arrays.asList(itemsoriginal);
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
		ResultClienteVO resultCliente = new ResultClienteVO();
		resultCliente.setId(ipvigilante.getData().getIpv4());
		resultCliente.setTempMax(consolidate.getMax_temp());
		resultCliente.setTempMin(consolidate.getMin_temp());
		logger.info("--- step 4: cliente result: " + resultCliente.toString());
		
		HttpStatus status = HttpStatus.ACCEPTED;
		return new ResponseEntity<ResultClienteVO>(resultCliente, status);
	}
	
}
