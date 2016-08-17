package serviceLayer;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.catalina.ha.backend.CollectedInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import daoLayer.OffersDAO;
import domainLayer.Offer;

@Service("offersService")
public class OffersService {

	@Autowired
	private OffersDAO offersDAO;

	public void setOffersDAO(OffersDAO offersDAO) {
		this.offersDAO = offersDAO;
		String[] args=new String[10];
		 List<Offer> offerList=new ArrayList();
	}

	public List<Offer> getAllOffers() {
		return offersDAO.getAllOffers();
	}

	public void addOffer(Offer offer) {
		offersDAO.addOffer(offer);
	}

	public Offer getOfferByUser(String username) {

		if (username == null) {
			return null;
		}

		List<Offer> offerList = offersDAO.getOfferByUser(username);

		if (offerList.size() == 0) {
			return null;
		}

		return offerList.get(0);

	}

	public boolean hasOffer(String name) {
		if (name == null) {
			return false;
		}

		List<Offer> offerList = offersDAO.getOfferByUser(name);
		if (offerList.size() == 0) {
			return false;
		}

		return true;
	}

	public void saveOrUpdate(Offer offer) {
		if (offer.getId() != 0) {
			offersDAO.update(offer);
		}
		else {
			offersDAO.addOffer(offer);
		}
	}

	public void delete(int id) {
		offersDAO.delete(id);
	}

}
