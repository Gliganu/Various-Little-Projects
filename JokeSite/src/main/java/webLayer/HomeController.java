package webLayer;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import serviceLayer.JokesService;
import serviceLayer.UsersService;
import domainLayer.Joke;
import domainLayer.User;

@Controller
@RequestMapping(value = "/")
public class HomeController {

	private final static String HOME_PAGE = "home";
	private final static String FUNNIEST_USERS_PAGE = "funniestUsers";

	@Autowired
	private JokesService jokesService;
	
	@Autowired
	private UsersService usersService;

	@RequestMapping(value="/")
	public String showHomePage(Model model) {

		List<Joke> jokeList = jokesService.getAllJokes();
		model.addAttribute("jokeList", jokeList);
		return HOME_PAGE;
	}
	
	
	@RequestMapping(value="/searchJokesByRating")
	public String searchJokesByRating(Model model) {
		
		List<Joke> jokeList = jokesService.getJokesByRating();
		model.addAttribute("jokeList", jokeList);
		return HOME_PAGE;
	}

	@RequestMapping(value="/funniestUsers")
	public String searchFunniestUsers(Model model) {
		List<User> userList = usersService.getUsersByRating();
		model.addAttribute("userList",userList);
		
		Map<User,Integer> jokesMap =  new HashMap<User,Integer>();
		
		for(User user: userList){
			int numberJokes= jokesService.getJokesByUsername(user.getUsername()).size();
			jokesMap.put(user, numberJokes);
		}
		
		model.addAttribute("jokesMap", jokesMap);
		
		return FUNNIEST_USERS_PAGE;
	}
	
	


}
