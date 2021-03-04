package com.practicaSD.demo;


import org.springframework.data.jpa.repository.JpaRepository;

import com.practicaSD.demo.Proveedor;

import java.util.List;


public interface RepositorioProveedor extends JpaRepository<Proveedor, Long>{
	List<Proveedor> findByNombreLikeIgnoreCase(String nombre);
	List<Proveedor> findByCodigopostal(int codigopostal);
	List<Proveedor> findByEmail(String email);
	List<Proveedor> findByTelefono(long telefono);
	Proveedor findByCif(String cif);
	
}
