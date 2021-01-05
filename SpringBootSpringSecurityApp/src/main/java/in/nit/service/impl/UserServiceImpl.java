package in.nit.service.impl;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import in.nit.model.User;
import in.nit.repo.UserRepository;
import in.nit.service.UserService;
@Service
public class UserServiceImpl implements UserService,UserDetailsService {
	@Autowired
	private UserRepository repo;
	@Autowired
	private BCryptPasswordEncoder bt;

	@Override
	public Integer saveUser(User user) {
		String pwd=user.getPassword();
		pwd=bt.encode(pwd);
		user.setPassword(pwd);
		
		return repo.save(user).getId();
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		//load model class object
		User user=repo.getUserByMail(username);
		
		//get roles from the User obj
		List<String> roles=user.getRoles();
		
		//create set for GrantedAuthority
		Set<GrantedAuthority> authorities=new HashSet<>();
		
		//convert String roles to GrantedAuthority object
		for(String role:roles) {
			authorities.add(new SimpleGrantedAuthority(role));
		}
		
		//create Spring Security User Object
		org.springframework.security.core.userdetails.User uob=new org.springframework.security.core.userdetails.User(username,
								user.getPassword(), authorities);
		
		
			return uob;
	}

}
