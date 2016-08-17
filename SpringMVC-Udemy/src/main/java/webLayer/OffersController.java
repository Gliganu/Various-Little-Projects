package webLayer;

import java.security.Principal;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import serviceLayer.OffersService;
import domainLayer.Offer;

@Controller
public class OffersController {

	private static String HOME_PAGE = "home";
	private static String ALL_OFFERS_PAGE = "allOffers";
	private static String ADD_OFFER_PAGE = "addOfferForm";
	private static String OFFER_CREATED_PAGE = "offerCreated";
	private static String OFFER_DELETED_PAGE = "offerDeleted";
	private static String ERROR_PAGE = "error";

	@Autowired
	private OffersService offersService;

	@RequestMapping(value = "/showAll", method = RequestMethod.GET)
	public String showAllOFfers(Model model) {
		List<Offer> offerList = offersService.getAllOffers();
		model.addAttribute("offerList", offerList);
		return ALL_OFFERS_PAGE;
	}

	@RequestMapping(value = "/addOffer")
	public String addOffer(Model model, Principal principal) {

		Offer offer = null;
		if (principal != null) {
			String username = principal.getName();
			offer = offersService.getOfferByUser(username);

		}

		if (offer == null) {
			offer = new Offer();
		}

		model.addAttribute("offer", offer);

		return ADD_OFFER_PAGE;
	}

	@RequestMapping(value = "/createOffer", method = RequestMethod.POST)
	public String createOffer(Model model, @Valid Offer offer,
			BindingResult result, Principal principal,
			@RequestParam(value = "delete", required = false) String delete) {

		System.out.println(offer);

		if (result.hasErrors()) {
			return ADD_OFFER_PAGE;
		}

		if (delete == null) {

			System.out.println("delete is null");
			String username = principal.getName();
			offer.getUser().setUsername(username);

			offersService.saveOrUpdate(offer);
			
			return OFFER_CREATED_PAGE;
			
		} else {
			
			offersService.delete(offer.getId());
			return OFFER_DELETED_PAGE;
		}


	}
}
