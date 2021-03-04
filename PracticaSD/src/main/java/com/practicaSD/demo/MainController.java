package com.practicaSD.demo;

import java.sql.Date;
import java.util.ArrayList;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import com.practicaSD.demo.RepositorioEjemplar;
import com.practicaSD.demo.RepositorioMoneda;
import com.practicaSD.demo.RepositorioProveedor;
@Controller
public class MainController {

	
	@Autowired
	private RepositorioEjemplar repEjemplares;
	
	@Autowired
	private RepositorioProveedor repProveedores;
	
	@Autowired
	private RepositorioMoneda repMonedas;
	
	@PostConstruct   
	public void init() { 
		
		// PROVEEDOR: Calderilla
				String calderilla = "Calderilla";
				String CIFC = "B0000000A";
				int CPC = 25935;
				String emailC = "calderilla@yahoo.com";
				int telefonoC = 925123432;

		// PROVEEDOR: Scrooge McDuck 
				String scroogeMcDuck = "Scrooge McDuck";
				String CIFS = "B0000000B";
				int CPS = 28638;
				String emailS = "ScroogeMcDuck@price.com";
				int telefonoS = 966622332;
		
		//MONEDA: Doblon Espa�ol
				String doblonEspanol = "Doblon Espanol";
				double valorD = 2.0;		
				String descripcionD = "El doblon1 fue una moneda de oro espanola que equivalia a dos escudos o 32 reales";
				double diametroD = 5;
				double pesoD = 6.77;
				String oro = "oro";
				ArrayList<String> metalesD = new ArrayList<>();
				metalesD.add(oro);

		// MONEDA: Real de a 8 (1)
				String realdea8 = "Real de a 8";
				double valorR = 5.0;
				String descripcionR = "El real de a 8, peso de ocho, peso fuerte o peso duro";
				double diametroR = 6;
				double pesoR = 25.560;
				String plata = "plata";
				
				ArrayList<String> metalesR = new ArrayList<>();
				metalesR.add(plata);
				
		// MONEDA: Real de a 8 (2)		
				double valorR2= 2.0;
				double diametroR2 = 6;
				double pesoR2 = 15.520;
		
		// EJEMPLAR: Doblon Espanol (1)
				int yearD1 = 1634;
				String ciudadD1 = "Valladolid";
				@SuppressWarnings("deprecation")
				Date fechaD1 = new Date(100,11,1);
				String estadoD1 = "Bien";

		// EJEMPLAR: Doblon Espanol (2)
				int yearD2 = 1644;
				String ciudadD2 = "Salamanca";
				@SuppressWarnings("deprecation")
				Date fechaD2 = new Date(80,1,4);
				String estadoD2 = "Sucio";
		
		//EJEMPLAR: Real de a 8 (1)
				int yearR = 1759;
				String ciudadR = "Mexico";
				@SuppressWarnings("deprecation")
				Date fechaR = new Date (110,3,4);
				String estadoR = "Bien" ;

				ArrayList<String> metalesB = new ArrayList<>();
				metalesB.add("bronce");
				Date fechaP = new Date (100,2,6);

				
				repMonedas.save(new Moneda(valorD, doblonEspanol, diametroD, pesoD, metalesD, descripcionD));
				repMonedas.save(new Moneda(4, "Tetradracma", 8, 17, metalesD, "antigua moneda griega equivalente a un estatero o a 4 dracmas"));
				repMonedas.save(new Moneda(valorR, realdea8, diametroR, pesoR, metalesR, descripcionR));
				repMonedas.save(new Moneda(1, "Peseta", 3, 2, metalesB, " moneda de curso legal en Espana de 19-10-1868 hasta el 28-2-2002"));
				//repMonedas.save(new Moneda(valorR2, realdea8, diametroR2, pesoR2, metalesR, descripcionR));

				  
				
				repProveedores.save(new Proveedor(CIFC, calderilla, CPC, emailC, telefonoC));
				repProveedores.save(new Proveedor(CIFS, scroogeMcDuck, CPS, emailS, telefonoS));
				repProveedores.save(new Proveedor("B0000000C", "Filatelia Monge",28128 , "filateliamonge@bing.com", 902122432 ));

				
				repEjemplares.save(new Ejemplar(yearD1, ciudadD1, fechaD1, estadoD1, repMonedas.findByUnidadLikeIgnoreCaseAndValorLike(doblonEspanol, valorD), repProveedores.findByCif(CIFC)));
				repEjemplares.save(new Ejemplar(yearR, ciudadR, fechaR, estadoR, repMonedas.findByUnidadLikeIgnoreCaseAndValorLike(realdea8, valorR), repProveedores.findByCif(CIFS)));
				repEjemplares.save(new Ejemplar(490, "Atenas", fechaD2, estadoD1, repMonedas.findByUnidadLikeIgnoreCaseAndValorLike("Tetradracma", 4), repProveedores.findByCif(CIFS)));
				repEjemplares.save(new Ejemplar(yearD2, ciudadD2, fechaD2, estadoD2, repMonedas.findByUnidadLikeIgnoreCaseAndValorLike(doblonEspanol, valorD), repProveedores.findByCif(CIFC)));
				repEjemplares.save(new Ejemplar(1947, "Malaga", fechaP, estadoD2, repMonedas.findByUnidadLikeIgnoreCaseAndValorLike("Peseta", 1), repProveedores.findByCif("B0000000C")));
				//repEjemplares.save(new Ejemplar(yearR, ciudadD1, fechaD2, estadoD2, repMonedas.findByUnidadLikeIgnoreCaseAndValorLike(realdea8, valorR2), repProveedores.findByCif(CIFS)));


	}
	
	@RequestMapping("/")
	public String tablonEjemplares(Model model) {
		
		return "Inicio";
	}

}
