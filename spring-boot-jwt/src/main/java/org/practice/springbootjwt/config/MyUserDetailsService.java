package org.practice.springbootjwt.config;

import org.practice.springbootjwt.db.model.User;
import org.practice.springbootjwt.model.MyUserDetails;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service	 
public class MyUserDetailsService implements UserDetailsService{

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		// get user from DB
		User user = new User(1, "Amol", "Singh", "amolsingh9372@gmail.com");
		
		return new MyUserDetails(user);
	}
	

}
