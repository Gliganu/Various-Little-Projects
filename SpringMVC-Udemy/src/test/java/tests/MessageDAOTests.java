package tests;

import static org.junit.Assert.assertEquals;

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

import daoLayer.MessagesDAO;
import daoLayer.OffersDAO;
import daoLayer.UsersDAO;
import domainLayer.Message;
import domainLayer.Offer;
import domainLayer.User;

@ActiveProfiles("dev")
@ContextConfiguration(locations = {
		"file:src/main/java/config/security-context.xml",
		"file:src/test/java/testConfig/datasource.xml" }) 
@RunWith(SpringJUnit4ClassRunner.class)
public class MessageDAOTests {

	@Autowired
	private UsersDAO usersDAO;

	@Autowired
	private OffersDAO offersDAO;
	
	@Autowired
	private MessagesDAO messagesDAO;

	@Autowired
	private DataSource dataSource;
	
	private User user1 = new User("Gliga", "pass", "g@g.com", "user", true,
			"Gliga Bogdan");
	
	private User user2 = new User("Mihai", "pass", "mihai@yahoo.com", "user", false,
			"Mihai Ionescu");

	private User user3 = new User("Andreea", "pass", "andreea@google.com", "user", true,
			"Andreea Muresan");
	
	

	@Before
	public void init() {
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

		jdbcTemplate.execute("delete from offers");
		jdbcTemplate.execute("delete from messages");
		jdbcTemplate.execute("delete from users");
	}
	
	@Test
	public void testCreateRetrieve(){
		usersDAO.createUser(user1);
		usersDAO.createUser(user2);
		usersDAO.createUser(user3);
		
		Message message1=new Message("Subject 1","Content 1","Isaac Newton","isac@g.com",user1.getUsername());
		messagesDAO.addMessage(message1);
		
		}
	
	
}