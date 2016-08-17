package tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.List;

import javax.sql.DataSource;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import daoLayer.UsersDAO;
import domainLayer.User;

@ActiveProfiles("dev")
@ContextConfiguration(locations = {
		"file:src/main/java/config/security-context.xml",
		"file:src/test/java/testConfig/datasource.xml" }) 
@RunWith(SpringJUnit4ClassRunner.class)
public class UsersDAOTests {

	@Autowired
	private UsersDAO usersDAO;

	@Autowired
	private DataSource dataSource;
	
	private User user1 = new User("Gliga", "pass", "g@g.com", "user", true,
			"Gliga Bogdan");
	
	private User user2 = new User("Mihai", "pass", "mihai@yahoo.com", "user", true,
			"Mihai Ionescu");
	
	private User user3 = new User("Andreea", "pass", "andreea@google.com", "user", true,
			"Andreea Muresan");
	
	private User user4 = new User("Tomi", "pass", "toma@gmail.com", "user", true,
			"Tomi Dagustul");
	

	@Before
	public void init() {
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

		jdbcTemplate.execute("delete from offers");
		jdbcTemplate.execute("delete from messages");
		jdbcTemplate.execute("delete from users");
	}

	@Test
	public void testCreateRetrive(){
		usersDAO.createUser(user1);
		usersDAO.createUser(user2);
		usersDAO.createUser(user3);
		usersDAO.createUser(user4);
		
		List<User> userList2= usersDAO.getAllUsers();
		assertEquals("Four users should have been created and retrieved", 4, userList2.size());
		 
	}
	
	@Test
	public void testExists(){
		usersDAO.createUser(user1);
		usersDAO.createUser(user2);
		usersDAO.createUser(user3);
		
		assertTrue("User should exist", usersDAO.exists(user1.getUsername()));
		assertTrue("User should exist", usersDAO.exists(user2.getUsername()));

	}

}