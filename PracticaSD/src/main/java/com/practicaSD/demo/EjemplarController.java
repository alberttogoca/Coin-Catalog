package com.practicaSD.demo;

import java.sql.Date;
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

import com.practicaSD.demo.Ejemplar;
import com.practicaSD.demo.RepositorioEjemplar;
import com.practicaSD.demo.RepositorioMoneda;
import com.practicaSD.demo.RepositorioProveedor;
@Controller
public class EjemplarController {
	
	private int yearAux;
	private String estadoAux;
	private Date fechaAux;
	private String ciudadAux;
		
	@Autowired
	private RepositorioEjemplar repositorioEjemplar;
	@Autowired
	private RepositorioMoneda repositorioMoneda;
	@Autowired
	private RepositorioProveedor repositorioProveedor;
	
	@RequestMapping("/Ejemplares")
	public String ejemplares(Model model) {
		model.addAttribute("ejemplares", repositorioEjemplar.findAll());	
		return "Ejemplares";
	}
	@RequestMapping(value="/aniadirEjemplar",method=RequestMethod.GET)
	public String ejemplarAniadir(Model model) {
		Ejemplar ejemplar = new Ejemplar();
		List<Moneda> monedas = repositorioMoneda.findAll();
		List<Proveedor> proveedores=repositorioProveedor.findAll();
		model.addAttribute("ejemplar", ejemplar);
		model.addAttribute("monedas", monedas);
		model.addAttribute("proveedores", proveedores);
		return "AniadirNuevoEjemplar";
	}
	@RequestMapping(value="/aniadirEjemplar", method=RequestMethod.POST)
	public String ejemplarAniadir(@Valid @ModelAttribute("ejemplar") Ejemplar ejemplar, BindingResult bindingResult,
				Model model) {
			
		if(bindingResult.hasErrors()) {
				List<Moneda> monedas =repositorioMoneda.findAll();
				List<Proveedor> proveedores=repositorioProveedor.findAll();
				model.addAttribute("ejemplar", ejemplar);
				model.addAttribute("monedas", monedas);
				model.addAttribute("proveedores", proveedores);
				return "AniadirNuevoEjemplar";
		}else {
				repositorioEjemplar.save(new Ejemplar(ejemplar.getYear(),ejemplar.getCiudad(),ejemplar.getFecha(),ejemplar.getEstado(),
				ejemplar.getMoneda(),ejemplar.getProveedor()));		
				return "OperacionCorrectaEjemplar";
		}
	}
	@RequestMapping(value="/editarEjemplar",method=RequestMethod.GET)
	public String editarEjemplar(@RequestParam(value = "year") int year, @RequestParam(value = "ciudad") String ciudad,
			@RequestParam(value= "fecha") Date fecha, @RequestParam(value= "estado") String estado ,Model model) {

		List<Moneda> monedas =repositorioMoneda.findAll();
		List<Proveedor> proveedores=repositorioProveedor.findAll();
		Ejemplar ejemplar = repositorioEjemplar.findByYearAndCiudadAndFechaAndEstado(year, ciudad, fecha, estado);
		model.addAttribute("ejemplar", ejemplar);
		model.addAttribute("monedas", monedas);
		model.addAttribute("proveedores", proveedores);
		model.addAttribute("yearAnt", ejemplar.getYear());
		model.addAttribute("ciudadAnt", ejemplar.getCiudad());
		model.addAttribute("fechaAnt", ejemplar.getFecha());
		model.addAttribute("estadoAnt", ejemplar.getEstado());
		
		this.yearAux=year;
		this.ciudadAux=ciudad;
		this.fechaAux=fecha;
		this.estadoAux=estado;
		return "ModificarEjemplar";
	}
	
	@RequestMapping(value="/editarEjemplar",method=RequestMethod.POST)
	public String editarEjemplar(@Valid @ModelAttribute("ejemplar")Ejemplar ejemplar, BindingResult bindingResult,
			Model model) {
		
		if(bindingResult.hasErrors()) {
			List<Moneda> monedas = repositorioMoneda.findAll();
			List<Proveedor> proveedores = repositorioProveedor.findAll();
			model.addAttribute("ejemplar", ejemplar);
			model.addAttribute("moneda", monedas);
			model.addAttribute("proveedor", proveedores);

			return "ModificarEjemplar";	
        }else {
        	Ejemplar ejemplares = repositorioEjemplar.findByYearAndCiudadAndFechaAndEstado(yearAux, ciudadAux, fechaAux, estadoAux);
        	ejemplares.setYear(ejemplar.getYear());
    		ejemplares.setCiudad(ejemplar.getCiudad());
    		ejemplares.setFecha(ejemplar.getFecha());
    		ejemplares.setEstado(ejemplar.getEstado());
    		ejemplares.setProveedor(ejemplar.getProveedor());
    		ejemplares.setMoneda(ejemplar.getMoneda());
    		repositorioEjemplar.save(ejemplares);
    		return "OperacionCorrectaModificarEjemplar";
        }
     }
	
