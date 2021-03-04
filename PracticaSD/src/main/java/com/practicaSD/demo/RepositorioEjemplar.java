package com.practicaSD.demo;

import java.sql.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.practicaSD.demo.Ejemplar;
import com.practicaSD.demo.Moneda;
import com.practicaSD.demo.Proveedor;



public interface RepositorioEjemplar extends JpaRepository<Ejemplar, Long>{	
	List<Ejemplar> findByYear(int year);
	List<Ejemplar> findByCiudad(String ciudad);
	List<Ejemplar> findByFecha(Date fecha);
	List<Ejemplar> findByEstado(String estado);
	List<Ejemplar> findByMoneda(Moneda moneda);
	List<Ejemplar> findByProveedor(Proveedor proveedores);
	Ejemplar findByYearAndCiudadAndFechaAndEstado(int year, String ciudad, Date fecha, String estado);
}