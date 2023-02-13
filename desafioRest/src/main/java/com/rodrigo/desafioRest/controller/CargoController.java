package com.rodrigo.desafioRest.controller;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rodrigo.desafioRest.entities.Cargo;
import com.rodrigo.desafioRest.entities.Usuarios;
import com.rodrigo.desafioRest.repository.CargoRepository;
import com.rodrigo.desafioRest.services.UserService;

@RestController
@RequestMapping(value = "/cargos")
public class CargoController {
	private CargoRepository cargoRepository;
	private UserService userService;
	
	public CargoController(CargoRepository cargoRepository, UserService userService) {
		this.cargoRepository = cargoRepository;
		this.userService = userService;
	}
	
	@GetMapping
	public Iterable<Cargo> getCargos(){
		return cargoRepository.findAll();
	}
	
	@PostMapping
	public Cargo createCargo(@RequestBody Cargo cargo) {
		return cargoRepository.save(cargo);
	}
	
	@PutMapping("/{id}")
	public Cargo updateCargo(@PathVariable Long id, @RequestBody Cargo cargo) {
		cargo.setId(id);
		return cargoRepository.save(cargo);
	}
	
	@DeleteMapping("/{id}")
	public void deleteCargo(@PathVariable Long id) {
		cargoRepository.deleteById(id);
	}
	
	
}
