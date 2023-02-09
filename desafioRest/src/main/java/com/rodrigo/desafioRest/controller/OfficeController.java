package com.rodrigo.desafioRest.controller;

import com.rodrigo.desafioRest.entities.Office;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.rodrigo.desafioRest.services.OfficeService;

import java.util.List;

@RestController
@RequestMapping(value = "/cargos")
public class OfficeController {
	private OfficeService officeService;

	@Autowired
	public OfficeController(OfficeService officeService) {

		this.officeService = officeService;
	}

	@PostMapping
	public Office criarCargo(@RequestBody Office office) {

		return officeService.creatOffice(office);
	}

	@PutMapping
	public Office atualizarCargo(@RequestBody Office office) {
		return officeService.updateOffice(office);
	}

	@DeleteMapping("/{id}")
	public void excluirCargo(@PathVariable Long id) {

		officeService.deleteOffice(id);
	}
	@GetMapping("/{id}")
	public Office obterCargoPorId(@PathVariable Long id) {
		return officeService.getById(id);
	}

	@GetMapping
	public List<Office> obterTodosCargos() {
		return officeService.getAll();
	}
}
