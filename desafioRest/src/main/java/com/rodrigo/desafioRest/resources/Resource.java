package com.rodrigo.desafioRest.resources;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rodrigo.desafioRest.dto.Office;

@RestController
@RequestMapping(value = "/cargos")
public class Resource {
	
	@GetMapping
	public ResponseEntity<Office> findAll() {
		Office cargo = new Office(902573746l, "Jedi", "Heroi que utiliza da força para o bem");
		
		return ResponseEntity.ok().body(cargo);
	}
}
