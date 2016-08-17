package daoLayer;

import java.util.List;

import javax.sql.DataSource;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import domainLayer.Offer;


@Component("offersDAO")
@Repository
@Transactional
public class OffersDAOHibernate implements OffersDAO {

	@Autowired
	private SessionFactory sessionFactory;


	public Session session() {
		return sessionFactory.getCurrentSession();
	}
	
	@Override
	public List<Offer> getAllOffers() {
		Criteria crit=session().createCriteria(Offer.class);
		crit.createAlias("user", "u").add(Restrictions.eq("u.enabled",true));
		
		return crit.list();
	}

	@Override
	public void addOffer(Offer offer) {
		session().save(offer);
	}

	@Override
	public List<Offer> getOfferByUser(String username) {
		Criteria crit=session().createCriteria(Offer.class);
		crit.createAlias("user", "u");
		crit.add(Restrictions.eq("u.enabled",true));
		crit.add(Restrictions.eq("u.username",username));
		 
		return crit.list();
	}

	@Override
	public boolean update(Offer offer) {
		session().update(offer); 
		return true;

	}

	@Override
	public void delete(int id) {
		Query query = session().createQuery("delete from Offer where id=:id");
		query.setLong("id", id);
		query.executeUpdate();
	}
}
