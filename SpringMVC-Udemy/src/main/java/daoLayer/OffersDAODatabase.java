package daoLayer;

import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

import domainLayer.Offer;

//@Component
public class OffersDAODatabase implements OffersDAO {

	private NamedParameterJdbcTemplate jdbcTemplate;
	private DataSource dataSource;
	


	@Autowired
	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
		this.jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
	}

	@Override
	public List<Offer> getAllOffers() {

		return jdbcTemplate
				.query("Select * from offers, users where offers.username=users.username",
						new OfferMapper());

	}

	@Override
	public void addOffer(Offer offer) {
		BeanPropertySqlParameterSource params = new BeanPropertySqlParameterSource(
				offer);

		jdbcTemplate
				.update("insert into offers(username, text) values ( :username, :text)",
						params);

	}

	@Override
	public List<Offer> getOfferByUser(String username) {

		return jdbcTemplate
				.query("Select * from offers,users where offers.username=users.username and users.enabled=true and offers.username=:username",
						new MapSqlParameterSource("username", username),
						new OfferMapper());

	}

	@Override
	public boolean update(Offer offer) {
		BeanPropertySqlParameterSource params = new BeanPropertySqlParameterSource(
				offer);
		return jdbcTemplate.update(
				"update offers set text=:text where id=:id", params) == 1;

	}

	@Override
	public void delete(int id) {
		jdbcTemplate.update("delete from offers where id=:id", new MapSqlParameterSource("id",id));
	}
}
