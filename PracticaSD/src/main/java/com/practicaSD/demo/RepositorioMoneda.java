package com.practicaSD.demo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.practicaSD.demo.Moneda;

public interface RepositorioMoneda extends JpaRepository <Moneda, Long> {
	Moneda findByValor (double valor);
	List<Moneda> findByUnidadLikeIgnoreCase (String unidad);
	Moneda findByUnidadLikeIgnoreCaseAndValorLike (String unidad,double valor);
	Moneda findByUnidadLike(String unidad);
}