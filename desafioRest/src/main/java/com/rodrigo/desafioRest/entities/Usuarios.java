package com.rodrigo.desafioRest.entities;

public class Usuarios {
	private String id;
	private String name;
	public Usuarios() {
		super();
	}
	public Usuarios(String id, String name) {
		super();
		this.id = id;
		this.name = name;
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
	
}
