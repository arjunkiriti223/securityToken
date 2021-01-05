package in.nit.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import in.nit.model.User;
import in.nit.model.UserRequest;
import in.nit.model.UserResponse;
import in.nit.service.IUSerService;
import in.nit.util.JwtUtil;

@RestController
@RequestMapping("/user")
public class UserController {
	
	@Autowired
	private IUSerService service;
	
	@Autowired
	private JwtUtil utils;
	
	// it will verify user name and password
	@Autowired
	private AuthenticationManager manager;
	
	
	@PostMapping("/save")
	public ResponseEntity<String> saveUser(@RequestBody User user){
		Integer id=service.saveUser(user);
		return  ResponseEntity.ok("USer is saved with id  "+id);
	}
	
	//it will cross check user data with DB using USerDetailseService
	
	//validate login user
	@PostMapping("/login")
	public ResponseEntity<UserResponse> loginCheck(
			@RequestBody UserRequest userRequest){
		
		manager.authenticate(new UsernamePasswordAuthenticationToken(
				userRequest.getUsername(),
				userRequest.getPassword()
				)
				);
		//genarate Token
		String token=utils.generateToken(userRequest.getUsername());
		//give response
		
		return ResponseEntity.ok(new UserResponse("SUCCES",token));
		
	}
}
