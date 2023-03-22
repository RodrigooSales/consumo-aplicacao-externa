package com.rodrigo.desafioRest.controller;

import com.rodrigo.desafioRest.entities.Cargo;
import com.rodrigo.desafioRest.entities.Usuario;
import com.rodrigo.desafioRest.exeception.ExternalApiUnavailableException;
import com.rodrigo.desafioRest.exeception.ResourceNotFoundException;
import com.rodrigo.desafioRest.exeception.UserNotFoundException;
import com.rodrigo.desafioRest.repository.CargoRepository;
import com.rodrigo.desafioRest.services.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;

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

	@PostMapping("/{id}/usuarios/{usuarioId}")
	public ResponseEntity<Cargo> adicionarUsuarioAoCargo(@PathVariable Long id, @PathVariable String usuarioId) {
		Cargo cargo = cargoRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Cargo não encontrado com id " + id));
		Usuario usuario;
		try {
			usuario = usuarioService.buscarUsuarioPorId(usuarioId);
		}catch (HttpServerErrorException | HttpClientErrorException ex){
			throw new UserNotFoundException("Usuário não encontrado");
		} catch (ExternalApiUnavailableException ex) {
			throw new ExternalApiUnavailableException("API externa indisponível");
		}

		usuario.setCargo(cargo);
		cargo.getUsuarios().add(usuario);
		return ResponseEntity.ok(cargoRepository.save(cargo));
	}

	@DeleteMapping("/{id}/usuarios/{usuarioId}")
	public ResponseEntity<Cargo> removerUsuarioDoCargo(@PathVariable Long id, @PathVariable String usuarioId) {
		Cargo cargo = cargoRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Cargo não encontrado com id " + id));
		Usuario usuario;
		try {
			usuario = usuarioService.buscarUsuarioPorId(usuarioId);
		} catch (HttpServerErrorException | HttpClientErrorException ex) {
			throw new UserNotFoundException("Usuário não encontrado");
		} catch (ExternalApiUnavailableException ex) {
			throw new ExternalApiUnavailableException("API externa indisponível");
		}

		cargo.getUsuarios().remove(usuario);
		return ResponseEntity.ok(cargoRepository.save(cargo));
	}
}
