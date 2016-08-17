package webLayer;

import java.security.Principal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import serviceLayer.OffersService;
import serviceLayer.UsersService;
import domainLayer.Message;
import domainLayer.User;

@Controller
public class LoginController {

	private static String LOGIN_PAGE = "loginForm";
	private static String CREATE_NEW_ACCOUNT_PAGE = "newAccountForm";
	private static String LOG_OUT_PAGE = "logOut";
	private static String ADMIN_PAGE = "admin";
	private static String ACCESS_DENIED_PAGE = "accessDenied";
	private static String ALL_MESSAGES_PAGE = "allMessages";

	
	@Autowired
	private UsersService usersService;
	@Autowired
	private OffersService offersService;
	
	public void setUsersService(UsersService usersService) {
		this.usersService = usersService;
	}

	@RequestMapping(value = "/login")
	public String showLoginPage() {
		return LOGIN_PAGE;
	}

	@RequestMapping(value = "/logOut")
	public String showLogOutPage() {
		return LOG_OUT_PAGE;
	}

	@RequestMapping(value = "/accessDenied")
	public String showAccessDeniedPage() {
		return ACCESS_DENIED_PAGE;
	}
	
	@RequestMapping(value = "/allMessages")
	public String showAllMessagesPage() {
		return ALL_MESSAGES_PAGE;
	}

	@RequestMapping(value = "/admin")
	public String showAdmin(Model model) {

		List<User> userList = usersService.getAllUsers();
		model.addAttribute("userList", userList);

		return ADMIN_PAGE;
	}

	@RequestMapping(value = "/newAccount")
	public String showNewAccountPage(Model model) {

		model.addAttribute("user", new User());
		return CREATE_NEW_ACCOUNT_PAGE;

	}

	@RequestMapping(value = "/createAccount", method = RequestMethod.POST)
	public String createOffer(@Valid User user, BindingResult result) {

		System.out.println(user);

		if (result.hasErrors()) {
			System.out.println("Form does not validate");
			return CREATE_NEW_ACCOUNT_PAGE;
		} else {
			System.out.println("Form validated");

			user.setEnabled(true);
			user.setAuthority("ROLE_USER");

			if (usersService.exists(user.getUsername())) {
				result.rejectValue("username", "DuplicateKey.user.username");
				return CREATE_NEW_ACCOUNT_PAGE;
			}

			usersService.createUser(user);

			return "accountCreated";
		}

	}
	
	@RequestMapping(value="/getMessages", produces="application/json")
	@ResponseBody
	public Map<String,Object> getMessages(Principal principal){
		
		List<Message> messageList=null;
		if(principal == null){
			messageList = new ArrayList();
		}
		else{
			String username=principal.getName();
			messageList= usersService.getMessages(username);
			
			Map<String,Object> data= new HashMap();
			data.put("messageList", messageList);
			data.put("number", messageList.size());
			return data;
		}
		
		return null;
	}

	@RequestMapping(value="/sendMessage", method=RequestMethod.POST, produces="application/json")
	@ResponseBody
	public Map<String,Object> sendMessage(Principal principal, @RequestBody Map<String,Object> data){
		
		String text=(String) data.get("text");
		String name= (String) data.get("name");
		String email = (String) data.get("email");
		Integer target= (Integer) data.get("target");
		
		System.out.println(name+"---"+email+"----"+text);

		Map<String,Object> returnVal= new HashMap();
		
		returnVal.put("success", true);
		returnVal.put("target", target);
		
		return returnVal;
	
	}
	
	
}
