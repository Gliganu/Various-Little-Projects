package daoLayer;

import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import domainLayer.User;

//@Component("usersDAODatabase")
@Transactional
public class UsersDAODatabase implements UsersDAO {

	private NamedParameterJdbcTemplate jdbcTemplate;

	@Autowired
	private PasswordEncoder passwordEncoder;

	private DataSource dataSource;

	@Autowired
	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
		this.jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
	}

	@Override
	@Transactional
	public void createUser(User user) {

		// BeanPropertySqlParameterSource params = new
		// BeanPropertySqlParameterSource(user);

		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("username", user.getUsername());
		params.addValue("password", passwordEncoder.encode(user.getPassword()));
		params.addValue("email", user.getEmail());
		params.addValue("enabled", user.isEnabled());
		params.addValue("authority", user.getAuthority());
		params.addValue("name", user.getName());

		jdbcTemplate
				.update("insert into users(username, name, password, authority, email,enabled) values ( :username, :name, :password, :authority, :email, :enabled)",
						params);
	}

	@Override
	public boolean exists(String username) {
		return jdbcTemplate.queryForObject(
				"Select count(*) from users where username=:username",
				new MapSqlParameterSource("username", username), Integer.class) > 0;

	}

	@Override
	public List<User> getAllUsers() {
		return jdbcTemplate.query("SELECT * FROM users",
				BeanPropertyRowMapper.newInstance(User.class));

	}

	@Override
	public User getUser(String username) {
		return null;
	}

}
