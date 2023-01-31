package com.rodrigo.desafioRest.entities;

import java.io.Serializable;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;


//@Entity
public class Office{
	
	//@Id
	//@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String name;
	private List<Usuarios> usuarios;
	
	public Office() {
	}

	public Office(Long id, String name, List<Usuarios> usuarios) {
		super();
		this.id = id;
		this.name = name;
		this.usuarios = usuarios;
	}
	
	public void adicionarUsuarios(List<Usuarios> usuarios) {
        this.usuarios.addAll(usuarios);
    }

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Usuarios> getUsuarios() {
		return usuarios;
	}

	public void setUsuarios(List<Usuarios> usuarios) {
		this.usuarios = usuarios;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Office other = (Office) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}
