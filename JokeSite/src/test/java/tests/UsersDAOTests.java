package tests;

import static org.junit.Assert.*;

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
		"file:src/test/java/testConfig/datasource.xml" })
@RunWith(SpringJUnit4ClassRunner.class)


public class UsersDAOTests {

	@Autowired
	private UsersDAO usersDAO;

	@Autowired
	private DataSource dataSource;

	private User user1 = new User("Gliganu", "pass", "User");
	private User user2 = new User("Mihai", "pass", "Admin");
	private User user3 = new User("Andreea", "pass", "User");

	@Before
	public void init() {
		
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		jdbcTemplate.execute("delete from users");

	}

	@Test
	public void testCreate() {
		usersDAO.addOrUpdateUser(user1);
		usersDAO.addOrUpdateUser(user2);
		usersDAO.addOrUpdateUser(user3);

		List<User> userList = usersDAO.getAllUsers();
		assertEquals("Three users should have been created and retrieved", 3,
				userList.size());
	}
	
	@Test 
	public void testRetrieve(){
		usersDAO.addOrUpdateUser(user1);
		assertTrue(usersDAO.exists(user1.getUsername()));
		assertTrue(usersDAO.getUserByUsername(user1.getUsername()).equals(user1));
	}
	
	@Test 
	public void testDelete(){
		usersDAO.addOrUpdateUser(user1);
		usersDAO.addOrUpdateUser(user2);
		
		usersDAO.deleteUser(user1.getUsername());
		usersDAO.deleteUser(user2.getUsername());
		
		List<User> userList = usersDAO.getAllUsers();
		assertEquals("Three shouldn't be any users in the db", 0,
				userList.size());
		
	}

}