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

}
