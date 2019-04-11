package com.uol.consumer;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
/**
 * 
 * @author julio
 * @since 2019-04-10
 * @version 1.0.0
 * 
 */
@Service
public class EndpointConsumer {

	public String getItemsByEndpoint() {
		RestTemplate template = new RestTemplate();
		return template.getForObject("https://ipvigilante.com/", String.class);
	}
	
	public String getLocationByParams(double latitude, double longitude) {
		RestTemplate template = new RestTemplate();
		return template.getForObject("https://www.metaweather.com/api/location/search/?lattlong="+latitude +","+longitude, String.class);
	}

	public String getTemperatureByWoeid(long idWoeid) {
		RestTemplate template = new RestTemplate();
		return template.getForObject("https://www.metaweather.com/api/location/"+idWoeid, String.class);
	}

}