	@RequestMapping("/FiltrarEjemplar")
	public String mostrarEjemplarFiltrado(@RequestParam(value = "unidad") String unidad  ,@RequestParam(value = "valor") Double valor,Model model) {
		
		Moneda monedas = this.repositorioMoneda.findByUnidadLikeIgnoreCaseAndValorLike("%"+unidad+"%",valor);
		
		List<Ejemplar> ejemplares=repositorioEjemplar.findByMoneda(monedas);
			model.addAttribute("ejemplares", ejemplares);
			
		
		return "FiltrarEjemplar";
	}
	
	
	
	@RequestMapping("/ejemplaresOrdenarPorUnidadAscendente")
	public String ejemplaresOrdenarPorUnidadAscendente(Model model) {
		List<Ejemplar> ejemplares = repositorioEjemplar.findAll();
		ordenaUnidad(ejemplares);
		model.addAttribute("ejemplares", ejemplares);
		
		return "Ejemplares";
	}
	@RequestMapping("/ejemplaresOrdenarPorUnidadDescendente")
	public String ejemplaresOrdenarPorUnidadDescendente(Model model) {
		
		List<Ejemplar> ejemplares = repositorioEjemplar.findAll();
		ordenaUnidad(ejemplares);
		Collections.reverse(ejemplares);
		model.addAttribute("ejemplares", ejemplares);
		
		return "Ejemplares";
	}
	
	
	@RequestMapping("/ejemplaresOrdenarPorValorAscendente")
	public String ejemplaresOrdenarPorValorAscendente(Model model) {
		
		List<Ejemplar> ejemplares = repositorioEjemplar.findAll();
		ordenaValor(ejemplares);
		model.addAttribute("ejemplares", ejemplares);
		
		return "Ejemplares";
	}
	
	@RequestMapping("/ejemplaresOrdenarPorValorDescendente")
	public String ejemplaresOrdenarPorValorDescendente(Model model) {
		
		List<Ejemplar> ejemplares = repositorioEjemplar.findAll();
		ordenaValor(ejemplares);
		Collections.reverse(ejemplares);
		model.addAttribute("ejemplares", ejemplares);
		
		return "Ejemplares";
	}	
	
	
	@RequestMapping("/ejemplaresOrdenarPorProveedorAscendente")
	public String ejemplaresOrdenarPorProveedorAscendente(Model model) {
		
		List<Ejemplar> ejemplares = repositorioEjemplar.findAll();
		ordenaProveedor(ejemplares);
		model.addAttribute("ejemplares", ejemplares);
		
		return "Ejemplares";
	}
	
	@RequestMapping("/ejemplaresOrdenarPorProveedorDescendente")
	public String ejemplaresOrdenarPorProveedorDescendente(Model model) {
		
		List<Ejemplar> ejemplares = repositorioEjemplar.findAll();
		ordenaProveedor(ejemplares);
		Collections.reverse(ejemplares);
		model.addAttribute("ejemplares", ejemplares);
		
		return "Ejemplares";
	}
	

	
	public void ordenaUnidad(List<Ejemplar> ejemplares) {
		Ejemplar aux=new Ejemplar();
		Collator comparador=Collator.getInstance();
		comparador.setStrength(Collator.PRIMARY);
		
		for (int i=0;i<ejemplares.size()-1;i++) {
			for (int j=0;j<ejemplares.size()-1;j++) {
				if(comparador.compare(ejemplares.get(j+1).getMoneda().getUnidad(), ejemplares.get(j).getMoneda().getUnidad())==-1) {
					
					aux.setMoneda(ejemplares.get(j+1).getMoneda());
					aux.setProveedor(ejemplares.get(j+1).getProveedor());
					aux.setCiudad(ejemplares.get(j+1).getCiudad());
					aux.setYear(ejemplares.get(j+1).getYear());
					aux.setFecha(ejemplares.get(j+1).getFecha());
					aux.setEstado(ejemplares.get(j+1).getEstado());
					
					ejemplares.get(j+1).setMoneda(ejemplares.get(j).getMoneda());
					ejemplares.get(j+1).setProveedor(ejemplares.get(j).getProveedor());
					ejemplares.get(j+1).setCiudad(ejemplares.get(j).getCiudad());
					ejemplares.get(j+1).setYear(ejemplares.get(j).getYear());
					ejemplares.get(j+1).setFecha(ejemplares.get(j).getFecha());
					ejemplares.get(j+1).setEstado(ejemplares.get(j).getEstado());
					
					ejemplares.get(j).setMoneda(aux.getMoneda()); 
					ejemplares.get(j).setProveedor(aux.getProveedor());
					ejemplares.get(j).setCiudad(aux.getCiudad());
					ejemplares.get(j).setYear(aux.getYear());
					ejemplares.get(j).setFecha(aux.getFecha());
					ejemplares.get(j).setEstado(aux.getEstado()); 
				}
			}
		}
	}
	

