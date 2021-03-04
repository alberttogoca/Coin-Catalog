package com.practicaSD.demo;

import java.text.Collator;
import java.util.Collections;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.practicaSD.demo.Moneda;
import com.practicaSD.demo.Ejemplar;

import com.practicaSD.demo.RepositorioEjemplar;
import com.practicaSD.demo.RepositorioMoneda;


@Controller
public class MonedaController {
	
	private String unidadAux;
	private double valorAux;

	@Autowired
	private RepositorioMoneda RepositorioMoneda;
	
	@Autowired
	private RepositorioEjemplar repositorioEjemplar;

	@RequestMapping("/Monedas")
	public String monedas(Model model) {
		model.addAttribute("monedas", RepositorioMoneda.findAll());	
		return "Monedas";
	}
	@RequestMapping(value="/aniadirMoneda",method=RequestMethod.GET)
	public String nuevaMoneda(Model model) {
		Moneda moneda = new Moneda();
		model.addAttribute("moneda", moneda);		
		return "AniadirNuevaMoneda";
	}
	@RequestMapping(value="/aniadirMoneda",method=RequestMethod.POST)
	public String insertarMoneda(@Valid @ModelAttribute("moneda") Moneda moneda,BindingResult bindingResult,
			Model model) {		
		if(bindingResult.hasErrors()) {
			model.addAttribute("moneda", moneda);
			return "AniadirNuevaMoneda";
		}
		RepositorioMoneda.save(new Moneda(moneda.getValor(),moneda.getUnidad(),moneda.getDiametro(),moneda.getPeso(),moneda.getMetales(),moneda.getDescripcion()));

		return "OperacionCorrectaMoneda";
	}
	
	@RequestMapping(value="/editarMoneda", method = RequestMethod.GET)
	public String editarMoneda(@RequestParam(value = "unidad") String unidad,@RequestParam(value = "valor") double valor,Model model) {

		Moneda moneda = this.RepositorioMoneda.findByUnidadLikeIgnoreCaseAndValorLike(unidad,valor);
		model.addAttribute("moneda", moneda);
		model.addAttribute("valorAnt", moneda.getValor());
		model.addAttribute("unidadAnt", moneda.getUnidad());
		model.addAttribute("diametroAnt", moneda.getDiametro());
		model.addAttribute("pesoAnt", moneda.getPeso());
		model.addAttribute("metalesAnt", moneda.getMetales());
		model.addAttribute("descripcionAnt", moneda.getDescripcion());
		
		this.unidadAux = unidad;
		this.valorAux = valor;
		return "ModificarMoneda";
	}
	
	@RequestMapping(value="/editarMoneda", method = RequestMethod.POST)
	public String editarMoneda(@Valid @ModelAttribute("moneda") Moneda moneda ,
			BindingResult bindingResult,Model model) {
			
		
		if(bindingResult.hasErrors()) {
			model.addAttribute("moneda", moneda);

			return "ModificarMoneda";
		}
		
		Moneda aux = RepositorioMoneda.findByUnidadLikeIgnoreCaseAndValorLike(this.unidadAux,this.valorAux);
		List<Ejemplar> ejemplares =repositorioEjemplar.findByMoneda(aux);
		aux.setUnidad(moneda.getUnidad());
		aux.setValor(moneda.getValor());
		aux.setDiametro(moneda.getDiametro());
		aux.setPeso(moneda.getPeso());
		aux.setMetales(moneda.getMetales());
		aux.setDescripcion(moneda.getDescripcion());
		for(Ejemplar ejemplar:ejemplares) {
			ejemplar.setMoneda(aux);
			repositorioEjemplar.save(ejemplar);
		}
		
		RepositorioMoneda.save(aux);
		
		return "OperacionCorrectaModificarMoneda";
			
	}
	
	@RequestMapping("/FiltrarMoneda")
	public String mostrarMonedaFiltrado(@RequestParam(value = "unidad") String unidad,Model model) {
		List<Moneda> monedas = this.RepositorioMoneda.findByUnidadLikeIgnoreCase("%"+unidad+"%");
		model.addAttribute("monedas", monedas);
		return "FiltrarMoneda";
	}
	
	
	@RequestMapping("/monedasOrdenarPorUnidadAscendente")
	public String monedasOrdenarPorUnidadAscendente(Model model) {
		List<Moneda> monedas = RepositorioMoneda.findAll();
		ordenaUnidad(monedas);
		model.addAttribute("monedas", monedas);
		
		return "Monedas";
	}
	@RequestMapping("/monedasOrdenarPorUnidadDescendente")
	public String monedasOrdenarPorUnidadDescendente(Model model) {
		
		List<Moneda> monedas = RepositorioMoneda.findAll();
		ordenaUnidad(monedas);
		Collections.reverse(monedas);
		model.addAttribute("monedas", monedas);
		
		return "Monedas";
	}

	@RequestMapping("/monedasOrdenarPorValorAscendente")
	public String monedasOrdenarPorValorAscendente(Model model) {
		List<Moneda> monedas = RepositorioMoneda.findAll();
		ordenaValor(monedas);
		model.addAttribute("monedas", monedas);
		
		return "Monedas";
	}
	@RequestMapping("/monedasOrdenarPorValorDescendente")
	public String monedasOrdenarPorValorDescendente(Model model) {
		
		List<Moneda> monedas = RepositorioMoneda.findAll();
		ordenaValor(monedas);
		Collections.reverse(monedas);
		model.addAttribute("monedas", monedas);
		
		return "Monedas";
	}
	
