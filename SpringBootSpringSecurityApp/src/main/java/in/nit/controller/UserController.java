

package in.nit.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import in.nit.model.User;
import in.nit.service.UserService;

@Controller
class UserController {
	@Autowired
	private UserService userService;
	
	@GetMapping("/register")
	public String userRegister() {
		return "UserRegister";
	}
	
	@PostMapping("/save")
	public String saveUser(@ModelAttribute User user,Model model) {
		Integer id=userService.saveUser(user);
		model.addAttribute("message","user is Saved with Id="+id);
		return "UserRegister";
		
		
	}
}
