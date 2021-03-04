package com.practicaSD.demo;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Proveedor {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long idProveedor;
	private String cif;
	private String nombre;
	private int codigopostal;
	private String email;
	private long telefono;

	
	public Proveedor() {}
	
	public Proveedor(String cif, String nombre, int codigopostal, String email, long telefono) {
		this.cif = cif;
		this.nombre = nombre;
		this.codigopostal = codigopostal;
		this.email = email;
		this.telefono = telefono;
	}
	
	public Proveedor(String cif) {
		this.cif = cif;
	}

	// Metodos GET
	public Long getIdProveedor() {
		return idProveedor;
	}

	public String getCif() {
		return cif;
	}

	public String getNombre() {
		return nombre;
	}

	public int getCodigopostal() {
		return codigopostal;
	}

	public String getEmail() {
		return email;
	}

	public long getTelefono() {
		return telefono;
	}
	

	// Metodos SET
	public void setIdProveedor(Long id) {
		this.idProveedor=id;
	}
	public void setCif(String cif) {
			this.cif = cif;
	}

	public void setNombre(String nombre) {
			this.nombre = nombre;
	}

	public void setCodigopostal(int CP) {
			this.codigopostal = CP;
	}

	public void setEmail(String email) {
			this.email = email;
	}

	public void setTelefono(long telefono) {
		this.telefono=telefono;
	}
	

	
}
