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
import in.nit.service.IUSerService;
@Service
public class UserServiceImpl implements IUSerService,UserDetailsService {
	@Autowired
	private UserRepository repo;
	@Autowired
	private BCryptPasswordEncoder pwdencoder;

	@Override
	public Integer saveUser(User user) {
		String password=user.getPwd();
		password=pwdencoder.encode(password);
		user.setPwd(password);
		return repo.save(user).getId();
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		//get model class User based on USerName
		User user=repo.findByUserName(username);
		
		//Roles to Set<GrantAuthorities>
		Set<GrantedAuthority> authorities=new HashSet<>();
		List<String> roles=user.getRoles();
		for(String role:roles) {
			authorities.add(new SimpleGrantedAuthority(role));
		}
		
		//return S/f User
		org.springframework.security.core.userdetails.User usr=new org.springframework.security.core.userdetails.User(user.getUserName(), user.getPwd(), authorities);
		
		
		return usr;
	}

}
