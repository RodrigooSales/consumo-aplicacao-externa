package com.rodrigo.desafioRest.entities;

public class Usuarios {
	private String id;
	private String name;
	private Office cargo;
	public Usuarios() {
		super();
	}
	public Usuarios(String id, String name, Office cargo) {
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
	public Office getCargo() {
		return cargo;
	}
	public void setCargo(Office cargo) {
		this.cargo = cargo;
	}
	
	
}
