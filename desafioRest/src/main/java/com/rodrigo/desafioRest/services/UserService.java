package com.rodrigo.desafioRest.services;

import org.springframework.web.client.RestTemplate;

import com.rodrigo.desafioRest.entities.Usuarios;

public class UserService {
	private RestTemplate restTemplate;
	
    public UserService(RestTemplate restTemplate){
    	this.restTemplate = restTemplate;
    }
    
    public Usuarios[] getUsers() {
    	return restTemplate.getForObject("http://localhost:3000/usuarios", Usuarios[].class);
    }
}