	public void ordenaValor(List<Ejemplar> ejemplares) {
		Ejemplar aux=new Ejemplar();
		
		for (int i=0;i<ejemplares.size()-1;i++) {
			for (int j=0;j<ejemplares.size()-1;j++) {
				if(ejemplares.get(j+1).getMoneda().getValor()<ejemplares.get(j).getMoneda().getValor()) {
					
					aux.setMoneda(ejemplares.get(j+1).getMoneda());
					aux.setProveedor(ejemplares.get(j+1).getProveedor());
					aux.setCiudad(ejemplares.get(j+1).getCiudad());
					aux.setYear(ejemplares.get(j+1).getYear());
					aux.setFecha(ejemplares.get(j+1).getFecha());
					aux.setEstado(ejemplares.get(j+1).getEstado());
					
					ejemplares.get(j+1).setMoneda(ejemplares.get(j).getMoneda());
					ejemplares.get(j+1).setProveedor(ejemplares.get(j).getProveedor());
					ejemplares.get(j+1).setCiudad(ejemplares.get(j).getCiudad());
					ejemplares.get(j+1).setYear(ejemplares.get(j).getYear());
					ejemplares.get(j+1).setFecha(ejemplares.get(j).getFecha());
					ejemplares.get(j+1).setEstado(ejemplares.get(j).getEstado());
					
					ejemplares.get(j).setMoneda(aux.getMoneda());
					ejemplares.get(j).setProveedor(aux.getProveedor());
					ejemplares.get(j).setCiudad(aux.getCiudad());
					ejemplares.get(j).setYear(aux.getYear());
					ejemplares.get(j).setFecha(aux.getFecha());
					ejemplares.get(j).setEstado(aux.getEstado()); 
				}
			}
		}
	}
	
	public void ordenaProveedor(List<Ejemplar> ejemplares) {
		Ejemplar aux=new Ejemplar();
		Collator comparador=Collator.getInstance();
		comparador.setStrength(Collator.PRIMARY);
		
		for (int i=0;i<ejemplares.size()-1;i++) {
			for (int j=0;j<ejemplares.size()-1;j++) {
				if(comparador.compare(ejemplares.get(j+1).getProveedor().getNombre(), ejemplares.get(j).getProveedor().getNombre())==-1) {
					
					aux.setMoneda(ejemplares.get(j+1).getMoneda());
					aux.setProveedor(ejemplares.get(j+1).getProveedor());
					aux.setCiudad(ejemplares.get(j+1).getCiudad());
					aux.setYear(ejemplares.get(j+1).getYear());
					aux.setFecha(ejemplares.get(j+1).getFecha());
					aux.setEstado(ejemplares.get(j+1).getEstado());
					
					ejemplares.get(j+1).setMoneda(ejemplares.get(j).getMoneda());
					ejemplares.get(j+1).setProveedor(ejemplares.get(j).getProveedor());
					ejemplares.get(j+1).setCiudad(ejemplares.get(j).getCiudad());
					ejemplares.get(j+1).setYear(ejemplares.get(j).getYear());
					ejemplares.get(j+1).setFecha(ejemplares.get(j).getFecha());
					ejemplares.get(j+1).setEstado(ejemplares.get(j).getEstado());
					
					ejemplares.get(j).setMoneda(aux.getMoneda()); 
					ejemplares.get(j).setProveedor(aux.getProveedor());
					ejemplares.get(j).setCiudad(aux.getCiudad());
					ejemplares.get(j).setYear(aux.getYear());
					ejemplares.get(j).setFecha(aux.getFecha());
					ejemplares.get(j).setEstado(aux.getEstado()); 
				}
			}
		}
	}
}