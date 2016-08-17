package serviceLayer;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import daoLayer.JokesDAO;
import domainLayer.Joke;

@Service("jokesService")
public class JokesService  {
	
	@Autowired
	private JokesDAO jokesDao;

	public void addOrUpdateJoke(Joke joke) {
		jokesDao.addOrUpdateJoke(joke);
		
	}

	public void deleteJoke(int id) {
		jokesDao.deleteJoke(id);
	}

	public List<Joke> getAllJokes() {
		return jokesDao.getAllJokes();
	}

	public List<Joke> getJokesByUsername(String username) {
		return jokesDao.getJokesByUsername(username);
	}

	public boolean exists(int id) {
		return jokesDao.exists(id);
	}
	
	public List<Joke> getJokesByRating(){
		return jokesDao.getJokesByRating();
	}

	public List<Joke> getJokesByDate() {
		return jokesDao.getJokesByDate();
	}

	public Set<String> getAllCategories() {
		return jokesDao.getAllCategories();
	}

	public List<Joke> getJokesByCategory(String categoryName) {
		return jokesDao.getJokesByCategory(categoryName);
	}

	public void upVote(int id) {
		jokesDao.upVote(id);
	}

	public void downVote(int id) {
		jokesDao.downVote(id);
	}

	public void deleteJokesByUser(String name) {
		jokesDao.deleteJokesByUser(name);
	}
	

}
