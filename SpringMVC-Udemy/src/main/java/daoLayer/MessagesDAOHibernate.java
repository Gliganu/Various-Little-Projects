package daoLayer;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import domainLayer.Message;


@Repository
@Transactional
@Component("messagesDAO")
public class MessagesDAOHibernate implements MessagesDAO  {

	@Autowired
	private SessionFactory sessionFactory;


	public Session session() {
		return sessionFactory.getCurrentSession();
	}
	

	@Override
	public List<Message> getAllMessages() {
		Criteria crit=session().createCriteria(Message.class);
		
		return crit.list();
	}


	@Override
	public void addMessage(Message message) {
		System.out.println(message);
		session().save(message);
	}

	@Override
	public List<Message> getMessagesByUser(String username) {
		Criteria crit=session().createCriteria(Message.class);
		crit.add(Restrictions.eq("username",username));
		
		return crit.list();
	}


	@Override
	public void update(Message message) {
		session().update(message); 

	}

	@Override
	public void delete(int id) {
		Query query = session().createQuery("delete from Message where id=:id");
		query.setLong("id", id);
		query.executeUpdate();
	}
}
