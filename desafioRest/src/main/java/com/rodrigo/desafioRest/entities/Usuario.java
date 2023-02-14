package com.rodrigo.desafioRest.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table
public class Usuario implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
//	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Id
	@Column
	private String id;

	@Column
	private String name;
	
	@ManyToOne
	@JoinColumn(name = "cargo_id")
	private Cargo cargo;

	public Usuario() {
		super();
	}

	public Usuario(String id, String name, Cargo cargo) {
		super();
		this.id = id;
		this.name = name;
		this.cargo = cargo;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Cargo getCargo() {
		return cargo;
	}

	public void setCargo(Cargo cargo) {
		this.cargo = cargo;
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
		Usuario other = (Usuario) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	
}
