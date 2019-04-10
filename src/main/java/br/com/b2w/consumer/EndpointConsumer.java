package br.com.b2w.consumer;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
/**
 * 
 * @author julio
 * @since 2019-03-26
 * @version 1.0.0
 * 
 */
@Service
public class EndpointConsumer {

	public String getItemsByEndpoint() {
		RestTemplate template = new RestTemplate();
		return template.getForObject("http://www.mocky.io/v2/5817803a1000007d01cc7fc9", String.class);
	}

}
