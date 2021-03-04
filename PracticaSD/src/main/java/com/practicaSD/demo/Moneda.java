package com.practicaSD.demo;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import java.util.ArrayList;

@Entity
public class Moneda {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long idMoneda;
	
	private double valor;
	
	private String unidad;
	
	private double diametro;
	
	private double peso;
	
	private ArrayList<String> metales = new ArrayList<>();
	
	private String descripcion;
	
	
	public Moneda() {}
	
	public Moneda(double valor,String unidad,double diametro,double peso,ArrayList<String> metales,String descripcion) {
		this.valor=valor;
		this.unidad=unidad;
		this.diametro=diametro;
		this.peso=peso;
		this.metales=metales;
		this.descripcion=descripcion;
		
	}
	
	
	//Metodos GET
	public Long getIdMoneda() {
		return idMoneda;
	}
	public double getValor() {
		return valor;
	}
	
	public String getUnidad() {
		return unidad;
	}
	
	public double getDiametro() {
		return diametro;
	}
	
	public double getPeso() {
		return peso;
	}
	
	public ArrayList<String> getMetales() {
		return metales;
	}
	
	public String getDescripcion() {
		return descripcion;
	}
	
	//Metodos SET
	public void setIdMoneda(Long id) {
		this.idMoneda=id;
	}
	public void setValor(double valor) {
		this.valor= valor;
	}
	
	public void setUnidad(String unidad) {
		this.unidad= unidad;
	}
	
	public void setDiametro(double diametro) {
		this.diametro= diametro;
	}
	
	public void setPeso(double peso) {
		this.peso= peso;
	}
	
	public void setMetales(ArrayList<String> metales) {
		this.metales= metales;
	}
	
	public void setDescripcion(String descripcion) {
		this.descripcion= descripcion;
	}
	
}
