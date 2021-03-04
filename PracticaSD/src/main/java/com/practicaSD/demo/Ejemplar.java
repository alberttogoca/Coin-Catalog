package com.practicaSD.demo;

import java.sql.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.practicaSD.demo.Proveedor;

@Entity
public class Ejemplar {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private long id;
	
	@ManyToOne
	@JoinColumn(name = "idMoneda")
	private Moneda moneda;
	@ManyToOne
	@JoinColumn(name = "idProveedor")
	private Proveedor proveedor;
	private int year;
	private String ciudad;
	private Date fecha;
	private String estado;
	
	public Ejemplar() {}
	
	public Ejemplar(int year,String ciudad,Date fecha,String estado, Moneda moneda, Proveedor proveedor) {
		this.year=year;
		this.ciudad=ciudad;
		this.fecha=fecha;
		this.estado=estado;
		this.proveedor=proveedor;
		this.moneda=moneda;
	}
	
// Metodos GET
		public long getID() {
			return id;
		}

		public int getYear() {
			return year;
		}

		public String getCiudad() {
			return ciudad;
		}

		public Date getFecha() {
			return fecha;
		}

		public String getEstado() {
			return estado;
		}
		
		public Moneda getMoneda(){
			return moneda;
		}
		
		public Proveedor getProveedor(){
			return proveedor;
		}
		
// Metodos SET
		public void setYear(int year) {
				this.year = year;
			
		}

		public void setCiudad(String ciudad) {
			if (ciudad != null) {
				this.ciudad = ciudad;
			}
		}

		public void setFecha(Date fecha) {
				this.fecha=fecha;
		}

		public void setEstado(String estado) {
			if (estado!=null) {
				this.estado = estado;
			}
		}
		
		public void setMoneda(Moneda moneda) {
			this.moneda=moneda;
		}
		
		public void setProveedor(Proveedor proveedor) {
			this.proveedor=proveedor;
		}

}
