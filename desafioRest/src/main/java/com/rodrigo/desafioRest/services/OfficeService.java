package com.rodrigo.desafioRest.services;

import com.rodrigo.desafioRest.repository.OfficeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.rodrigo.desafioRest.entities.Office;

import java.util.List;

@Service
public class OfficeService {

	private OfficeRepository officeRepository;

	@Autowired
	public OfficeService(OfficeRepository officeRepository){
		this.officeRepository = officeRepository;
	}

	public Office creatOffice(Office office){
		return officeRepository.save(office);
	}

	public Office updateOffice(Office office){
		return officeRepository.save(office);
	}

	public void deleteOffice(Long id) {
		officeRepository.deleteById(id);
	}

	public Office getById(Long id){
		return officeRepository.findById(id).orElse(null);
	}

	public List<Office> getAll(){
		return officeRepository.findAll();
	}
}
