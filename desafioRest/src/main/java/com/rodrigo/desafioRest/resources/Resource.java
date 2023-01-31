package com.rodrigo.desafioRest.resources;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.rodrigo.desafioRest.entities.Office;
import com.rodrigo.desafioRest.services.OfficeService;

@RestController
@RequestMapping(value = "/cargos")
public class Resource {
	
	@GetMapping
	public ResponseEntity<Office[]> obterCargos(){
		
		
		return null;
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Office[]> obterCargo(@PathVariable String id) {
		
		Office[] cargo = OfficeService.findById(id);
        
        return ResponseEntity.ok(cargo);
	
	}
}