	@RequestMapping("/monedasOrdenarPorDiametroAscendente")
	public String monedasOrdenarPorDiametroAscendente(Model model) {
		List<Moneda> monedas = RepositorioMoneda.findAll();
		ordenaDiametro(monedas);
		model.addAttribute("monedas", monedas);
		
		return "Monedas";
	}
	@RequestMapping("/monedasOrdenarPorDiametroDescendente")
	public String monedasOrdenarPorDiametroDescendente(Model model) {
		
		List<Moneda> monedas = RepositorioMoneda.findAll();
		ordenaDiametro(monedas);
		Collections.reverse(monedas);
		model.addAttribute("monedas", monedas);
		
		return "Monedas";
	}
	
	public void ordenaUnidad(List<Moneda> monedas) {
		Moneda aux=new Moneda();
		Collator comparador=Collator.getInstance();
		comparador.setStrength(Collator.PRIMARY);
		
		for (int i=0;i<monedas.size()-1;i++) {
			for (int j=0;j<monedas.size()-1;j++) {
				if(comparador.compare(monedas.get(j+1).getUnidad(), monedas.get(j).getUnidad())==-1) {
					
					aux.setValor(monedas.get(j+1).getValor());
					aux.setUnidad(monedas.get(j+1).getUnidad());
					aux.setDiametro(monedas.get(j+1).getDiametro());
					aux.setPeso(monedas.get(j+1).getPeso());
					aux.setMetales(monedas.get(j+1).getMetales());
					aux.setDescripcion(monedas.get(j+1).getDescripcion());
					
					monedas.get(j+1).setValor(monedas.get(j).getValor());
					monedas.get(j+1).setUnidad(monedas.get(j).getUnidad());
					monedas.get(j+1).setDiametro(monedas.get(j).getDiametro());
					monedas.get(j+1).setPeso(monedas.get(j).getPeso());
					monedas.get(j+1).setMetales(monedas.get(j).getMetales());
					monedas.get(j+1).setDescripcion(monedas.get(j).getDescripcion());

					
					monedas.get(j).setValor(aux.getValor());
					monedas.get(j).setUnidad(aux.getUnidad());
					monedas.get(j).setDiametro(aux.getDiametro());
					monedas.get(j).setPeso(aux.getPeso());
					monedas.get(j).setMetales(aux.getMetales());
					monedas.get(j).setDescripcion(aux.getDescripcion());
				}
			}
		}
	}
	

	public void ordenaValor(List<Moneda> monedas) {
		Moneda aux=new Moneda();
		
		for (int i=0;i<monedas.size()-1;i++) {
			for (int j=0;j<monedas.size()-1;j++) {
				if(monedas.get(j+1).getValor()<monedas.get(j).getValor()) {
					
					aux.setValor(monedas.get(j+1).getValor());
					aux.setUnidad(monedas.get(j+1).getUnidad());
					aux.setDiametro(monedas.get(j+1).getDiametro());
					aux.setPeso(monedas.get(j+1).getPeso());
					aux.setMetales(monedas.get(j+1).getMetales());
					aux.setDescripcion(monedas.get(j+1).getDescripcion());
					
					monedas.get(j+1).setValor(monedas.get(j).getValor());
					monedas.get(j+1).setUnidad(monedas.get(j).getUnidad());
					monedas.get(j+1).setDiametro(monedas.get(j).getDiametro());
					monedas.get(j+1).setPeso(monedas.get(j).getPeso());
					monedas.get(j+1).setMetales(monedas.get(j).getMetales());
					monedas.get(j+1).setDescripcion(monedas.get(j).getDescripcion());

					
					monedas.get(j).setValor(aux.getValor());
					monedas.get(j).setUnidad(aux.getUnidad());
					monedas.get(j).setDiametro(aux.getDiametro());
					monedas.get(j).setPeso(aux.getPeso());
					monedas.get(j).setMetales(aux.getMetales());
					monedas.get(j).setDescripcion(aux.getDescripcion());
				}
			}
		}
	}
	
	public void ordenaDiametro(List<Moneda> monedas) {
		Moneda aux=new Moneda();
		
		for (int i=0;i<monedas.size()-1;i++) {
			for (int j=0;j<monedas.size()-1;j++) {
				if(monedas.get(j+1).getDiametro()<monedas.get(j).getDiametro()) {
					
					aux.setValor(monedas.get(j+1).getValor());
					aux.setUnidad(monedas.get(j+1).getUnidad());
					aux.setDiametro(monedas.get(j+1).getDiametro());
					aux.setPeso(monedas.get(j+1).getPeso());
					aux.setMetales(monedas.get(j+1).getMetales());
					aux.setDescripcion(monedas.get(j+1).getDescripcion());
					
					monedas.get(j+1).setValor(monedas.get(j).getValor());
					monedas.get(j+1).setUnidad(monedas.get(j).getUnidad());
					monedas.get(j+1).setDiametro(monedas.get(j).getDiametro());
					monedas.get(j+1).setPeso(monedas.get(j).getPeso());
					monedas.get(j+1).setMetales(monedas.get(j).getMetales());
					monedas.get(j+1).setDescripcion(monedas.get(j).getDescripcion());

					
					monedas.get(j).setValor(aux.getValor());
					monedas.get(j).setUnidad(aux.getUnidad());
					monedas.get(j).setDiametro(aux.getDiametro());
					monedas.get(j).setPeso(aux.getPeso());
					monedas.get(j).setMetales(aux.getMetales());
					monedas.get(j).setDescripcion(aux.getDescripcion());
				}
			}
		}
	}
	
	
}