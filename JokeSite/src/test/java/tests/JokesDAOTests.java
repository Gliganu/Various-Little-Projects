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

import daoLayer.JokesDAO;
import daoLayer.UsersDAO;
import domainLayer.Joke;
import domainLayer.User;

@ActiveProfiles("dev")
@ContextConfiguration(locations = { "file:src/test/java/testConfig/datasource.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
public class JokesDAOTests {

	@Autowired
	private JokesDAO jokesDAO;

	@Autowired
	private UsersDAO usersDAO;

	@Autowired
	private DataSource dataSource;

	private User user1 = new User("Gliga", "pass", "ROLE_USER");
	private User user2 = new User("Admin", "pass", "ROLE_ADMIN");
	private User user3 = new User("Flaviu", "pass", "ROLE_USER");

	private Joke joke1 = new Joke("Banc", "Visinesugipula", "grosier", user1);
	private Joke joke2 = new Joke("Glumita", "Bula la scoala", "Bula", user1);
	private Joke joke3 = new Joke("Gluma", "Bancu cu bascula", "grosier", user2);

	@Before
	public void init() {

		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		jdbcTemplate.execute("delete from jokes");
		jdbcTemplate.execute("delete from users");

	}
	
	@Test
	public void populateDatabases(){
		user1.setRating(100);
		user2.setRating(120);
		user3.setRating(8);
		
		usersDAO.addOrUpdateUser(user1);
		usersDAO.addOrUpdateUser(user2);
		usersDAO.addOrUpdateUser(user3);
		
		joke1.setRating(40);
		joke2.setRating(16);
		joke3.setRating(42);
		jokesDAO.addOrUpdateJoke(joke1);
		jokesDAO.addOrUpdateJoke(joke2);
		jokesDAO.addOrUpdateJoke(joke3);
		
	}

	@Test
	public void testCreateJoke() {
		usersDAO.addOrUpdateUser(user1);
		usersDAO.addOrUpdateUser(user2);

		jokesDAO.addOrUpdateJoke(joke1);
		jokesDAO.addOrUpdateJoke(joke2);
		jokesDAO.addOrUpdateJoke(joke3);

		List<Joke> jokeList = jokesDAO.getAllJokes();
		assertEquals("Three jokes should have been created and retrieved", 3,
				jokeList.size());
	}

	@Test
	public void testExistsAndDelete() {
		usersDAO.addOrUpdateUser(user1);
		jokesDAO.addOrUpdateJoke(joke1);

		assertTrue(jokesDAO.exists(joke1.getId()));

		jokesDAO.deleteJoke(joke1.getId());

		assertFalse(jokesDAO.exists(joke1.getId()));
	}

	@Test
	public void testJokesByUsername() {

		usersDAO.addOrUpdateUser(user1);
		usersDAO.addOrUpdateUser(user2);

		jokesDAO.addOrUpdateJoke(joke1);
		jokesDAO.addOrUpdateJoke(joke2);
		jokesDAO.addOrUpdateJoke(joke3);

		List<Joke> jokeList = jokesDAO.getJokesByUsername(user1.getUsername());
		assertEquals("Two jokes belong to user1", 2,
				jokeList.size());
	}

}