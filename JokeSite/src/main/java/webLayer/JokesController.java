package webLayer;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import serviceLayer.JokesService;
import serviceLayer.UsersService;
import domainLayer.Joke;

@Controller
@RequestMapping(value="/")
public class JokesController {

	private final static String HOME_PAGE="home";
	private final static String CATEGORIES_PAGE="allCategories";
	
	@Autowired
	private JokesService jokesService;
	
	@Autowired
	private UsersService usersService;
	
	@RequestMapping(value="/searchJokesByUser")
	public String serachJokesByUser(@RequestParam String uid, Model model) {
		
		List<Joke> jokeList = jokesService.getJokesByUsername(uid);
		model.addAttribute("jokeList", jokeList);
		return HOME_PAGE;
	}

	@RequestMapping(value="/searchJokesByDate")
	public String searchJokesByDate(Model model) {
		
		List<Joke> jokeList = jokesService.getJokesByDate();
		model.addAttribute("jokeList", jokeList);
		return HOME_PAGE;
		
	}

	@RequestMapping(value="/searchJokesByCategory")
	public String searchJokesByCategory(@RequestParam String category, Model model) {
		
		List<Joke> jokeList = jokesService.getJokesByCategory(category);
		model.addAttribute("jokeList", jokeList);
		return HOME_PAGE;
		
	}
	
	
	@RequestMapping(value="/allCategories")
	public String showCategoriesPage(Model model) {
		
		Set<String> categorySet = jokesService.getAllCategories();
		model.addAttribute("categorySet", categorySet);
		
		return CATEGORIES_PAGE;
	}

	@RequestMapping(value="/upVote")
	public String upVoteJoke(@RequestParam int id, Model model) {
		
		jokesService.upVote(id);
		usersService.upVote(id);
		
		List<Joke> jokeList = jokesService.getAllJokes();
		model.addAttribute("jokeList", jokeList);
		
		return HOME_PAGE;
	}
	
	@RequestMapping(value="/downVote")
	public String downVoteJoke(@RequestParam int id, Model model) {
		
		jokesService.downVote(id);
		usersService.downVote(id);
		
		List<Joke> jokeList = jokesService.getAllJokes();
		model.addAttribute("jokeList", jokeList);
		
		return HOME_PAGE;
	}
	
	
}

