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

import com.practicaSD.demo.RepositorioEjemplar;
import com.practicaSD.demo.RepositorioProveedor;

@Controller
public class ProveedorController{

	private String proveedorAux;
	
	@Autowired
	private RepositorioProveedor repositorioProveedor;
	
	@Autowired
	private RepositorioEjemplar repositorioEjemplar;
	
	@RequestMapping("/Proveedores")
	public String mostrarProveedores(Model model) {
		
		model.addAttribute("proveedores", repositorioProveedor.findAll() );
		
		return "Proveedores";
	}
	
	@RequestMapping(value="/aniadirProveedor",method=RequestMethod.GET)
	public String nuevoProveedor(Model model) {
		Proveedor proveedor = new Proveedor();
		model.addAttribute("proveedor", proveedor);
		
		return "AniadirNuevoProveedor";
	}
	
	@RequestMapping(value="/aniadirProveedor",method=RequestMethod.POST)
	public String insertarProveedor(@Valid @ModelAttribute("proveedor") Proveedor proveedor,BindingResult bindingResult,
			Model model) {
		
		if(bindingResult.hasErrors()) {
			model.addAttribute("proveedor", proveedor);
			return "AniadirNuevoProveedor";
		}
		repositorioProveedor.save(new Proveedor(proveedor.getCif(),proveedor.getNombre(),proveedor.getCodigopostal(),proveedor.getEmail(),proveedor.getTelefono()));

		return "OperacionCorrectaProveedor";
	}
	
	
	@RequestMapping(value="/editarProveedor",method=RequestMethod.GET)
	public String editarProveedor(@RequestParam(value = "cif") String cif, Model model) {

		Proveedor proveedor = this.repositorioProveedor.findByCif(cif);
		model.addAttribute("proveedor", proveedor);
		model.addAttribute("CIFAnt", proveedor.getCif());
		model.addAttribute("nombreAnt", proveedor.getNombre());
		model.addAttribute("CPAnt", proveedor.getCodigopostal());
		model.addAttribute("emailAnt", proveedor.getEmail());
		model.addAttribute("telefonoAnt", proveedor.getTelefono());
		
		this.proveedorAux = cif;
		return "ModificarProveedor";
	}
	
	@RequestMapping(value="/editarProveedor",method=RequestMethod.POST)
	public String insertarEdicionProveedor(@Valid @ModelAttribute("proveedor") Proveedor proveedor ,
			BindingResult bindingResult,Model model) {
			
		
		if(bindingResult.hasErrors()) {
			model.addAttribute("proveedor", proveedor);
			return "ModificarProveedor";
		}
		
		Proveedor aux = repositorioProveedor.findByCif(this.proveedorAux);
		List<Ejemplar> ejemplares =repositorioEjemplar.findByProveedor(aux);
		aux.setCif(proveedor.getCif());
		aux.setNombre(proveedor.getNombre());
		aux.setCodigopostal(proveedor.getCodigopostal());
		aux.setEmail(proveedor.getEmail());
		aux.setTelefono(proveedor.getTelefono());
		for(Ejemplar ejemplar:ejemplares) {
			ejemplar.setProveedor(aux);
			repositorioEjemplar.save(ejemplar);
		}
		
		repositorioProveedor.save(aux);
		
		return "OperacionCorrectaModificarProveedor";
			
	}
	@RequestMapping("/FiltrarProveedores")
	public String mostrarProveedorFiltrado(@RequestParam(value = "nombre") String nombre,Model model) {
		List<Proveedor> proveedores = this.repositorioProveedor.findByNombreLikeIgnoreCase("%"+nombre+"%");
		model.addAttribute("proveedores", proveedores);
		return "FiltrarProveedores";
	}
	
	
	@RequestMapping("/proveedorOrdenarPorCIFAscendente")
	public String proveedorOrdenarPorCIFAscendente(Model model) {
		List<Proveedor> proveedores = repositorioProveedor.findAll();
		ordenaCIF(proveedores);
		model.addAttribute("proveedores", proveedores);
		
		return "Proveedores";
	}
	
	@RequestMapping("/proveedorOrdenarPorCIFDescendente")
	public String proveedorOrdenarPorCIFDescendente(Model model) {
		List<Proveedor> proveedores = repositorioProveedor.findAll();
		ordenaCIF(proveedores);
		Collections.reverse(proveedores);
		model.addAttribute("proveedores", proveedores);
		
		return "Proveedores";
	}
	
	@RequestMapping("/proveedorOrdenarPorNombreAscendente")
	public String proveedorOrdenarPorNombreAscendente(Model model) {
		List<Proveedor> proveedores = repositorioProveedor.findAll();
		ordenaNombre(proveedores);
		model.addAttribute("proveedores", proveedores);
		
		return "Proveedores";
	}
	
	@RequestMapping("/proveedorOrdenarPorNombreDescendente")
	public String proveedorOrdenarPorNombreDescendente(Model model) {
		List<Proveedor> proveedores = repositorioProveedor.findAll();
		ordenaNombre(proveedores);
		Collections.reverse(proveedores);
		model.addAttribute("proveedores", proveedores);
		
		return "Proveedores";
	}

	
	@RequestMapping("/proveedorOrdenarPorCPAscendente")
	public String proveedorOrdenarPorCPAscendente(Model model) {
		List<Proveedor> proveedores = repositorioProveedor.findAll();
		ordenaCP(proveedores);
		model.addAttribute("proveedores", proveedores);
		
		return "Proveedores";
	}
	
