package com.rodrigo.desafioRest.services;

import com.rodrigo.desafioRest.repository.CargoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.rodrigo.desafioRest.entities.Cargo;
import com.rodrigo.desafioRest.entities.Usuarios;

import java.util.List;

@Service
public class CargoService {
	public Cargo addUserToDepartment(Long departmentId, Usuarios user) {
		Cargo cargo = repository.findById(departmentId).orElseThrow(() -> new DepartmentNotFoundException(departmentId));
		cargo.getUsers().add(user);
		return repository.save(cargo);
	}

	public Cargo removeUserFromDepartment(Long departmentId, User user) {
		Cargo cargo = repository.findById(departmentId).orElseThrow(() -> new DepartmentNotFoundException(departmentId));
		cargo.getUsers().remove(user);
		return repository.save(department);
	}
}
