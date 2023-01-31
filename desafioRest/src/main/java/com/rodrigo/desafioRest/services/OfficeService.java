package com.rodrigo.desafioRest.services;

import org.springframework.web.reactive.function.client.WebClient;

import com.rodrigo.desafioRest.entities.Office;

public class OfficeService {
	public static Office[] findById(String id){
		
		String url = "http://localhost:3000";
		String uri = "/usuarios/{id}";
		
		Office[] cargo = WebClient
				.create(url)
				.get()
				.uri(uri, id)
				.retrieve()
				.bodyToMono(Office[].class).block();
		
		return cargo;
		
	}
}
