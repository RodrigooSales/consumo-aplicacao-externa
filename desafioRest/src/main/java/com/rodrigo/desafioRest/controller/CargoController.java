package com.rodrigo.desafioRest.controller;

import com.rodrigo.desafioRest.entities.Cargo;
import com.rodrigo.desafioRest.entities.Usuario;
import com.rodrigo.desafioRest.exeception.ResourceNotFoundException;
import com.rodrigo.desafioRest.repository.CargoRepository;
import com.rodrigo.desafioRest.services.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cargos")
public class CargoController {

	@Autowired
	private CargoRepository cargoRepository;

	@Autowired
	private UsuarioService usuarioService;

	@PostMapping
	public Cargo criarCargo(@RequestBody Cargo cargo) {
		return cargoRepository.save(cargo);
	}

	@GetMapping("/{id}")
	public Cargo buscarCargoPorId(@PathVariable Long id) {
		return cargoRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Cargo não encontrado com id " + id));
	}

	@PutMapping("/{id}")
	public Cargo atualizarCargo(@PathVariable Long id, @RequestBody Cargo cargoAtualizado) {
		Cargo cargo = cargoRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Cargo não encontrado com id " + id));
		cargo.setName(cargoAtualizado.getName());
		cargo.setUsuarios(cargoAtualizado.getUsuarios());
		return cargoRepository.save(cargo);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<?> deletarCargo(@PathVariable Long id) {
		Cargo cargo = cargoRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Cargo não encontrado com id " + id));
		cargoRepository.delete(cargo);
		return ResponseEntity.ok().build();
	}

	@PostMapping("/{id}/usuarios")
	public Cargo adicionarUsuarioAoCargo(@PathVariable Long id, @RequestBody String usuarioId) {
		Cargo cargo = cargoRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Cargo não encontrado com id " + id));
		Usuario usuario = usuarioService.buscarUsuarioPorId(usuarioId);
		cargo.getUsuarios().add(usuario);
		return cargoRepository.save(cargo);
	}

	@DeleteMapping("/{id}/usuarios/{usuarioId}")
	public Cargo removerUsuarioDoCargo(@PathVariable Long id, @PathVariable String usuarioId) {
		Cargo cargo = cargoRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Cargo não encontrado com id " + id));
		Usuario usuario = usuarioService.buscarUsuarioPorId(usuarioId);
		cargo.getUsuarios().remove(usuario);
		return cargoRepository.save(cargo);
	}
}
