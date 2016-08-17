package daoLayer;

import java.util.List;
import java.util.Set;

import domainLayer.Joke;

public interface JokesDAO {

	public void addOrUpdateJoke(Joke joke);
	
	public void deleteJoke(int id);

	public List<Joke> getAllJokes();
	
	public List<Joke> getJokesByUsername(String username);

	public List<Joke> getJokesByRating();
	
	public boolean exists(int id);

	public List<Joke> getJokesByDate();

	public Set<String> getAllCategories();

	public List<Joke> getJokesByCategory(String categoryName);

	public void upVote(int id);

	public void downVote(int id);

	public void deleteJokesByUser(String name);
}
