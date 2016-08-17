package daoLayer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import domainLayer.Joke;
import domainLayer.JokeDateComparator;
import domainLayer.JokeRatingComparator;

@Component("jokesDao")
@Repository
@Transactional
public class JokesDAOHibernate implements JokesDAO {

	@Autowired
	private SessionFactory sessionFactory;
	
	public Session session(){
		return sessionFactory.getCurrentSession();
	}
	
	public void addOrUpdateJoke(Joke joke) {
		session().saveOrUpdate(joke);
		
	}

	@Override
	public void deleteJoke(int id) {
		Criteria crit= session().createCriteria(Joke.class);
		crit.add(Restrictions.idEq(id));
		session().delete(crit.uniqueResult());
	}

	@Override
	public List<Joke> getAllJokes() {
		return session().createQuery("from Joke").list();
	}

	@Override
	public List<Joke> getJokesByUsername(String username) {
		Criteria crit= session().createCriteria(Joke.class);
		crit.add(Restrictions.eq("user.username", username));
		return crit.list();
	}

	@Override
	public boolean exists(int id) {

		Criteria crit= session().createCriteria(Joke.class);
		crit.add(Restrictions.idEq(id));
		return ((Joke) crit.uniqueResult()) != null;
	}

	@Override
	public List<Joke> getJokesByRating() {
		List<Joke> jokeList = this.getAllJokes();
		
		Collections.sort(jokeList,new JokeRatingComparator());
		
		return jokeList;
	}

	@Override
	public List<Joke> getJokesByDate() {
			List<Joke> jokeList = this.getAllJokes();
			
			Collections.sort(jokeList,new JokeDateComparator());
			
			return jokeList;
	}

	@Override
	public Set<String> getAllCategories() {
		List<Joke> jokeList =  this.getAllJokes();
		Set<String> categorySet = new HashSet<String>();
		
		for(Joke joke:jokeList){
			categorySet.add(joke.getCathegory());
		}
		
		List categoryList = new ArrayList(categorySet);
		Collections.sort(categoryList);
		
		return new HashSet<String>(categoryList);
	}

	@Override
	public List<Joke> getJokesByCategory(String categoryName) {
		Criteria crit= session().createCriteria(Joke.class);
		crit.add(Restrictions.eq("cathegory", categoryName));
		return crit.list();
	}

	@Override
	public void upVote(int id) {
		Criteria crit= session().createCriteria(Joke.class);
		crit.add(Restrictions.idEq(id));
		((Joke)crit.uniqueResult()).upVote();
	}

	@Override
	public void downVote(int id) {
		Criteria crit= session().createCriteria(Joke.class);
		crit.add(Restrictions.idEq(id));
		((Joke)crit.uniqueResult()).downVote();
	}

	@Override
	public void deleteJokesByUser(String name) {
		List<Joke> jokesToBeDeleted= getJokesByUsername(name);
		for(Joke joke: jokesToBeDeleted){
			deleteJoke(joke.getId());
		}
	}

}
