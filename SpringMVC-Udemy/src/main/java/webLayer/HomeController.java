package webLayer;

import java.security.Principal;
import java.util.List;

import javax.validation.Valid;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import serviceLayer.OffersService;
import domainLayer.Offer;


@Controller
@RequestMapping(value="/")
public class HomeController {

	private static String HOME_PAGE="home";
	private static Logger logger = Logger.getLogger(HomeController.class);
	
	@Autowired
	private OffersService offersService;
	

	@RequestMapping(method=RequestMethod.GET)
	public String showHomePage(Model model, Principal principal){
	
		List<Offer> offerList= offersService.getAllOffers();
		model.addAttribute("offerList", offerList);
		
		boolean hasOffer=false;
		
		if(principal != null){
			hasOffer = offersService.hasOffer(principal.getName());
		}
		
		model.addAttribute("hasOffer", hasOffer);
		return HOME_PAGE;
	}
	
	
	
	
	
	
//	@ExceptionHandler(Exception.class) 
//	public String handleDatabaseException(Exception ex){
//			return ERROR_PAGE;
//	}
	
	
}
