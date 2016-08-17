package daoLayer;

import java.util.List;

import domainLayer.Offer;

public interface OffersDAO {

	public abstract List<Offer> getAllOffers();

	public abstract void addOffer(Offer offer);

	public abstract List<Offer> getOfferByUser(String username);
	
	public abstract boolean update(Offer offer);

	public abstract void delete(int id);

}