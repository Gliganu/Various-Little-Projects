package webLayer;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import serviceLayer.UsersService;
import domainLayer.User;

@Controller
@RequestMapping(value="/")
public class LoginController {

	private final static String LOGIN_PAGE="loginForm";
	private final static String LOG_OUT_PAGE="logOut";
	private final static String CREATE_ACCOUNT_PAGE="createAccountForm";
	private final static String INFO_PAGE="infoPage";
	
	@Autowired
	private UsersService usersService;

	@RequestMapping(value="/admin")
	public String showAdminPage(){
		return "admin";
	}

	@RequestMapping(value="/authenticated")
	public String showAuthenticatedPage(){
		return "authenticated";
	}
	
	@RequestMapping(value="/login")
	public String showLoginPage(){
		return LOGIN_PAGE;
	}
	
	@RequestMapping(value="/logOut")
	public String showLogOutPage(){
		return LOG_OUT_PAGE;
	}
	
	@RequestMapping(value="/accessDenied")
	public String showAccessDeniedPage(Model model){
		
		model.addAttribute("info", "Access denied!");
		return INFO_PAGE;
	}


	@RequestMapping(value="/createAccount")
	public String showCreateAccountPage(Model model){
		model.addAttribute("user", new User());
		return CREATE_ACCOUNT_PAGE;
	}
	
	@RequestMapping(value = "/doCreateAccount", method = RequestMethod.POST)
	public String doCreateAccount(@Valid User user, BindingResult result,Model model) {

		System.out.println(user);

		if (result.hasErrors()) {
			System.out.println("Form does not validate");
			return CREATE_ACCOUNT_PAGE;
		} else {

			user.setAuthority("ROLE_USER");

			if (usersService.exists(user.getUsername())) {
				result.rejectValue("username", "DuplicateKey.user.username");
				return CREATE_ACCOUNT_PAGE;
			}

			usersService.addOrUpdateUser(user);
			
			model.addAttribute("info", "Account successfully created!");
			return INFO_PAGE;
		}

	}
	
	
	
}