	@RequestMapping("/proveedorOrdenarPorCPDescendente")
	public String proveedorOrdenarPorCPDescendente(Model model) {
		List<Proveedor> proveedores = repositorioProveedor.findAll();
		ordenaCP(proveedores);
		Collections.reverse(proveedores);
		model.addAttribute("proveedores", proveedores);
		
		return "Proveedores";
	}
	
	public void ordenaCIF(List<Proveedor> proveedores) {
		Proveedor aux=new Proveedor();
		Collator comparador=Collator.getInstance();
		comparador.setStrength(Collator.PRIMARY);
		
		for (int i=0;i<proveedores.size()-1;i++) {
			for (int j=0;j<proveedores.size()-1;j++) {
				if(comparador.compare(proveedores.get(j+1).getCif(), proveedores.get(j).getCif())==-1) {
					
					aux.setCif(proveedores.get(j+1).getCif());
					aux.setNombre(proveedores.get(j+1).getNombre());
					aux.setCodigopostal(proveedores.get(j+1).getCodigopostal());
					aux.setEmail(proveedores.get(j+1).getEmail());
					aux.setTelefono(proveedores.get(j+1).getTelefono());
					
					proveedores.get(j+1).setCif(proveedores.get(j).getCif());
					proveedores.get(j+1).setNombre(proveedores.get(j).getNombre());
					proveedores.get(j+1).setCodigopostal(proveedores.get(j).getCodigopostal());
					proveedores.get(j+1).setEmail(proveedores.get(j).getEmail());
					proveedores.get(j+1).setTelefono(proveedores.get(j).getTelefono());

					
					proveedores.get(j).setCif(aux.getCif());
					proveedores.get(j).setNombre(aux.getNombre());
					proveedores.get(j).setCodigopostal(aux.getCodigopostal());
					proveedores.get(j).setEmail(aux.getEmail());
					proveedores.get(j).setTelefono(aux.getTelefono());

				}
			}
		}
	}
	
	public void ordenaNombre(List<Proveedor> proveedores) {
		Proveedor aux=new Proveedor();
		Collator comparador=Collator.getInstance();
		comparador.setStrength(Collator.PRIMARY);
		
		for (int i=0;i<proveedores.size()-1;i++) {
			for (int j=0;j<proveedores.size()-1;j++) {
				if(comparador.compare(proveedores.get(j+1).getNombre(), proveedores.get(j).getNombre())==-1) {
					
					aux.setCif(proveedores.get(j+1).getCif());
					aux.setNombre(proveedores.get(j+1).getNombre());
					aux.setCodigopostal(proveedores.get(j+1).getCodigopostal());
					aux.setEmail(proveedores.get(j+1).getEmail());
					aux.setTelefono(proveedores.get(j+1).getTelefono());
					
					proveedores.get(j+1).setCif(proveedores.get(j).getCif());
					proveedores.get(j+1).setNombre(proveedores.get(j).getNombre());
					proveedores.get(j+1).setCodigopostal(proveedores.get(j).getCodigopostal());
					proveedores.get(j+1).setEmail(proveedores.get(j).getEmail());
					proveedores.get(j+1).setTelefono(proveedores.get(j).getTelefono());

					
					proveedores.get(j).setCif(aux.getCif());
					proveedores.get(j).setNombre(aux.getNombre());
					proveedores.get(j).setCodigopostal(aux.getCodigopostal());
					proveedores.get(j).setEmail(aux.getEmail());
					proveedores.get(j).setTelefono(aux.getTelefono());

				}
			}
		}
	}
	
	
	
	public void ordenaCP(List<Proveedor> proveedores) {
		Proveedor aux=new Proveedor();
		
		for (int i=0;i<proveedores.size()-1;i++) {
			for (int j=0;j<proveedores.size()-1;j++) {
				if(proveedores.get(j+1).getCodigopostal()<proveedores.get(j).getCodigopostal()) {
					
					aux.setCif(proveedores.get(j+1).getCif());
					aux.setNombre(proveedores.get(j+1).getNombre());
					aux.setCodigopostal(proveedores.get(j+1).getCodigopostal());
					aux.setEmail(proveedores.get(j+1).getEmail());
					aux.setTelefono(proveedores.get(j+1).getTelefono());
					
					proveedores.get(j+1).setCif(proveedores.get(j).getCif());
					proveedores.get(j+1).setNombre(proveedores.get(j).getNombre());
					proveedores.get(j+1).setCodigopostal(proveedores.get(j).getCodigopostal());
					proveedores.get(j+1).setEmail(proveedores.get(j).getEmail());
					proveedores.get(j+1).setTelefono(proveedores.get(j).getTelefono());

					
					proveedores.get(j).setCif(aux.getCif());
					proveedores.get(j).setNombre(aux.getNombre());
					proveedores.get(j).setCodigopostal(aux.getCodigopostal());
					proveedores.get(j).setEmail(aux.getEmail());
					proveedores.get(j).setTelefono(aux.getTelefono());
				}
			}
		}
	}
	
	
	
}